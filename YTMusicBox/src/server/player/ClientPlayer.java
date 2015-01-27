package server.player;

import server.MusicTrack;
import server.YTJBServer;

public class ClientPlayer implements MusicPlayer {
	
	private YTJBServer server;
	private TrackScheduler scheduler;
	private boolean playing;
	
	public ClientPlayer(YTJBServer server,TrackScheduler scheduler) {
		this.server = server;
		this.playing = false;
		this.scheduler = scheduler;
	}

	@Override
	public void play(MusicTrack track) {
		this.playing = true;
		server.waitForPlayerToFinish();
	}

	@Override
	public boolean skip() {
		scheduler.interrupt();
		return true;
	}

	@Override
	public boolean pauseResume() {
		this.playing = !playing;
		return true;
	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public boolean seekForward() {
		return true;
	}

	@Override
	public boolean seekBackward() {
		return true;
	}

}
