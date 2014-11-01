package server;

import java.net.Socket;

import server.connectivity.handler.CommandHandler;
import server.player.TrackScheduler;

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
		sendMessage("Track wurde übersprungen");
		return true;
	}

}
