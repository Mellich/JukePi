package server.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.IO;
import server.MusicTrack;
import server.SkipCommandHandler;
import server.YTJBServer;
import server.connectivity.handler.*;
import server.player.TrackScheduler;

/**handles incomming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionHandler extends Thread {
	
	public static final String SEPERATOR = ";\t;";
	
	private Socket socket;
	private LinkedList<MusicTrack> wishList;
	private LinkedList<MusicTrack> gapList;
	private TrackScheduler trackScheduler;
	
	public ConnectionHandler(Socket s,YTJBServer server) {
		socket = s;
		wishList = server.getWishList();
		this.gapList = server.getGapList();
		this.trackScheduler = server.getScheduler();
	}
	
		
	@Override
	public void run() {
		super.run();
		try {
			String message = receiveMessage();
			String[] args = message.split(SEPERATOR);
			int prompt = Integer.parseInt(args[0]);
			IO.printlnDebug(this, "Parsing input...");
			switch (prompt){
			case MessageType.PAUSERESUME: new PauseResumeCommandHandler(socket,trackScheduler).handle();
				break;
			case MessageType.SKIP: new SkipCommandHandler(socket,trackScheduler).handle();
				break;
			case MessageType.GAPYOUTUBE: new YoutubeCommandHandler(socket,gapList,args[1]).handle();
				break;
			case MessageType.GAPLISTSAVETOFILE: new SaveGapListCommandHandler(socket,gapList).handle();
				break;
			case MessageType.DELETEFROMGAPLIST: new DeleteFromListCommandHandler(socket,gapList,Integer.parseInt(args[1])).handle();
				break;
			case MessageType.GETGAPLIST: new GetListCommandHandler(socket, gapList).handle();
				break;
			case MessageType.GETWISHLIST: new GetListCommandHandler(socket, wishList).handle();
				break;
			case MessageType.YOUTUBE:  new YoutubeCommandHandler(socket,wishList,args[1]).handle();
				break;
			case MessageType.ISREADY: new CheckIfReadyCommandHandler(socket).handle();
				break;
			case MessageType.GETCURRENTTRACK: new GetCurrentTrackCommandHandler(socket,trackScheduler).handle();
				break;
			default: new UnknownCommandHandler(socket,message).handle();
			}
			socket.close();
		} catch (IOException e) {
			IO.printlnDebug(this, "Error while handling client connection");
		}
	}


	private String receiveMessage() throws IOException {
		BufferedReader networkInput;
		networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		IO.printlnDebug(this, "Waiting for input...");
		return networkInput.readLine();
	}

}
