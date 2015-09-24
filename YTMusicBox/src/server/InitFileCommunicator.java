package server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

import utilities.IO;

public class InitFileCommunicator {
	
	/**
	 * The column types of the init file. 
	 * To add a new column, add a new value to this class and add a constructor with a id to the new value.
	 * The id represents the index of the default value in the defalutValues array. Add a default value there in the right position.
	 * 
	 * @author mellich
	 *
	 */
	public enum ColumnType{
		PLAYERPW(1),
		PLAYBACKPW(2), 
		DEBUGPW(3), 
		GAPLISTPW(4), 
		STARTUPGAPLIST(0), 
		AUTOPLAY(5) ,
		MAXADMINCOUNT(6),
		MAXPLAYERCOUNT(7);
		
		private int id;
		
		private ColumnType(int id){
			this.id = id;
		}
		
		public int getID(){
			return id;
		}
	}
	
	private HashMap<ColumnType,String> fileValues = new HashMap<ColumnType,String>();
	
	private static String INITFILENAME = "jukepi.ini";
	
	private String[] defaultValues = {"gaplist.jb","player","playback","debug","gaplist",""+true,""+Long.MAX_VALUE,""+1};
	
	private String dir;
	
	private void fillDefaultValues(){
		for (ColumnType t : ColumnType.values()){
			fileValues.put(t, defaultValues[t.getID()]);
		}
	}
	
	public InitFileCommunicator(String directory) {
		this.dir = directory;
		fillDefaultValues();
		loadInitFile();
	}
	
	public String getValue(ColumnType type){
		return fileValues.get(type);
	}
	
	public void setValue(ColumnType type, String value){
		fileValues.put(type, value);
		saveInitFile();
	}
	
	private void saveInitFile(){
		BufferedWriter file = IO.getFileInput(dir+INITFILENAME);
		try {
			for (ColumnType k : fileValues.keySet()){
				file.append(k.toString()+" = "+fileValues.get(k));
				file.newLine();
			}
			file.close();
			IO.printlnDebug(this, "saved init file changes!");
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
				line = line.replace(" ", "");
				int equalPos = line.indexOf("=");
				String call = line.substring(0,equalPos);
				String input = line.substring(equalPos + 1);
				fileValues.put(ColumnType.valueOf(call), input);
				line = file.readLine();
			}
			file.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: could not load the init file");
			this.saveInitFile();
		}
	}
}
