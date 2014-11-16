package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class PlayerFinishedCommand extends Command {

	private YTJBServer server;

	public PlayerFinishedCommand(BufferedWriter out,YTJBServer server) {
		super(out);
		this.server = server;
	}

	@Override
	public boolean handle() {
		server.playerHasFinished();
		response(""+true);
		return true;
	}

}
