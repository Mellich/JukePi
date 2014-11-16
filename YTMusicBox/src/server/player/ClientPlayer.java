package server.player;

import server.MusicTrack;
import server.YTJBServer;

public class ClientPlayer implements MusicPlayer {
	
	private YTJBServer server;
	private boolean playing;
	
	public ClientPlayer(YTJBServer server) {
		this.server = server;
		this.playing = false;
	}

	@Override
	public void play(MusicTrack track) {
		this.playing = true;
		server.waitForPlayerToFinish();
	}

	@Override
	public boolean skip() {
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

}
