package server.connectivity.handler;

import java.io.BufferedWriter;

public class CheckIfReadyCommand extends Command {

	public CheckIfReadyCommand(BufferedWriter out) {
		super(out);
	}

	@Override
	public boolean handle() {
		response(""+true);
		return true;
	}

}
