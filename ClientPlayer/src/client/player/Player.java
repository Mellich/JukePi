package client.player;

public interface Player {

	public abstract void play(String track);

	public abstract boolean skip();

	public abstract boolean pauseResume();

	public abstract boolean isPlaying();

	public abstract boolean seekForward();

	public abstract boolean seekBackward();

}