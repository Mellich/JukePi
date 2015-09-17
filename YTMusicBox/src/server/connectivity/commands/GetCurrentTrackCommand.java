package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.MusicTrack;
import server.player.TrackScheduler;

public class GetCurrentTrackCommand extends Command {

	private TrackScheduler scheduler;
	
	public GetCurrentTrackCommand(BufferedWriter out,int messageType,TrackScheduler scheduler) {
		super(out, messageType);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		MusicTrack current = scheduler.getCurrentTrack();
		if (current != null){
			response(current.getTrackID()+MessageType.SEPERATOR+current.getTitle()+MessageType.SEPERATOR+current.getShortURL());
		}
		else{
			response("NOTHING");
		}
		return true;
	}

}
