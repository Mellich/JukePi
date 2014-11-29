package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;

/**
 * The Thread to pause or resume a Track.
 * @author Haeldeus
 *
 */
public class PlayPauseThread extends Thread{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, that will be changed.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the Thread.
	 * @param c	The Collector, that will send the Messages.
	 * @param fail	The Label, that will display possible Messages.
	 * @param frame	The Frame, that will be changed.
	 */
	public PlayPauseThread(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		boolean wasRunning = c.getStatus();
		boolean success = c.playButtonPressed();
		if (!wasRunning)
			if (success)
				fail.setText("Track resumed successfully.");
			else
				fail.setText("Track couldn't be paused.");
		else
			if (success)
				fail.setText("Track paused successfully.");
			else
				fail.setText("Track couldn't be resumed");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		new ShowLabelThread(fail, frame).start();
	}
	
}