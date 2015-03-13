package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GapListTrackUpCommand extends Command {

	private long trackID;
	private YTJBServer server;
	
	public GapListTrackUpCommand(BufferedWriter out,int messageType, YTJBServer server,long trackID) {
		super(out, messageType);
		this.server = server;
		this.trackID = trackID;
	}

	@Override
	public boolean handle() {
		response(""+server.switchTrackPosition(trackID,true));
		return true;
	}

}
