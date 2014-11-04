package threads;

import java.io.BufferedReader;
import java.io.IOException;

import connection.MessageType;

public class NotifierReaderThread extends Thread{

	private BufferedReader reader;
	private boolean running;
	
	public NotifierReaderThread(BufferedReader reader) {
		this.reader = reader;
		this.running = true;
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
				e.printStackTrace();
			}
			
			
			
		}
	}
}
