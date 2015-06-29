package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;
import server.connectivity.Connection;

public class RegisterCommand extends Command {
	
	private Connection parent;
	private YTJBServer server;
	private String[] args;

	public RegisterCommand(BufferedWriter out, int messageType, Connection parent, YTJBServer server, String[] args) {
		super(out, messageType);
		this.parent = parent;
		this.server = server;
		this.args = args;
	}

	@Override
	public boolean handle() {
		 server.registerNotifiable(parent);
		 parent.setMACAddress(Long.parseLong(args[1]));
		 parent.setVersion(Long.parseLong(args[2]));
		 this.response(""+server.getVersion());
		 return true;
	}

}
