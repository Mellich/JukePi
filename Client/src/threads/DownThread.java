package threads;

import javax.swing.JList;

import connection.Collector;

/**
 * The Thread to change the position of a Track in the Gaplist downwards.
 * @author Haeldeus
 *
 */
public class DownThread extends Thread{

	/**
	 * The Gaplist as a selectable List.
	 */
	private JList<String> gaplist;
	
	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the Thread.
	 * @param gaplist	The Gaplist as a selectable List.
	 * @param c	The Collector that will send the Messages.
	 */
	public DownThread(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		int index = gaplist.getSelectedIndex();
		c.moveTrackDown(index);
		try {Thread.sleep(100);} catch (InterruptedException e ) {}
		gaplist.setSelectedIndex(index+1);
	}
}
