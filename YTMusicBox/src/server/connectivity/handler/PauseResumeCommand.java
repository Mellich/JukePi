package server.connectivity.handler;

import java.io.BufferedWriter;

import server.YTJBServer;
import server.player.TrackScheduler;
import utilities.IO;

public class PauseResumeCommand extends Command {
	
	private TrackScheduler trackScheduler;

	public PauseResumeCommand(BufferedWriter out, YTJBServer server) {
		super(out);
		trackScheduler = server.getScheduler();
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "pause/resume current playback");
		trackScheduler.pauseResume();
		response(""+true);
		return true;
	}

}
