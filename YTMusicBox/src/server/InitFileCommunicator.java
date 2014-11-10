package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import utilities.IO;

public class InitFileCommunicator {
	
	private static String INITFILENAME = "init";
	
	public static final String DEFAULTGAPLISTNAME  = "gaplist.jb";
	
	private static final String STARTUPGAPLISTROW = "STARTUPGAPLIST";
	private static final String AUTOPLAYROW = "AUTOPLAY";
	
	private String startUpGapList = DEFAULTGAPLISTNAME;
	private boolean autoPlay = true;
	private String dir;
	
	public void setStartUpGapList(String filename){
		this.startUpGapList = filename;
		saveInitFile();
	}
	
	public InitFileCommunicator(String directory) {
		this.dir = directory;
		this.startUpGapList = DEFAULTGAPLISTNAME;
		loadInitFile();
	}
	
	public String getStartUpGapList(){
		return this.startUpGapList;
	}
	
	public void setAutoPlay(boolean autoPlay){
		this.autoPlay = autoPlay;
		saveInitFile();
	}
	
	public boolean getAutoPlay(){
		return this.autoPlay;
	}
	
	private void saveInitFile(){
		BufferedWriter file = IO.getFileInput(dir+INITFILENAME);
		try {
			file.write(STARTUPGAPLISTROW+"="+this.startUpGapList);
			file.newLine();
			file.write(AUTOPLAYROW+"="+this.autoPlay);
			file.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: Could not save the init file!");
		}
	}
	
	private void loadInitFile(){
		BufferedReader file = IO.getFileOutput(dir+INITFILENAME);
		String line;
		try {
			line = file.readLine();
			while (line != null){
				line.replace(" ", "");
				String[] splitted = line.split("=");
				if (splitted[0].equals(STARTUPGAPLISTROW))
					this.startUpGapList = splitted[1];
				else if (splitted[0].equals(AUTOPLAYROW))
					this.autoPlay = Boolean.getBoolean(splitted[1]);
				line = file.readLine();
			}
			file.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: could not load the init file");
			this.saveInitFile();
		}
	}
}
