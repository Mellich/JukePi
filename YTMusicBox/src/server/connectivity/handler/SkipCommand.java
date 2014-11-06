package server.connectivity.handler;

import java.io.BufferedWriter;

import server.player.TrackScheduler;
import utilities.IO;

public class SkipCommand extends Command {

	private TrackScheduler trackScheduler;
	
	public SkipCommand(BufferedWriter out, TrackScheduler scheduler) {
		super(out);
		trackScheduler = scheduler;
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "skipping current playback");
		trackScheduler.skip();
		response(""+true);
		return true;
	}

}
