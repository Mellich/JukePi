package client.listener;

/**
 * A Listener, that will be notified whenever the Song was seeked forwards or backwards. Will be
 * needed for all Players.
 * @author Mellich
 * @version 1.0
 */
public interface SeekNotificationListener {
	
	/**
	 * The Method, that will be called, whenever a Song was seeked.
	 * @param forward	{@code true}, if the Song was seeked forwards, {@code false} else.
	 * @since 1.0
	 */
	public void onSeekNotify(boolean forward);
}
