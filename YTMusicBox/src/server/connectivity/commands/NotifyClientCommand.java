package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;

public class NotifyClientCommand extends Command {

	private int notification;
	private String args;
	private static volatile int nextTrackNotifyCount = 0;
	
	public NotifyClientCommand(BufferedWriter out,int messageType,int notification,String args) {
		super(out, notification);
		this.notification = notification;
		this.args = args;
		if (notification == MessageType.NEXTTRACKNOTIFY)
			nextTrackNotifyCount++;
	}

	@Override
	public boolean handle() {
		if (notification == MessageType.NEXTTRACKNOTIFY){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if (nextTrackNotifyCount == 1)
				notify(notification,args);
			if (notification == MessageType.NEXTTRACKNOTIFY)
				nextTrackNotifyCount--;			//TODO: unsafe edit. maybe semaphore necessary?
		}
		else{
			notify(notification,args);			
		}
		return true;
	}

}
