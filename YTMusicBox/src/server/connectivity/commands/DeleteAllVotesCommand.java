package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class DeleteAllVotesCommand extends Command {

	public DeleteAllVotesCommand(BufferedWriter out, int messageType, YTJBServer server) {
		super(out, messageType);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean handle() {
		// TODO Auto-generated method stub
		return false;
	}

}
