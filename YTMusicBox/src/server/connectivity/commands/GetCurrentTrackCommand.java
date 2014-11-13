package server.connectivity.commands;

import java.io.BufferedWriter;

import server.MusicTrack;
import server.player.TrackScheduler;

public class GetCurrentTrackCommand extends Command {

	private TrackScheduler scheduler;
	
	public GetCurrentTrackCommand(BufferedWriter out,TrackScheduler scheduler) {
		super(out);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		MusicTrack current = scheduler.getCurrentTrack();
		if (current != null){
			response(current.getTitle());
		}
		else{
			response("NOTHING");
		}
		return true;
	}

}
