package threads;

import java.io.BufferedReader;
import java.io.IOException;

import connection.Collector;
import connection.MessageType;

/**
 * The Thread, that will listen to Notifies from the Server.
 * @author Haeldeus
 *
 */
public class NotifierReaderThread extends Thread{

	/**
	 * The BufferedReader, that will Read Messages.
	 */
	private BufferedReader reader;
	
	/**
	 * Determines, if the Thread is running or not.
	 */
	private boolean running;
	
	/**
	 * The Collector, that will perform the Changes.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the Thread.
	 * @param reader	The BufferedReader, that will listen to the Server.
	 * @param c	The Collector, that will perform the Changes.
	 */
	public NotifierReaderThread(BufferedReader reader, Collector c) {
		this.reader = reader;
		this.running = true;
		this.c = c;
	}
	
	/**
	 * Pauses the Thread.
	 */
	public void pause() {
		this.running = false;
	}
	
	/**
	 * Unpauses the Thread.
	 */
	public void unpause() {
		this.running = true;
	}
	
	/**
	 * Executes the Listening.
	 */
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
			case MessageType.LISTSUPDATEDNOTIFY: c.updateLists();c.fillModels();c.updateGaplistName();break;
			case MessageType.PAUSERESUMENOTIFY: c.updateStatus();break;
			case MessageType.GAPLISTCOUNTCHANGEDNOTIFY: c.fillGaplistModel();break;
			default: break;
			}
		}
	}
}