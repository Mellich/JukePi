package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;
import server.connectivity.Connection;

public class SetAsPlayerCommand extends Command {

	private Connection connection;
	private YTJBServer server;

	public SetAsPlayerCommand(BufferedWriter out, int messageType,YTJBServer server,Connection connection) {
		super(out, messageType);
		this.connection = connection;
		this.server = server;
	}

	@Override
	public boolean handle() {
		server.registerPlayer(connection);
		response(""+true);
		return true;
	}

}
