package server.connectivity.commands;

import java.io.BufferedWriter;

public class NotifyClientCommand extends Command {

	private int notification;
	
	public NotifyClientCommand(BufferedWriter out,int notification) {
		super(out);
		this.notification = notification;
	}

	@Override
	public boolean handle() {
		notify(notification);
		return false;
	}

}
