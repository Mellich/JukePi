package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;
import utilities.IO;

public class PlayerFinishedCommand extends Command {

	private YTJBServer server;

	public PlayerFinishedCommand(BufferedWriter out,int messageType,YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		server.playerHasFinished();
		IO.printlnDebug(this, "Player hat angegeben fertig zu sein...");
		response(""+true);
		return true;
	}

}
