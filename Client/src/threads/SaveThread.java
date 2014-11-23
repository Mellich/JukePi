package threads;

import connection.Collector;

/**
 * The Thread to save a Gaplist.
 * @author Frederic
 *
 */
public class SaveThread extends Thread{

	/**
	 * The Collector that will send the Message.
	 */
	private Collector c;
	
	/**
	 * The Constructor that will send the Message.
	 * @param c
	 */
	public SaveThread(Collector c) {
		this.c = c;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		c.saveGaplist();
	}
}
