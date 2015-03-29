package client.listener;

import client.serverconnection.Song;

/**
 * The Default Listener, without any special Permissions. Will be needed for 
 * Android-Clients and the Master-Client.
 * @author Mellich
 * @version 1.0
 */
public interface DefaultNotificationListener {
	
	/**
	 * The Method that will be called, whenever the Wishlist was updated.
	 * @param songs	The new Wishlist.
	 * @since 1.0
	 */
	public void onWishListUpdatedNotify(Song[] songs);
	
	/**
	 * The Method, that will be called, whenever a new Track was started by a Player.
	 * @param title	The Title of the new Song.
	 * @param url	The URL of the new Song.
	 * @param isVideo	{@code true}, if the URL is a Video-URL, {@code false} else.
	 * @since 1.0
	 */
	public void onNextTrackNotify(String title,String url,boolean isVideo);
	
	/**
	 * The Method, that will be called, whenever the Connection to the Server was lost.
	 * @since 1.0
	 */
	public void onDisconnect();
}
