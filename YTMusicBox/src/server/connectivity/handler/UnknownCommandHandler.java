package server.connectivity.handler;

import java.net.Socket;

import network.MessageType;
import utilities.IO;

public class UnknownCommandHandler extends CommandHandler {

	private String command;
	
	public UnknownCommandHandler(Socket s,String command) {
		super(s);
		this.command = command;
	}

	@Override
	public boolean handle() {
		notify(MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY);
		IO.printlnDebug(this, "ERROR: Sended command could not be handled! "+command);
		return false;
	}

}
