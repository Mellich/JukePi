package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;
import server.player.TrackScheduler;
import utilities.IO;

public class PauseResumeCommand extends Command {
	
	private TrackScheduler trackScheduler;

	public PauseResumeCommand(BufferedWriter out,int messageType, YTJBServer server) {
		super(out, messageType);
		trackScheduler = server.getScheduler();
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "pause/resume current playback");
		response(""+trackScheduler.pauseResume());
		return true;
	}

}
