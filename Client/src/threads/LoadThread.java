package threads;

import javax.swing.JLabel;
import javax.swing.JList;

import connection.Collector;

/**
 * The Thread to load a specified Gaplist.
 * @author Haeldeus
 *
 */
public class LoadThread extends Thread{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Gaplists as a selectable List.
	 */
	private JList<String> gaplists;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the Thread.
	 * @param c	The Collector that will send the Messages.
	 * @param gaplists	The Gaplists as a selectable List.
	 * @param fail	The Label, that will display possible Messages.
	 */
	public LoadThread(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		
		fail.setText("Pending Server");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		
		if (gaplists.getSelectedValue() != null)
			if (c.loadGaplist(gaplists.getSelectedValue()))
				fail.setText("Gaplist " + gaplists.getSelectedValue() + ".jb loaded successfully");
			else
				fail.setText("Loading Gaplist "+ gaplists.getSelectedValue() + ".jb failed");
		else
			fail.setText("Please select a Gaplist");
		
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}
