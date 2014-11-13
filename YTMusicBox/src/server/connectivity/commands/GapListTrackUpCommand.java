package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;
import utilities.IO;

public class GapListTrackUpCommand extends Command {

	private int index;
	private YTJBServer server;
	
	public GapListTrackUpCommand(BufferedWriter out, YTJBServer server,int index) {
		super(out);
		this.server = server;
		this.index = index;
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "Setting track one step up");
		response(""+server.switchWithUpper(index));
		return true;
	}

}
