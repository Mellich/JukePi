package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class VoteForSongCommand extends Command {

	private long mac;
	private long trackID;
	private YTJBServer server;

	public VoteForSongCommand(BufferedWriter out, int messageType,YTJBServer server, long mac, long trackID) {
		super(out, messageType);
		this.mac = mac;
		this.trackID = trackID;
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(""+server.addVote(trackID, mac));
		return true;
	}

}
