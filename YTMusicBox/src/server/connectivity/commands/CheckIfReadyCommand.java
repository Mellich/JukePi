package server.connectivity.commands;

import java.io.BufferedWriter;

public class CheckIfReadyCommand extends Command {

	public CheckIfReadyCommand(BufferedWriter out,int messageType) {
		super(out, messageType);
	}

	@Override
	public boolean handle() {
		response(""+true);
		return true;
	}

}
