package client.listener;

/**
 * Listens to Notifications by the Server that are not necessary for Client purposes but for 
 * Network Server debugging and Info screening.
 * @author Mellich
 * @version 1.0
 */
public interface DebugNotificationListener {
	
	/**
	 * The Method, that will be called, whenever the count of connected Clients has changed.
	 * @param newClientCount	The new amount of connected Clients.
	 * @since 1.0
	 */
	public void onClientCountChangedNotify(int newClientCount);
	
	/**
	 * The Method, that will be called, whenever the count of connected Players has changed.
	 * @param newPlayerCount	The new amount of Players.
	 * @since 1.0
	 */
	public void onPlayerCountChangedNotify(int newPlayerCount);
	
	/**
	 * The Method, that will be called, whenever a new Output-Line was created.
	 * @param output	The Line, that has to be printed.
	 * @since 1.0
	 */
	public void onNewOutput(String output);
}