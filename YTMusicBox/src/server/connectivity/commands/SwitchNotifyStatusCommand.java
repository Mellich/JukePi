package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.connectivity.Connection;

public class SwitchNotifyStatusCommand extends Command {

	private Connection c;
	private int message;

	public SwitchNotifyStatusCommand(BufferedWriter out, int messageType,Connection c) {
		super(out, messageType);
		this.c = c;
		this.message = messageType;
	}

	@Override
	public boolean handle() {
		switch (message){
		case MessageType.SWITCHSEEKNOTIFY: c.setAsSeekListener();
			break;
		case MessageType.SWITCHDEBUGNOTIFY: c.setAsDebugListener();
			break;
		case MessageType.SWITCHGAPLISTNOTIFY:c.setAsGapListListener();
			break;
		case MessageType.SWITCHPAUSERESUMENOTIFY:c.setAsPauseResumeListener();
			break;
		default: c.setAsDefaultListener();
		}
		return true;
	}

}
