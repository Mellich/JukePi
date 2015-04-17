package client.serverconnection.functionality.handler;

import client.serverconnection.ServerConnectionNotifier;

/**
 * A Handler for Notifications.
 * @author Mellich
 * @version 1.0
 */
public class NotificationHandler implements Runnable {

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
	public void run() {
		for (String s: args){
			System.out.print(s+", ");
		}
		System.out.println();
		String[] arguments = new String[args.length - 1];
		for (int i = 1; i < args.length;i++){
			arguments[i-1] = args[i];
		}
		notifyListener.onNotify(messageType,arguments);
	}

}
