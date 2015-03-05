package server.connectivity.commands;

import java.io.BufferedWriter;

import server.connectivity.Connection;

public class SetAsDebugCommand extends Command {

	private Connection c;

	public SetAsDebugCommand(BufferedWriter out, int messageType,Connection c) {
		super(out, messageType);
		this.c = c;
	}

	@Override
	public boolean handle() {
		// TODO Auto-generated method stub
		return false;
	}

}
