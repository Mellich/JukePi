package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;

public class GetGapListsCommand extends Command {

	private YTJBServer server;
	
	public GetGapListsCommand(BufferedWriter out,int messageType,YTJBServer server) {
		super(out, messageType);
		this.server = server;
	}

	@Override
	public boolean handle() {
		String[] gaplists = server.getGapListNames();
		StringBuilder message = new StringBuilder();
		for (String name : gaplists){
			message.append(name+MessageType.SEPERATOR);
		}
		response(message.toString());
		return true;
	}

}
