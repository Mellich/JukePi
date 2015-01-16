package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GetCurrentPlayerCountCommand extends Command {
	
	private YTJBServer server;

	public GetCurrentPlayerCountCommand(BufferedWriter out, int messageType, YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(""+server.getCurrentPlayerCount());
		return false;
	}

}
