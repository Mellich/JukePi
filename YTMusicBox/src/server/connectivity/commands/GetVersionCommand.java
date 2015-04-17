package server.connectivity.commands;

import java.io.BufferedWriter;

public class GetVersionCommand extends Command {

	public GetVersionCommand(BufferedWriter out, int messageType) {
		super(out, messageType);
	}

	@Override
	public boolean handle() {
		response("8.1");
		return true;
	}

}
