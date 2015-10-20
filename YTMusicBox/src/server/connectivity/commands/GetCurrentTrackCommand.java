package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.MusicTrack;
import server.connectivity.Connection;
import server.player.TrackScheduler;

public class GetCurrentTrackCommand extends Command {

	private TrackScheduler scheduler;
	private Connection parent;
	
	public GetCurrentTrackCommand(BufferedWriter out,int messageType,TrackScheduler scheduler,Connection parent) {
		super(out, messageType);
		this.scheduler = scheduler;
		this.parent = parent;
	}

	@Override
	public boolean handle() {
		MusicTrack current = scheduler.getCurrentTrack();
		if (current != null){
			if (parent.getVersion() >= 901L)
				response(current.getTrackID()+MessageType.SEPERATOR+current.getTitle()+MessageType.SEPERATOR+current.getShortURL());
			else response(current.getTitle());
		}
		else{
			response(-1+MessageType.SEPERATOR+"NOTHING"+MessageType.SEPERATOR+"NOTHING");
		}
		return true;
	}

}
