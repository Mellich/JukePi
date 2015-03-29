package client.listener;

import client.serverconnection.Song;

/**
 * A Listener, that will be notified, whenever the Gaplist was changed. Will be needed 
 * for Master-Clients.
 * @author Mellich
 * @version 1.0
 */
public interface GapListNotificationListener {
	
	/**
	 * The Method, that will be called, whenever the overall count of Gaplists has changed.
	 * @param gapLists	All Saved Gaplists.
	 * @since 1.0
	 */
	public void onGapListCountChangedNotify(String[] gapLists);
	
	/**
	 * The Method, that will be called, whenever the current Gaplist was changed.
	 * @param gapListName	The Name of the new Gaplist.
	 * @since 1.0
	 */
	public void onGapListChangedNotify(String gapListName);
	
	/**
	 * The Method, that will be called, whenever the current Gaplist was edited.
	 * @param songs	All songs in the Gaplist.
	 * @since 1.0
	 */
	public void onGapListUpdatedNotify(Song[] songs);
}
