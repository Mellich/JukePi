package server.connectivity.commands;

import java.io.BufferedWriter;

import network.MessageType;
import utilities.IO;

public class UnknownCommand extends Command {

	private String command;
	
	public UnknownCommand(BufferedWriter out,String command) {
		super(out);
		this.command = command;
	}

	@Override
	public boolean handle() {
		notify(MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY);
		IO.printlnDebug(this, "ERROR: Sended command could not be handled! "+command);
		return false;
	}

}
