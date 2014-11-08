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
	 * @return the direct video url
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public String parseStandardURL(String url) throws IOException{
		IO.printlnDebug(null, "waiting for output url...");
		Process parseProcess = new ProcessBuilder("youtube-dl","--max-quality","22","-g", url).start();
		BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
		String parsedURL = parseInput.readLine();
		parseInput.close();
		IO.printlnDebug(null, ""+parsedURL.length());
		IO.printlnDebug(null, parsedURL);
		return parsedURL;
	}
	
	/**parses the title of the video given by a link with youtube-dl
	 * 
	 * @param url the link to the video
	 * @return the title of the video
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	public static String parseTitle(String url) throws IOException{
		Process parseProcess = new ProcessBuilder("youtube-dl","-e", url).start();
		BufferedReader parseInput = new BufferedReader(new InputStreamReader(parseProcess.getInputStream()));
		IO.printlnDebug(null, "waiting for output title...");
		String parsedURL = parseInput.readLine();
		parseInput.close();
		return parsedURL;		
	}
	
	/**
	 * plays the video of the given parsed url and returns, when done
	 * 
	 * @param parsedURL the parsed youtube url that has to be played
	 * @throws IOException raised when there are issues with communicating with the extern process
	 */
	static public Process getExternPlayerProcess(String parsedURL){
		try {
			return  new ProcessBuilder("omxplayer","-o","both",parsedURL).start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
}
