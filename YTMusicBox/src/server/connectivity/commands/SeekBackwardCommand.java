package server.connectivity.commands;

import java.io.BufferedWriter;

import server.player.TrackScheduler;

public class SeekBackwardCommand extends Command {

	private TrackScheduler scheduler;

	public SeekBackwardCommand(BufferedWriter out, int messageType,TrackScheduler scheduler) {
		super(out, messageType);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		response(""+scheduler.seekBackward());
		return true;
	}

}
