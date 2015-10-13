package server;

/**
 * a juke box server
 * @author mellich
 *
 */
public interface Server {
	/**
	 * starts the server. This method blocks the thread that is calling it until the server was shut down 
	 * via the shutDown method!
	 * 
	 * @return true, if the server started without complications.
	 */
	public boolean startUp();
	
	/**
	 * Lets the server try to update the Youtube-DL instance in the execution directory.
	 * It is recommended to execute this command before starting up the server, so the gap list can be
	 * loaded with the newest version of youtube-dl. Outdated versions can lead to exceptions during video url parsing. 
	 * 
	 * @return true, if youtube-dl exists in the execution directory and it was updated without errors.
	 */
	public boolean updateYoutubeDL();
	
	/**
	 * let the server search for new gaplist files in the execution directory.
	 * Execute this function to read in gaplist files, that where added after start up to avoid a server restart.
	 */
	public void searchGapListFiles();
	
	/**
	 * shuts down the server
	 */
	public void shutDown();
}
