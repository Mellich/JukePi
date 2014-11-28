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
		notify(MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY,"");
		IO.printlnDebug(this, "ERROR: Sended command could not be handled! "+command);
		return false;
	}

}
