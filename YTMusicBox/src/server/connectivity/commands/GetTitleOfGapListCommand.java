package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;

public class GetTitleOfGapListCommand extends Command {

	private YTJBServer server;
	private String filename;
	
	public GetTitleOfGapListCommand(BufferedWriter out,int messageType,YTJBServer server,String filename) {
		super(out, messageType);
		this.server = server;
		this.filename = filename;
	}

	@Override
	public boolean handle() {
		String[] title = server.readOutGapList(filename+".jb");
		StringBuilder result = new StringBuilder();
		for (String t: title){
			result.append(t+MessageType.SEPERATOR);
		}
		response(result.toString());
		return true;
	}

}
