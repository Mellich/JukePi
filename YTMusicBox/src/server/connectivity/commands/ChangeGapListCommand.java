package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class ChangeGapListCommand extends Command {

	private String filename;
	private YTJBServer server;
	
	public ChangeGapListCommand(BufferedWriter out,int messageType, YTJBServer server,String filename) {
		super(out, messageType);
		this.filename = filename;
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(""+server.setGapList(filename));
		return true;
	}

}
