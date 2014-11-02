package server.connectivity.handler;

import java.net.Socket;

import network.MessageType;
import server.YTJBServer;
import server.player.TrackScheduler;
import utilities.IO;

public class PauseResumeCommandHandler extends CommandHandler {
	
	private TrackScheduler trackScheduler;
	private YTJBServer server;

	public PauseResumeCommandHandler(Socket s, YTJBServer server) {
		super(s);
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
