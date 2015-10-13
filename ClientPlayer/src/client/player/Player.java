package client.player;

public interface Player {

	public void play(String track);

	public boolean skip();

	public boolean pause();
	
	public boolean resume();
	
	public boolean isPlaying();

	public boolean seekForward();

	public boolean seekBackward();
	
	public void destroy();

}