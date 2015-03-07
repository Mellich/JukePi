package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class RemoveVoteCommand extends Command {

	private YTJBServer server;
	private long mac;

	public RemoveVoteCommand(BufferedWriter out, int messageType,YTJBServer server,long mac) {
		super(out, messageType);
		this.server = server;
		this.mac = mac;
	}

	@Override
	public boolean handle() {
		response(""+server.removeVote(mac));
		return true;
	}

}
