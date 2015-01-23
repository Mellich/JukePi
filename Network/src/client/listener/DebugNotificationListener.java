package client.listener;

/**Listens to notifications by the server that are not necessary for client purposes but for 
 * network server debugging and info screening
 * 
 * @author mellich
 *
 */
public interface DebugNotificationListener {
	public void onClientCountChangedNotify(int newClientCount);
	public void onPlayerCountChangedNotify(int newPlayerCount);
	public void onNewOutput(String output);
}
