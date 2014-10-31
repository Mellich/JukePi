package server;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedList;


public class IO {

	static public void printlnDebug(Object speaker, String input){
		String name ;
		if (speaker != null)
			name = speaker.getClass().getName();
		else name = "STATIC";
		long n = Thread.currentThread().getId();
		Timestamp t = new Timestamp(System.currentTimeMillis());
		System.out.println(t.toString()+" Tread-"+n+"="+name+": "+input);
	}
	
	public static LinkedList<MusicTrack> loadGapListFromFile(String filename){
		LinkedList<MusicTrack> gapList = new LinkedList<MusicTrack>();
		try {
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			String url = reader.readLine();
			while (url != null){
				MusicTrack yURL = new MusicTrack(MessageType.YOUTUBE,"Standard-Track",url);
				gapList.add(yURL);
				url = reader.readLine();
			}
			reader.close();
		} catch (IOException e) {
			IO.printlnDebug(null, "Could not open file "+filename);
		}
		return gapList;
	}
	
	public static boolean saveGapListToFile(LinkedList<MusicTrack> urls, String filename){
		try {
			FileWriter fw = new FileWriter(filename);
			fw.write("");
			for (MusicTrack url : urls){
				fw.write(url.getMusicType()+";"+url.getTitle()+";"+url.getURL()+"/n");
			}
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			IO.printlnDebug(null, "Error while saving the gaplist!");
		}
		return false;
	}
}
