package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;
import java.util.LinkedList;

import server.MusicTrack;
import server.MusicTrack.TrackType;

/**static class that provides functions for uniform input and output
 * 
 * @author Mellich
 *
 */
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
			while (url != null || url == ""){
				String[] splitted = url.split(";");
				MusicTrack yURL = new MusicTrack(TrackType.valueOf(splitted[0]),splitted[1],splitted[2]);
				IO.printlnDebug(null, url);
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
			BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
			writer.write("");
			for (MusicTrack url : urls){
				writer.write(url.getMusicType()+";"+url.getTitle()+";"+url.getURL());
				writer.newLine();
			}
			writer.close();
			return true;
		} catch (IOException e) {
			IO.printlnDebug(null, "Error while saving the gaplist!");
		}
		return false;
	}
	
	public static boolean receiveAndSaveFile(Socket socket,String filename){
		InputStream in;
		IO.printlnDebug(null, "Receiving file...");
		try {
			in = socket.getInputStream();
			FileOutputStream fileOut = new FileOutputStream(filename);	           
			byte[] buffer = new byte[1024];
			int bytesRead = 0;
			while ((bytesRead = in.read(buffer)) > 0) {
			    fileOut.write(buffer, 0, bytesRead);
			    System.out.println(bytesRead);
			}	 
			fileOut.close();
			IO.printlnDebug(null, "File received!");
			return true;
		} catch (IOException e) {
			IO.printlnDebug(null, "ERROR: could not receive or save the file");
		}
		return false;
	}
	
	public static boolean sendFile(Socket socket,String filename){
		OutputStream out;
		IO.printlnDebug(null, "Sending file...");
		try {
			out = socket.getOutputStream();
			InputStream fileIn = new FileInputStream(filename);		 
			byte[] buffer = new byte[1024];
			while (fileIn.available() > 0) {
			    out.write(buffer, 0, fileIn.read(buffer));
			}			 
			out.close();
			fileIn.close();
			IO.printlnDebug(null, "File sent!");
			return true;
		} catch (IOException e) {
			IO.printlnDebug(null, "ERROR: could not send the file "+filename);
		}
		return false;
	}
}
