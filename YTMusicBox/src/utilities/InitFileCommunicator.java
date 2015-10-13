package utilities;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.HashMap;

public class InitFileCommunicator {
	

	
	private HashMap<ColumnType,String> fileValues = new HashMap<ColumnType,String>();
	
	//private static String INITFILENAME = "jukepi.ini";
	
	//private String[] defaultValues = {"gaplist.jb","player","playback","debug","gaplist",""+true,""+Long.MAX_VALUE,""+1,"22222"};
	
	private String dir;

	private String filename;
	
	private void fillDefaultValues(){
		for (ColumnType t : ColumnType.values()){
			fileValues.put(t, t.getDefaultValue());
		}
	}
	
	public InitFileCommunicator(String directory,String filename) {
		this.dir = directory;
		this.filename = filename;
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
		BufferedWriter file = IO.getFileInput(dir+filename);
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
		BufferedReader file = IO.getFileOutput(dir+filename);
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
