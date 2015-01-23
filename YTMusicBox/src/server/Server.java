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
	 */
	public void startUp();
	
	/**
	 * shuts down the server
	 */
	public void shutDown();
}
