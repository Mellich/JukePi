package clientinterface.handler;

import java.util.List;

import clientinterface.listener.NotifyListener;


public class NotificationHandler implements Runnable {

	private List<NotifyListener> notifyListener;
	private int messageType;

	public NotificationHandler(List<NotifyListener> notifyListener,int messageType) {
		this.notifyListener = notifyListener;
		this.messageType = messageType;
	}
	
	@Override
	public void run() {
		for (NotifyListener l : notifyListener){
			l.handleNotify(messageType);
		}
	}

}
