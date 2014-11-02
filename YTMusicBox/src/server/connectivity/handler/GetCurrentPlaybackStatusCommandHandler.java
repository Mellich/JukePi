package server.connectivity.handler;

import java.net.Socket;

import server.player.TrackScheduler;

public class GetCurrentPlaybackStatusCommandHandler extends CommandHandler {

	private TrackScheduler scheduler;
	
	public GetCurrentPlaybackStatusCommandHandler(Socket s,TrackScheduler scheduler) {
		super(s);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		sendMessage(""+scheduler.isPlaying());
		return true;
	}

}
