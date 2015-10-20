package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class DeleteAllVotesCommand extends Command {

	private YTJBServer server;

	public DeleteAllVotesCommand(BufferedWriter out, int messageType, YTJBServer server) {
		super(out, messageType);
		this.server = server;

	}

	@Override
	public boolean handle() {
		response(""+server.removeAllVotes());
		return true;
	}

}
