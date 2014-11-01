package server.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.LinkedList;

import server.IO;
import server.MessageType;
import server.MusicTrack;
import server.SkipCommandHandler;
import server.connectivity.handler.DeleteFromListCommandHandler;
import server.connectivity.handler.GetListCommandHandler;
import server.connectivity.handler.PauseResumeCommandHandler;
import server.connectivity.handler.SaveGapListCommandHandler;
import server.connectivity.handler.YoutubeCommandHandler;
import server.player.YTTrackScheduler;

/**handles incomming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionHandlerImpl extends Thread {
	
	public static final String SEPERATOR = ";\t;";
	
	private Socket socket;
	private LinkedList<MusicTrack> urlList;
	private LinkedList<MusicTrack> gapList;
	private YTTrackScheduler trackScheduler;
	
	public ConnectionHandlerImpl(Socket s, LinkedList<MusicTrack> u, LinkedList<MusicTrack> gapList,YTTrackScheduler scheduler) {
		socket = s;
		urlList = u;
		this.gapList = gapList;
		this.trackScheduler = scheduler;
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
			case MessageType.SENDEDFILE:
				break;
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
			case MessageType.GETWISHLIST: new GetListCommandHandler(socket, urlList).handle();
				break;
			default:  new YoutubeCommandHandler(socket,urlList,args[1]).handle();
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
