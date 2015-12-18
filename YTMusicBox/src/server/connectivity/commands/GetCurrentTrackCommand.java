package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import server.connectivity.Connection;

public class GetCurrentTrackCommand extends Command {

	private YTJBServer server;;
	private Connection parent;
	
	public GetCurrentTrackCommand(BufferedWriter out,int messageType,YTJBServer server,Connection parent) {
		super(out, messageType);
		this.server = server;
		this.parent = parent;
	}

	@Override
	public boolean handle() {
		MusicTrack current = server.getScheduler().getCurrentTrack();
		if (current != null){
			if (parent.getVersion() >= 901L)
				response(current.getTrackID()+MessageType.SEPERATOR+current.getTitle()+MessageType.SEPERATOR+current.getShortURL()+MessageType.SEPERATOR+server.getVote(parent.getMACAddress()));
			else response(current.getTitle());
		}
		else{
			response(-1+MessageType.SEPERATOR+"NOTHING"+MessageType.SEPERATOR+"NOTHING");
		}
		return true;
	}

}
