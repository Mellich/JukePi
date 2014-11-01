package server.connectivity.handler;

import java.net.Socket;

import server.player.TrackScheduler;
import utilities.IO;

public class PauseResumeCommandHandler extends CommandHandler {
	
	private TrackScheduler trackScheduler;

	public PauseResumeCommandHandler(Socket s, TrackScheduler scheduler) {
		super(s);
		trackScheduler = scheduler;
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "pause/resume current playback");
		trackScheduler.pauseResume();
		sendMessage("Track wurde pausiert/angehalten");
		return true;
	}

}
