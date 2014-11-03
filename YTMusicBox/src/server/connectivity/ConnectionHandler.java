package server.connectivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

import network.MessageType;
import server.YTJBServer;
import server.connectivity.handler.*;
import utilities.IO;

/**handles incomming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionHandler extends Thread {
	
	public static final String FILESAVELOCATION = "files/";
	public static final String TEMPDIRECTORY = "/tmp/";
	
	private Socket socket;
	private YTJBServer server;
	private boolean running = true;
	private boolean notifiable = false;
	
	public ConnectionHandler(Socket s,YTJBServer server) {
		socket = s;
		this.server = server;
		server.registerClient(this);
	}
	
	private synchronized boolean getNotifiable(){
		return notifiable;
	}
	
	private synchronized void setNotifiable(boolean b){
		notifiable = b;
	}
	
		
	@Override
	public void run() {
		super.run();
		try {
			while (running){
				String message = receiveMessage();
				IO.printlnDebug(this, "Received message: "+message);
				String[] args = message.split(MessageType.SEPERATOR);
				int prompt = Integer.parseInt(args[0]);
				IO.printlnDebug(this, "Parsing input...");
				switch (prompt){
				case MessageType.PAUSERESUME: new PauseResumeCommandHandler(socket,server).handle();
					break;
				case MessageType.SKIP: new SkipCommandHandler(socket,server.getScheduler()).handle();
					break;
				case MessageType.GAPYOUTUBE: new YoutubeCommandHandler(socket,server,server.getGapList(),args[1]).handle();
					break;
				case MessageType.GAPLISTSAVETOFILE: new SaveGapListCommandHandler(socket,server.getGapList()).handle();
					break;
				case MessageType.DELETEFROMGAPLIST: new DeleteFromListCommandHandler(socket,server,server.getGapList(),Integer.parseInt(args[1])).handle();
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
				case MessageType.SENDEDFILE: new SendedFileCommandHandler(socket,TEMPDIRECTORY+args[1],server.getWishList()).handle();
					break;
				case MessageType.GAPSENTFILE: new SendedFileCommandHandler(socket,FILESAVELOCATION+args[1],server.getGapList()).handle();
					break;
				case MessageType.GETCURRENTPLAYBACKSTATUS: new GetCurrentPlaybackStatusCommandHandler(socket,server.getScheduler()).handle();
					break;
				case MessageType.BEGINNINGYOUTUBE: new BeginYoutubeCommandHandler(socket,server,server.getWishList(),args[1]).handle();
					break;
				case MessageType.GAPBEGINNINGYOUTUBE: new BeginYoutubeCommandHandler(socket,server,server.getGapList(),args[1]).handle();
					break;
				case MessageType.BEGINNINGSENTFILE: new BeginSentFileCommandHandler(socket,server,server.getWishList(),args[1]).handle();
					break;
				case MessageType.GAPBEGINNINGSENTFILE: new BeginSentFileCommandHandler(socket,server,server.getGapList(),args[1]).handle();
					break;
				case MessageType.DECLAREMEASNOTIFY: this.setNotifiable(true);
					break;
				default: new UnknownCommandHandler(socket,message).handle();
				}
			}
			socket.close();
		} catch (IOException e) {
			IO.printlnDebug(this, "Error while handling client connection");
		}
		finally{
			synchronized(server){
				server.removeClient(this);
			}
		}
	}
	
	public void notify(int messageType){
		if (getNotifiable())
			new NotifyClientCommandHandler(socket,messageType).handle();
	}


	private String receiveMessage() throws IOException {
		BufferedReader networkInput;
		networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		IO.printlnDebug(this, "Waiting for input...");
		while (true){
			String newline = networkInput.readLine();
			if (!newline.equals("")){
				return newline;
			}
		}
	}

}
