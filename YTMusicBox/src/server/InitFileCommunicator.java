package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

import utilities.IO;

public class InitFileCommunicator {
	
	private static String INITFILENAME = "init";
	
	public static final String DEFAULTGAPLISTNAME  = "gaplist.jb";
	private static final String DEFAULTPLAYERPW = "player";
	private static final String DEFAULTPLAYBACKPW = "playback";
	private static final String DEFAULTDEBUGPW = "debug";
	private static final String DEFAULTGAPLISTPW = "gaplist";
	
	private static final String PLAYERPWROW = "PLAYERPW";
	private static final String PLAYBACKPWROW = "PLAYBACKPW";
	private static final String DEBUGPWROW = "DEBUGPW";
	private static final String GAPLISTPWROW = "GAPLISTPW";
	private static final String STARTUPGAPLISTROW = "STARTUPGAPLIST";
	private static final String AUTOPLAYROW = "AUTOPLAY";
	
	private String startUpGapList = DEFAULTGAPLISTNAME;
	private boolean autoPlay = true;
	private String playerPW = DEFAULTPLAYERPW;
	private String playbackPW = DEFAULTPLAYBACKPW;
	private String debugPW = DEFAULTDEBUGPW;
	private String gaplistPW = DEFAULTGAPLISTPW;
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
	
	public String getPlayerPW(){
		return playerPW;
	}
	
	public String getPlaybackPW(){
		return playbackPW;
	}
	
	public String getDebugPW(){
		return debugPW;
	}
	
	public String getGaplistPW(){
		return gaplistPW;
	}
	
	public void setPlayerPW(String pw){
		this.playerPW = pw;
	}
	
	public void setPlaybackPW(String pw){
		this.playbackPW = pw;
	}
	
	public void setDebugPW(String pw){
		this.debugPW = pw;
	}
	
	public void setGaplistPW(String pw){
		this.gaplistPW = pw;
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
			file.write(PLAYERPWROW+"="+this.playerPW);
			file.newLine();
			file.write(PLAYBACKPWROW+"="+this.playbackPW);
			file.newLine();
			file.write(DEBUGPWROW+"="+this.debugPW);
			file.newLine();
			file.write(GAPLISTPWROW+"="+this.gaplistPW);
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
				int equalPos = line.indexOf("=");
				String call = line.substring(0,equalPos);
				String input = line.substring(equalPos + 1);
				if (call.equals(STARTUPGAPLISTROW))
					this.startUpGapList = input;
				else if (call.equals(AUTOPLAYROW))
					this.autoPlay = Boolean.getBoolean(input);
				else if (call.equals(PLAYERPWROW))
					this.playerPW = input;
				else if (call.equals(PLAYBACKPWROW))
					this.playbackPW = input;
				else if (call.equals(DEBUGPWROW))
					this.debugPW = input;
				else if (call.equals(GAPLISTPWROW))
					this.gaplistPW = input;
				line = file.readLine();
			}
			file.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: could not load the init file");
			this.saveInitFile();
		}
	}
}
