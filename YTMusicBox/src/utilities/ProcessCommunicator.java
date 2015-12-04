package utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * static class that provides functions for communication with extern programs
 * @author Mellich
 *
 */
public class ProcessCommunicator {

	
	/**parses the direct video url of the link with youtube-dl
	 * 
	 * @param url the link to parse
	 * @return the title of the video and the direct video url in an array
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public String[] parseShortURLToVideoURLAndTitle(String url,String path) throws IOException{
		String[] result = new String[2];
		if ((new File(path+"youtube-dl.exe").exists() || new File(path+"youtube-dl").exists()) && !System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+path+"youtube-dl");
			Process parseProcess = new ProcessBuilder(path+"youtube-dl.exe","-e","-g", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result[0] = parseInput.readLine();
			result[1] = parseInput.readLine();
			parseInput.close();
		}else if (System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+"youtube-dl");
			Process parseProcess = new ProcessBuilder("youtube-dl","-e","-g", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result[0] = parseInput.readLine();
			result[1] = parseInput.readLine();
			parseInput.close();
		} else 	IO.printlnDebug(null, "youtube-dl not found! parsing aborted!");
		return result;
	}
	
	static public String parseShortURLToTitle(String url,String path) throws IOException{
		String result = null;
		if ((new File(path+"youtube-dl.exe").exists() || new File(path+"youtube-dl").exists()) && !System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+path+"youtube-dl");
			Process parseProcess = new ProcessBuilder(path+"youtube-dl.exe","-e", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result = parseInput.readLine();
			parseInput.close();
		}else if (System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+"youtube-dl");
			Process parseProcess = new ProcessBuilder("youtube-dl","-e", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result = parseInput.readLine();
			parseInput.close();
		} else 	IO.printlnDebug(null, "youtube-dl not found! parsing aborted!");
		return result;
	}
	
	/**parses the direct video url of the link with youtube-dl
	 * 
	 * @param url the link to parse
	 * @return  the direct video url in a string
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public String parseShortURLToVideoURL(String url, String path) throws IOException{
		String result = null;
		if ((new File(path+"youtube-dl.exe").exists() || new File(path+"youtube-dl").exists()) && !System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+path+"youtube-dl");
			Process parseProcess = new ProcessBuilder(path+"youtube-dl","-g", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result = parseInput.readLine();
			parseInput.close();
		}else if (System.getProperty("os.name").equals("Linux")){
			IO.printlnDebug(null, "Using Youtube-dl: "+"youtube-dl");
			Process parseProcess = new ProcessBuilder("youtube-dl","-g", url).start();
			BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
			result = parseInput.readLine();
			parseInput.close();
		} else 	IO.printlnDebug(null, "youtube-dl not found! paring aborted!");
		return result;
	}
	
	public static boolean updateYoutubeDL(String path){
		IO.printlnDebug(null, "Updating youtube-dl... please wait...");
		if ((new File(path+"youtube-dl.exe").exists() || new File(path+"youtube-dl").exists()) && !System.getProperty("os.name").equals("Linux")){
			try {
				Process updateProcess = new ProcessBuilder(path+"youtube-dl","-U").start();
				BufferedReader updateInput = new BufferedReader(new InputStreamReader(updateProcess.getInputStream()));
				while (updateProcess.isAlive()){
					String out = updateInput.readLine();
					if (out != null)
						IO.printlnDebug(null,out);
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			IO.printlnDebug(null, "youtube-dl could not be found in the working directory!");
			return false;
		}
		return true;
	}

	
	/**
	 * plays the video of the given parsed url and returns, when done
	 * 
	 * @param parsedURL the parsed youtube url that has to be played
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public Process getExternPlayerProcess(String parsedURL){
		try {
			return  new ProcessBuilder("omxplayer",parsedURL).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	static public Process startPlayer(String ownIPAddress,int port,String workingDir){
		try {
			if (new File(workingDir+"clientplayer.jar").exists() && System.getProperty("os.name").equals("Linux"))
				return new ProcessBuilder("sudo","java","-jar","-Dcom.sun.javafx.transparentFramebuffer=true",workingDir+"clientplayer.jar",ownIPAddress,""+port).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
