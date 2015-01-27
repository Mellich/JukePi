package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GetCurrentGapListNameCommand extends Command {

	private YTJBServer server;
	
	public GetCurrentGapListNameCommand(BufferedWriter out,int messageType, YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(server.getCurrentGapListName());
		return true;
	}

}
