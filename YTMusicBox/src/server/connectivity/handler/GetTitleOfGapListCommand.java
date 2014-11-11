package server.connectivity.handler;

import java.io.BufferedWriter;

import network.MessageType;
import server.YTJBServer;

public class GetTitleOfGapListCommand extends Command {

	private YTJBServer server;
	private String filename;
	
	public GetTitleOfGapListCommand(BufferedWriter out,YTJBServer server,String filename) {
		super(out);
		this.server = server;
		this.filename = filename;
	}

	@Override
	public boolean handle() {
		String[] title = server.readOutGapList(filename);
		StringBuilder result = new StringBuilder();
		for (String t: title){
			result.append(t+MessageType.SEPERATOR);
		}
		response(result.toString());
		return true;
	}

}
