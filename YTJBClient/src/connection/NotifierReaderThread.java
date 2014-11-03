package connection;

import java.io.BufferedReader;
import java.util.LinkedList;

public class NotifierReaderThread extends Thread{
	
	private BufferedReader reader;
	private boolean running;
	private Collector c;
	
	public NotifierReaderThread(BufferedReader reader, Collector c) {
		this.reader = reader;
		running = true;
		this.c = c;
	}
	
	public void pause() {
		running = false;
	}
	
	public void unpause() {
		running = true;
	}
	
	@Override
	public void run(){
		String line = "";
		while (running) {
			line ="";
			try {
				line = reader.readLine();
			}
			catch (Exception e) {
				e.printStackTrace();
			}
			int notify = Integer.parseInt(line);
			switch (notify) {
			case MessageType.LISTSUPDATEDNOTIFY: c.updateLists();break;
			case MessageType.NEXTTRACKNOTIFY: c.updateLists();break;
			case MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY:
			case MessageType.PAUSERESUMENOTIFY:
			case MessageType.READYFORRECEIVENOTIFY:
			case MessageType.RESPONSENOTIFY:
			default: break;
			}
			
			System.out.println(line+" notifier");
		}
	}
}
