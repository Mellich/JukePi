package server.connectivity.handler;

import java.io.BufferedWriter;

import network.MessageType;
import server.YTJBServer;
import server.player.TrackScheduler;
import utilities.IO;

public class PauseResumeCommand extends Command {
	
	private TrackScheduler trackScheduler;
	private YTJBServer server;

	public PauseResumeCommand(BufferedWriter out, YTJBServer server) {
		super(out);
		trackScheduler = server.getScheduler();
		this.server = server;
	}

	@Override
	public boolean handle() {
		IO.printlnDebug(this, "pause/resume current playback");
		trackScheduler.pauseResume();
		response(""+true);
		server.notifyClients(MessageType.PAUSERESUMENOTIFY);
		return true;
	}

}
