package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;
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
	 * The Label that displays responses.
	 */
	private JLabel fail;
	
	private JFrame frame;
	
	/**
	 * The Constructor for the Thread.
	 * @param gaplist	The Gaplist as a selectable List.
	 * @param c	The Collector, that will send the Messages.
	 */
	public UpThread(JList<String> gaplist, Collector c, JLabel fail, JFrame frame) {
		this.gaplist = gaplist;
		this.c = c;
		this.frame = frame;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		int index = gaplist.getSelectedIndex();
		if (c.moveTrackUp(index))
			fail.setText("Moved track successfully.");
		else
			fail.setText("Failed to move track");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		new ShowLabelThread(fail, frame).start();
		try {Thread.sleep(100);} catch (InterruptedException e ) {}
		gaplist.setSelectedIndex(index-1);
	}
}
