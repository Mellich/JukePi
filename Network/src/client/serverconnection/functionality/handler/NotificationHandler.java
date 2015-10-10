package client.serverconnection.functionality.handler;

import client.serverconnection.ServerConnectionNotifier;

/**
 * A Handler for Notifications.
 * @author Mellich
 * @version 1.0
 */
public class NotificationHandler implements InputHandler {

	/**
	 * The Notifier for the ServerConnection.
	 */
	private ServerConnectionNotifier notifyListener;
	
	/**
	 * The Message Type of the Notification.
	 */
	private int messageType;
	
	/**
	 * The arguments of the notification.
	 */
	private String[] args;

	/**
	 * Creates a new Handler.
	 * @param notifyListener	The Notifier for the ServerConnection.
	 * @param messageType	The Message Type of the Notification.
	 * @param args	The arguments of the notification.
	 */
	public NotificationHandler(ServerConnectionNotifier notifyListener,int messageType, String[] args) {
		this.notifyListener = notifyListener;
		this.messageType = messageType;
		this.args = args;
	}

	@Override
	public void execute() {
		String[] arguments = new String[args.length - 1];
		for (int i = 1; i < args.length;i++){
			arguments[i-1] = args[i];
		}
		notifyListener.onNotify(messageType,arguments);
		
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(args[1]);
		for (int i = 2; i < args.length; i++){
			sb.append(", "+args[i]);
		}
		return "[Type: Notification, Code: "+messageType+", Arguments: "+sb+"]";
	}

}
