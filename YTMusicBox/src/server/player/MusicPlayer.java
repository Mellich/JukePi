package server.player;

import server.MusicTrack;

public interface MusicPlayer{
	public void play(MusicTrack track);
	public boolean skip();
	public boolean pauseResume();
	public boolean isPlaying();
}
