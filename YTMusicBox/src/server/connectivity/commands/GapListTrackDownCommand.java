package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GapListTrackDownCommand extends Command {

	private long trackID;
	private YTJBServer server;
	
	public GapListTrackDownCommand(BufferedWriter out,int messageType, YTJBServer server,long trackID) {
		super(out, messageType);
		this.server = server;
		this.trackID = trackID;
	}

	@Override
	public boolean handle() {
		response(""+server.switchTrackPosition(trackID,false));
		return true;
	}

}
