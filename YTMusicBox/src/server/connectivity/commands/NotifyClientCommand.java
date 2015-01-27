package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;

public class NotifyClientCommand extends Command {

	private int notification;
	private String[] args;
	private static volatile int nextTrackNotifyCount = 0;
	
	public NotifyClientCommand(BufferedWriter out,int messageType,int notification,String[] args) {
		super(out, notification);
		this.notification = notification;
		this.args = args;
		if (notification == MessageType.NEXTTRACKNOTIFY)
			nextTrackNotifyCount++;
	}

	@Override
	public boolean handle() {
		StringBuilder builder = new StringBuilder();
		for (String s : args){
			builder.append(MessageType.SEPERATOR+s);
		}
		if (notification == MessageType.NEXTTRACKNOTIFY){
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (nextTrackNotifyCount == 1)
				notify(notification,builder.toString());
			if (notification == MessageType.NEXTTRACKNOTIFY)
				nextTrackNotifyCount--;
		}
		else{
			notify(notification,builder.toString());			
		}
		return true;
	}

}
