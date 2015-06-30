package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;

public class GetTitleOfGapListCommand extends Command {

	private YTJBServer server;
	private String filename;
	private long version;
	
	public GetTitleOfGapListCommand(BufferedWriter out,int messageType,YTJBServer server,String filename, long version) {
		super(out, messageType);
		this.server = server;
		this.filename = filename;
		this.version = version;
	}

	@Override
	public boolean handle() {
		String[] title = server.readOutGapList(filename+".jb");
		StringBuilder result = new StringBuilder();
		if (version >= 816L){
			for (String t: title){
				result.append(t+MessageType.SEPERATOR);
			}
		}
		else{
			for (int i = 0; i < title.length; i = i+2){
				result.append(title[i]+MessageType.SEPERATOR);
			}
		}
		response(result.toString());
		return true;
	}

}
