package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.sql.Timestamp;

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
	
	public static BufferedReader getFileOutput(String filename){
		try {
			return new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException e) {
			IO.printlnDebug(null, "File could not be loaded: "+filename);
		}
		return null;
	}
	
	public static boolean deleteGapList(String filename){
		return new File(filename).delete();
	}
	
	public static BufferedWriter getFileInput(String filename){
		try {
			return new BufferedWriter(new FileWriter(filename));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static String[] getGapLists(String directory){
		IO.printlnDebug(null, "getting gap lists...");
		String[] result = null;
		File dir = new File(directory);
		File[] gaplists = dir.listFiles((File f,String s) -> {if (s.contains(".jb")) return true; else return false;});
		if (gaplists != null){
			result = new String[gaplists.length];
			for (int i = 0; i < result.length; i++){
				result[i] = (gaplists[i].getName().substring(0,gaplists[i].getName().length() - 3));
			}
		}
		return result;
	}
	
	public static String[] readOutGapList(String filename){
		BufferedReader reader = getFileOutput(filename);
		try {
			long max = reader.lines().count();
			reader.close();
			String[] title = new String[(int)max];
			IO.printlnDebug(null, "Titles to output: "+max);
			reader = getFileOutput(filename);
			int current = 0;
			String url = reader.readLine();
			while (url != null || url == ""){
				String[] splitted = url.split(";");
				title[current] = splitted[1];
				current++;
				url = reader.readLine();
			}
			reader.close();
			IO.printlnDebug(null, "Finished sucessfully");
			return title;
		} catch (IOException e) {
			IO.printlnDebug(null, "ERROR: Could not read out title of the gaplist "+filename);
		}
		return null;
		
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
