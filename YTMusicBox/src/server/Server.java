package server;

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
	
	/**
	 * gets the current working directory as a absolute path
	 * @return the current working directory
	 */
	public String getWorkingDir();
	
	/**
	 * gets the current count of clients that are connected to the server and declared themselves as notifiable
	 * @return count of clients
	 */
	public int getCurrentClientCount();
	
	/**
	 * gets the count of current connected players to the server
	 * @return count of connected players
	 */
	public int getCurrentPlayerCount();	
}
