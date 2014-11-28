package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;

public class NotifyClientCommand extends Command {

	private int notification;
	private String[] args;
	
	public NotifyClientCommand(BufferedWriter out,int messageType,int notification,String[] args) {
		super(out, notification);
		this.notification = notification;
		this.args = args;
	}

	@Override
	public boolean handle() {
		StringBuilder builder = new StringBuilder();
		for (String s : args){
			builder.append(MessageType.SEPERATOR+s);
		}
		notify(notification,builder.toString());
		return true;
	}

}
