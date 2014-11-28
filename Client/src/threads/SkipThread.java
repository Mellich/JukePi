package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;

/**
 * The Thread to skip a Track.
 * @author Haeldeus
 *
 */
public class SkipThread extends Thread{

	/**
	 * The Collector, that will send the Messages.
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
	 * @param fail	The Label that will display possible Messages.
	 * @param frame	The Frame, that will be changed.
	 */
	public SkipThread(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		c.skip(fail, frame);
	}
	
}
