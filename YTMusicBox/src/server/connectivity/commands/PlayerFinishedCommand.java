package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class PlayerFinishedCommand extends Command {

	private YTJBServer server;

	public PlayerFinishedCommand(BufferedWriter out,int messageType,YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		server.playerHasFinished();
		response(""+true);
		return true;
	}

}
