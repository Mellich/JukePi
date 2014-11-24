package utilities;

import java.io.BufferedReader;
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
	static public String[] parseShortURLToVideoURLAndTitle(String url) throws IOException{
		IO.printlnDebug(null, "waiting for output url...");
		String[] result = new String[2];
		Process parseProcess = new ProcessBuilder("youtube-dl","--max-quality","22","-e","-g", url).start();
		BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
		result[0] = parseInput.readLine();
		result[1] = parseInput.readLine();
		parseInput.close();
		return result;
	}
	
	/**parses the direct video url of the link with youtube-dl
	 * 
	 * @param url the link to parse
	 * @return  the direct video url in a string
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public String parseShortURLToVideoURL(String url) throws IOException{
		IO.printlnDebug(null, "waiting for output url...");
		String result = null;
		Process parseProcess = new ProcessBuilder("youtube-dl","--max-quality","22","-g", url).start();
		BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
		result = parseInput.readLine();
		parseInput.close();
		return result;
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
	
	static public Process startPlayer(int port){
		try {
			return new ProcessBuilder("java","-jar","-Dcom.sun.javafx.transparentFramebuffer=true","clientplayer.jar","localhost",""+port).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
