package server.connectivity.handler;

import java.net.Socket;

public class NotifyClientCommandHandler extends CommandHandler {

	private int notification;
	
	public NotifyClientCommandHandler(Socket s,int notification) {
		super(s);
		this.notification = notification;
	}

	@Override
	public boolean handle() {
		notify(notification);
		return false;
	}

}
