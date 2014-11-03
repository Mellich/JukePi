package connection;

import java.io.BufferedReader;
import java.util.LinkedList;

public class ReaderThread extends Thread{
	
	private BufferedReader reader;
	private boolean running;
	private boolean notifier;
	private Collector c;
	
	public ReaderThread(BufferedReader reader, boolean notifier, Collector c) {
		this.reader = reader;
		running = true;
		this.notifier = notifier;
		this.c = c;
	}
	
	public void pause() {
		running = false;
	}
	
	public void unpause() {
		running = true;
	}
	
	public void updateGapList(LinkedList<String> gaplist) {
		gaplist.clear();
		String line = "";
		try {
			line = reader.readLine();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		String[] songs = line.split(MessageType.SEPERATOR);
		if (songs[0] != null) {
			for (int i = 1; i < songs.length; i++) {
				gaplist.add(songs[i]);
			}
		}
	}
	
	public void updatePlayingFile() {
		
	}
	
	@Override
	public void run(){
		if (notifier) {
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
				case MessageType.NEXTTRACKNOTIFY: updatePlayingFile();c.updateLists();break;
				case MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY:
				case MessageType.PAUSERESUMENOTIFY:
				case MessageType.READYFORRECEIVENOTIFY:
				case MessageType.RESPONSENOTIFY:
				default: break;
				}
				
				System.out.println(line+" notifier");
			}
		}
		else {
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
}
