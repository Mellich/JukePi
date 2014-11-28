package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * The Thread to show the Message in the FailLabel.
 * @author Haeldeus
 *
 */
public class ShowLabelThread extends Thread{

	/**
	 * The Label that will display the Message.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, the Label will be added to.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the Thread.
	 * @param fail	The Label, that will display the Message.
	 * @param frame	The Frame, the Label will be added to.
	 */
	public ShowLabelThread(JLabel fail, JFrame frame) {
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Executes the Command.
	 */
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
