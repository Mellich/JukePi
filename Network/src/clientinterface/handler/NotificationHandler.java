package clientinterface.handler;

import clientwrapper.ClientNotifyWrapper;


public class NotificationHandler implements Runnable {

	private ClientNotifyWrapper notifyListener;
	private int messageType;

	public NotificationHandler(ClientNotifyWrapper notifyListener,int messageType) {
		this.notifyListener = notifyListener;
		this.messageType = messageType;
	}
	
	@Override
	public void run() {
		notifyListener.onNotify(messageType);
	}

}
