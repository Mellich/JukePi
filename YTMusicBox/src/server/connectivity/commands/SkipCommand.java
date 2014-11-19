package server.connectivity.commands;

import java.io.BufferedWriter;

import server.player.TrackScheduler;
import utilities.IO;

public class SkipCommand extends Command {

	private TrackScheduler trackScheduler;
	
	public SkipCommand(BufferedWriter out,int messageType, TrackScheduler scheduler) {
		super(out, messageType);
		trackScheduler = scheduler;
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "skipping current playback");
		response(""+trackScheduler.skip());
		return true;
	}

}
