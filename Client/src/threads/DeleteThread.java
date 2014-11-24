package threads;

import connection.Collector;

/**
 * The Thread to delete a Track from the Gaplist.
 * @author Haeldeus
 *
 */
public class DeleteThread extends Thread{

	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The index of the Track.
	 */
	private int index;
	
	/**
	 * The Constructor for the Thread.
	 * @param c	The Collector, that will send the Messages.
	 * @param index	The index of the Track.
	 */
	public DeleteThread(Collector c, int index) {
		this.c = c;
		this.index = index;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		c.deleteTrack(index);
	}
}
