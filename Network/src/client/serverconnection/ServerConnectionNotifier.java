package client.serverconnection;

/**
 * The Notifier for the ServerConnection.
 * @author Mellich
 * @version 1.0
 */
public interface ServerConnectionNotifier {
	
	/**
	 * The Method, that will forward the Message sent by the Server.
	 * @param notifyType	The Type of notify, that was sent by the Server.
	 * @param args	The possible arguments that will be sent within the answer.
	 * @since 1.0
	 */
	public void onNotify(int notifyType, String[] args);
}
