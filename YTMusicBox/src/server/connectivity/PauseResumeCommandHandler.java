package server.connectivity;

import java.net.Socket;

import server.IO;
import server.player.YTTrackScheduler;

public class PauseResumeCommandHandler extends CommandHandler {
	
	private YTTrackScheduler trackScheduler;

	public PauseResumeCommandHandler(Socket s, YTTrackScheduler scheduler) {
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
