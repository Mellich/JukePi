package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import utilities.IO;

public class UnknownCommand extends Command {

	private String command;
	
	public UnknownCommand(BufferedWriter out,int messageType,String command) {
		super(out, messageType);
		this.command = command;
	}

	@Override
	public boolean handle() {
		response("" + MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY);
		IO.printlnDebug(this, "ERROR: Sent command could not be handled! No permissions? "+command);
		return false;
	}

}
