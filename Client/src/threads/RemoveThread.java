package threads;

import javax.swing.JLabel;
import javax.swing.JList;

import connection.Collector;

/**
 * The Thread to remove a Track from the Gaplist.
 * @author Haeldeus
 *
 */
public class RemoveThread extends Thread{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Gaplists as a selectable list.
	 */
	private JList<String> gaplists;
	
	/**
	 * The Label that will display possible Messages.
	 */
	private JLabel fail;
	
	public RemoveThread(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		fail.setText("Pending Server");
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		if (c.removeGaplist(gaplists.getSelectedValue()))
			fail.setText("Removed the Gaplist");
		else
			fail.setText("Failed to remove the Gaplist");
		
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}
