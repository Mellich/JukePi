package server.connectivity.commands;

import java.io.BufferedWriter;

import server.player.TrackScheduler;

public class SeekForwardCommand extends Command {

	private TrackScheduler scheduler;

	public SeekForwardCommand(BufferedWriter out, int messageType,TrackScheduler scheduler) {
		super(out, messageType);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		response(""+scheduler.seekForward());
		return true;
	}

}
