package server.connectivity.handler;

import java.net.Socket;

import utilities.IO;

public class UnknownCommandHandler extends CommandHandler {

	private String command;
	
	public UnknownCommandHandler(Socket s,String command) {
		super(s);
		this.command = command;
	}

	@Override
	public boolean handle() {
		sendMessage("ERROR: Unknown or not yet implemented command");
		IO.printlnDebug(this, "ERROR: Sended command could not be handled! "+command);
		return false;
	}

}
