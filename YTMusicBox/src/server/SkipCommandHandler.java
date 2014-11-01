package server;

import java.net.Socket;

import server.connectivity.handler.CommandHandler;
import server.player.YTTrackScheduler;

public class SkipCommandHandler extends CommandHandler {

	private YTTrackScheduler trackScheduler;
	
	public SkipCommandHandler(Socket s, YTTrackScheduler scheduler) {
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
