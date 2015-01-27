package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GetCurrentClientCountCommand extends Command {
	
	private YTJBServer server;

	public GetCurrentClientCountCommand(BufferedWriter out, int messageType, YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(""+server.getCurrentClientCount());
		return false;
	}

}
