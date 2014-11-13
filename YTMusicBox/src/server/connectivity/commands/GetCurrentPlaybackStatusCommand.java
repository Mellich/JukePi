package server.connectivity.commands;

import java.io.BufferedWriter;

import server.player.TrackScheduler;

public class GetCurrentPlaybackStatusCommand extends Command {

	private TrackScheduler scheduler;
	
	public GetCurrentPlaybackStatusCommand(BufferedWriter out,TrackScheduler scheduler) {
		super(out);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		response(""+scheduler.isPlaying());
		return true;
	}

}
