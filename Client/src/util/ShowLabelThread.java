package util;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A Thread to display the Responses from the Server.
 * @author Haeldeus
 *
 */
public class ShowLabelThread extends Thread{

	/**
	 * The Label, that displays the Response.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, that contains the Fail-Label.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the Thread.
	 * @param fail	The Label, that displays the Response.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public ShowLabelThread(JLabel fail, JFrame frame) {
		this.fail = fail;
		this.frame = frame;
	}
	
	@Override
	public void run() {
		fail.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		fail.setVisible(false);
		if (frame != null)
			frame.repaint();
	}
}
