package server.connectivity.handler;

import java.net.Socket;

import server.MusicTrack;
import server.player.TrackScheduler;

public class GetCurrentTrackCommandHandler extends CommandHandler {

	private TrackScheduler scheduler;
	
	public GetCurrentTrackCommandHandler(Socket s,TrackScheduler scheduler) {
		super(s);
		this.scheduler = scheduler;
	}

	@Override
	public boolean handle() {
		MusicTrack current = scheduler.getCurrentTrack();
		if (current != null){
			sendMessage(current.getTitle());
		}
		else{
			sendMessage("NOTHING");
		}
		return true;
	}

}
