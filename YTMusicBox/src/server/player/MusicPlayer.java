package server.player;

import server.MusicTrack;

public interface MusicPlayer{
	public void play(MusicTrack track);
	public void skip();
	public void pauseResume();
}
