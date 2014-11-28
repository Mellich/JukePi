package clientinterface.handler;

import clientwrapper.ClientNotifyWrapper;


public class NotificationHandler implements Runnable {

	private ClientNotifyWrapper notifyListener;
	private int messageType;
	private String[] args;

	public NotificationHandler(ClientNotifyWrapper notifyListener,int messageType, String[] args) {
		this.notifyListener = notifyListener;
		this.messageType = messageType;
		this.args = args;
	}
	
	@Override
	public void run() {
		String[] arguments = new String[args.length - 1];
		for (int i = 1; i < args.length;i++){
			arguments[i-1] = args[i];
		}
		notifyListener.onNotify(messageType,arguments);
	}

}
