package client.serverconnection.impl;

/**
 * A Class, that will save the LoadStatus of the Gaplist.
 * @author Mellich
 * @version 1.0
 */
public class LoadGapListStatus{
	
	/**
	 * The amount of loaded Tracks.
	 */
	private int loaded = 0;
	
	/**
	 * The amount of Tracks, that have to be loaded.
	 */
	private int max = 0;
	
	/**
	 * Creates a new Instance of the LoadStatus.
	 * @param loaded	The amount of loaded Tracks.
	 * @param max	The amount of Track, that have to be loaded.
	 * @since 1.0
	 */
	protected LoadGapListStatus(int loaded, int max){
		this.loaded = loaded;
		this.max = max;
	}
	
	/**
	 * Returns the amount of loaded Tracks.
	 * @return	The amount of loaded Tracks.
	 * @since 1.0
	 */
	public int getLoadedTrackCount(){
		return loaded;
	}
	
	/**
	 * Returns the amount of Tracks, that have to be loaded.
	 * @return	The amount of Tracks, that have to be loaded.
	 * @since 1.0
	 */
	public int getMaxTrackCount(){
		return max;
	}
}
