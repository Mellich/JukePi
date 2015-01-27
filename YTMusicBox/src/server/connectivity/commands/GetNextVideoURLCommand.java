package server.connectivity.commands;

import java.io.BufferedWriter;

import server.MusicTrack;
import server.player.TrackScheduler;

public class GetNextVideoURLCommand extends Command {

	private TrackScheduler scheduler;

	public GetNextVideoURLCommand(BufferedWriter out,int messageType,TrackScheduler scheduler) {
		super(out, messageType);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		MusicTrack m = scheduler.getCurrentTrack();
		if (m != null)
			response(m.getVideoURL());
		else response("");
		return true;
	}

}
