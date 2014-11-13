package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GetCurrentGapListNameCommand extends Command {

	private YTJBServer server;
	
	public GetCurrentGapListNameCommand(BufferedWriter out, YTJBServer server) {
		super(out);
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(server.getCurrentGapListName());
		return true;
	}

}
