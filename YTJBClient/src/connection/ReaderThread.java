package connection;

import java.io.BufferedReader;

public class ReaderThread extends Thread{
	
	BufferedReader reader;
	boolean running;
	
	public ReaderThread(BufferedReader reader) {
		this.reader = reader;
		running = true;
	}
	
	public void pause() {
		running = false;
	}
	
	public void unpause() {
		running = true;
	}
	
	@Override
	public void run(){
		while (running) {
			String line ="";
			try {
				line = reader.readLine();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(line);
		}
	}
}
