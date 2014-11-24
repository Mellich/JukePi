package threads;

import javax.swing.JList;

import connection.Collector;

/**
 * A Thread to change the Position of a Track in the Gaplist upwards.
 * @author Haeldeus
 *
 */
public class UpThread extends Thread{

	/**
	 * The Gaplist as a selectable List.
	 */
	private JList<String> gaplist;
	
	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the Thread.
	 * @param gaplist	The Gaplist as a selectable List.
	 * @param c	The Collector, that will send the Messages.
	 */
	public UpThread(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		int index = gaplist.getSelectedIndex();
		c.moveTrackUp(index);
		try {Thread.sleep(100);} catch (InterruptedException e ) {}
		gaplist.setSelectedIndex(index-1);
	}
}
