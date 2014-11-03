package connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.LinkedList;

public class SenderReaderThread extends Thread{

	private BufferedReader reader;
	private Collector c;
	
	public SenderReaderThread(BufferedReader reader, Collector c) {
		this.reader = reader;
		this.c = c;
	}
	
	public LinkedList<String> updateList() {
		LinkedList<String> res = new LinkedList<String>();
		String line = "";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String[] songs = line.split(MessageType.SEPERATOR);
		
		for (int i=1;i<songs.length;i++)
			res.add(songs[i]);
		return res;
	}
	
	public String getPlayingFile() {
		String line ="";
		try {
			line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String song[] = line.split(MessageType.SEPERATOR);
		return song[1];
	}
	
	public void skipCatcher() {
		try {
			String line = reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addCatcher() {
		try {
			String line = reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO What happens if adding fails?
	}
}
