package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class AddSongToOtherListCommand extends Command {
	
	private YTJBServer server;
	private long trackID;

	public AddSongToOtherListCommand(BufferedWriter out, int messageType, YTJBServer server,long trackID) {
		super(out, messageType);
		this.server = server;
		this.trackID = trackID;
	}

	@Override
	public boolean handle() {
		response(""+server.addSongToOtherList(trackID));
		return true;
	}

}
