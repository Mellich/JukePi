package server.connectivity.commands;

import java.io.BufferedWriter;

import server.player.TrackScheduler;

public class GetNextVideoURLCommand extends Command {

	private TrackScheduler scheduler;

	public GetNextVideoURLCommand(BufferedWriter out,TrackScheduler scheduler) {
		super(out);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		response(scheduler.getCurrentTrack().getVideoURL());
		return true;
	}

}
