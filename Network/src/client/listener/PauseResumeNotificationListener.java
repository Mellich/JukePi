package client.listener;

/**
 * A Listener, that will be notified, whenever the current Playback-Status was changed. Will be
 * needed for the Master-Client and the Player.
 * @author Mellich
 * @version 1.0
 */
public interface PauseResumeNotificationListener {
	
	/**
	 * The Method, that will be called, whenever the current Playback-Status was changed.
	 * @param isPlaying	{@code true} if the Track is NOW playing, {@code false} if it's paused.
	 * @since 1.0
	 */
	public void onPauseResumeNotify(boolean isPlaying);
}
