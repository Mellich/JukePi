package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import network.MessageType;
import server.SkipCommandHandler;
import server.YTJBServer;
import server.connectivity.handler.*;
import utilities.IO;

/**handles incomming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionHandler extends Thread {
	
	public static final String SEPERATOR = ";\t;";
	public static final String FILESAVELOCATION = "files/";
	
	private Socket socket;
	private YTJBServer server;
	private boolean running = true;
	
	public ConnectionHandler(Socket s,YTJBServer server) {
		socket = s;
		this.server = server;
		server.registerClient(this);
	}
	
		
	@Override
	public void run() {
		super.run();
		try {
			while (running){
				String message = receiveMessage();
				String[] args = message.split(SEPERATOR);
				int prompt = Integer.parseInt(args[0]);
				IO.printlnDebug(this, "Parsing input...");
				switch (prompt){
				case MessageType.PAUSERESUME: new PauseResumeCommandHandler(socket,server.getScheduler()).handle();
					break;
				case MessageType.SKIP: new SkipCommandHandler(socket,server.getScheduler()).handle();
					break;
				case MessageType.GAPYOUTUBE: new YoutubeCommandHandler(socket,server,server.getGapList(),args[1]).handle();
					break;
				case MessageType.GAPLISTSAVETOFILE: new SaveGapListCommandHandler(socket,server.getGapList()).handle();
					break;
				case MessageType.DELETEFROMGAPLIST: new DeleteFromListCommandHandler(socket,server.getGapList(),Integer.parseInt(args[1])).handle();
					break;
				case MessageType.GETGAPLIST: new GetListCommandHandler(socket, server.getGapList()).handle();
					break;
				case MessageType.GETWISHLIST: new GetListCommandHandler(socket, server.getWishList()).handle();
					break;
				case MessageType.YOUTUBE:  new YoutubeCommandHandler(socket,server,server.getWishList(),args[1]).handle();
					break;
				case MessageType.ISREADY: new CheckIfReadyCommandHandler(socket).handle();
					break;
				case MessageType.GETCURRENTTRACK: new GetCurrentTrackCommandHandler(socket,server.getScheduler()).handle();
					break;
				case MessageType.SENDEDFILE: new SendedFileCommandHandler(socket,FILESAVELOCATION+args[1],server.getWishList()).handle();
					break;
				case MessageType.GAPSENDEDFILE: new SendedFileCommandHandler(socket,FILESAVELOCATION+args[1],server.getGapList()).handle();
				default: new UnknownCommandHandler(socket,message).handle();
				}
			}
			socket.close();
		} catch (IOException e) {
			IO.printlnDebug(this, "Error while handling client connection");
		}
		finally{
			server.removeClient(this);
		}
	}
	
	public void notify(int messageType){
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			out.write(messageType);
			out.newLine();
			out.flush();
		}
		catch (IOException e){
			IO.printlnDebug(this, "ERROR could not notify client");
		}
	}


	private String receiveMessage() throws IOException {
		BufferedReader networkInput;
		networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		IO.printlnDebug(this, "Waiting for input...");
		return networkInput.readLine();
	}

}
