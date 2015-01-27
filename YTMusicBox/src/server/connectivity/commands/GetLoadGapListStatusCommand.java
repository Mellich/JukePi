package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;

public class GetLoadGapListStatusCommand extends Command {

	private YTJBServer server;

	public GetLoadGapListStatusCommand(BufferedWriter out, int messageType,YTJBServer server) {
		super(out, messageType);
		this.server  =server;
	}

	@Override
	public boolean handle() {
		response(""+server.getCurrentLoadedGapListTracksCount()+MessageType.SEPERATOR+server.getMaxLoadedGapListTracksCount());
		return true;
	}

}
