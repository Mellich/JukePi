package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class DeleteGapListCommand extends Command {

	private YTJBServer server;
	private String filename;

	public DeleteGapListCommand(BufferedWriter out,int messageType,YTJBServer server,String filename) {
		super(out, messageType);
		this.server = server;
		this.filename = filename;
	}

	@Override
	public boolean handle() {
		response(""+server.deleteGapList(filename));
		return true;
	}

}
