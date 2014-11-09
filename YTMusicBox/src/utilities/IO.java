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

import server.IdelViewer;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;

/**static class that provides functions for uniform input and output
 * 
 * @author Mellich
 *
 */
public class IO {
	
	public static boolean debugMode = true;

	static public void printlnDebug(Object speaker, String input){
		if (debugMode){
			String name ;
			if (speaker != null)
				name = speaker.getClass().getName();
			else name = "STATIC";
			long n = Thread.currentThread().getId();
			Timestamp t = new Timestamp(System.currentTimeMillis());
			System.out.println(t.toString()+" Thread-"+n+"="+name+": "+input);
		}
	}
	
	public static void loadGapListFromFile(String filename, YTJBServer server, IdelViewer viewer){
		try {
			IO.printlnDebug(null, "Start to load gap list...");
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			long max = reader.lines().count();
			reader.close();
			reader = new BufferedReader(new FileReader(filename));
			String url = reader.readLine();
			int current = 0;
			viewer.gaplistStatus(current, (int)max);
			while (url != null || url == ""){
				String[] splitted = url.split(";");
				MusicTrack yURL = new MusicTrack(TrackType.valueOf(splitted[0]),splitted[1],ProcessCommunicator.parseShortURLToVideoURL(splitted[2]),splitted[2]);
				IO.printlnDebug(null, "Loaded Track: "+splitted[1]);
				server.addToList(yURL, false, true);
				current++;
				viewer.gaplistStatus(current, (int) max);
				url = reader.readLine();
			}
			reader.close();
			viewer.gaplistStatus(current, (int) max);
		} catch (IOException e) {
			IO.printlnDebug(null, "ERROR while opening file: "+filename);
			viewer.gaplistStatus(0, 0);
		}
		IO.printlnDebug(null, "finished loading gap list!");
	}
	
	public static boolean saveGapListToFile(LinkedList<MusicTrack> urls, String filename){
		try {
			BufferedWriter writer = new BufferedWriter( new FileWriter(filename));
			writer.write("");
			for (MusicTrack url : urls){
				writer.write(url.getMusicType()+";"+url.getTitle()+";"+url.getShortURL());
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
