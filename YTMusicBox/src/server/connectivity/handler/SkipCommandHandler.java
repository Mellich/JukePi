package server.connectivity.handler;

import java.net.Socket;

import server.player.TrackScheduler;
import utilities.IO;

public class SkipCommandHandler extends CommandHandler {

	private TrackScheduler trackScheduler;
	
	public SkipCommandHandler(Socket s, TrackScheduler scheduler) {
		super(s);
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
