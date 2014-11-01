package server.connectivity.handler;

import java.net.Socket;

public class CheckIfReadyCommandHandler extends CommandHandler {

	public CheckIfReadyCommandHandler(Socket s) {
		super(s);
	}

	@Override
	public boolean handle() {
		sendMessage("READY");
		return true;
	}

}
