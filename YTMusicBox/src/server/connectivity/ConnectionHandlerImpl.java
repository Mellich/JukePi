package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import server.IO;
import server.MessageType;
import server.ProcessCommunicator;
import server.MusicTrack;
import server.player.YTTrackScheduler;

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
	
	private void handleYTVideo(String url) throws IOException{
		String parsedURL = ProcessCommunicator.parseStandardURL(url);
		String title = ProcessCommunicator.parseTitle(url);
		MusicTrack yURL = new MusicTrack(MessageType.YOUTUBE,title,parsedURL);
		int position = addToList(yURL);
		sendMessage(position+". "+yURL.getTitle());
	}
	
	private void handleSkipMessage() throws IOException{
		IO.printlnDebug(this, "skipping current playback");
		trackScheduler.skip();
		sendMessage("Track wurde übersprungen");
	}
	
	private void handlePauseResumeMessage() throws IOException{
		IO.printlnDebug(this, "pause/resume current playback");
		trackScheduler.pauseResume();
		sendMessage("Track wurde pausiert/angehalten");
	}
	
	private int addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		synchronized(urlList){
			urlList.add(track);
		}
		return urlList.indexOf(track);
	}
	
	private String receiveMessage() throws IOException{
		BufferedReader networkInput;
		networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		IO.printlnDebug(this, "Waiting for input...");
		return networkInput.readLine();
	}
	
	private void sendMessage(String s) throws IOException{
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
		IO.printlnDebug(this, "Sending Message: "+s);
		out.write(s);
		out.newLine();
		out.flush();
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
			case MessageType.PAUSERESUME: handlePauseResumeMessage();
				break;
			case MessageType.SKIP: handleSkipMessage();
				break;
			default:  handleYTVideo(args[1]);
			}
			socket.close();
		} catch (IOException e) {
			IO.printlnDebug(this, "Error while handling client connection");
		}
	}

}
