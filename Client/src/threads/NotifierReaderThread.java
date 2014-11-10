package threads;

import java.io.BufferedReader;
import java.io.IOException;

import connection.Collector;
import connection.MessageType;

public class NotifierReaderThread extends Thread{

	private BufferedReader reader;
	private boolean running;
	private Collector c;
	
	public NotifierReaderThread(BufferedReader reader, Collector c) {
		this.reader = reader;
		this.running = true;
		this.c = c;
	}
	
	public void pause() {
		this.running = false;
	}
	
	public void unpause() {
		this.running = true;
	}
	
	public void run() {
		while (running) {
			String line = "";
			try {
				line = reader.readLine();
			} catch (IOException e) {
				//Socket was closed due to disconnect
			}
			int notify = 0;
			
			try {notify = Integer.parseInt(line);}
			catch (NumberFormatException e) { //Socket was closed due to disconnect
				notify = 0;
			}
			
			switch (notify) {
			case MessageType.NEXTTRACKNOTIFY: c.nextTrack();break;
			case MessageType.LISTSUPDATEDNOTIFY: c.updateLists();c.fillModels();break;
			case MessageType.PAUSERESUMENOTIFY: c.updateStatus();break;
			default: break;
			}
		}
	}
}