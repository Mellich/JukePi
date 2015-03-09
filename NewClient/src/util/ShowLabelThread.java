package util;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * A {@link Thread} to display Messages.
 * @author Haeldeus
 * @version 1.0
 */
public class ShowLabelThread extends Thread{

	/**
	 * The Label, that displays the Message.
	 * @see JLabel
	 */
	private JLabel fail;
	
	/**
	 * The Frame, that contains the Fail-Label.
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * The Message to be shown.
	 */
	private String text;
	
	/**
	 * The Constructor for the {@link Thread}.
	 * @param fail	The Label, that displays the Message.
	 * @param frame	The Frame, that contains the Fail-Label.
	 * @since 1.0
	 */
	public ShowLabelThread(JLabel fail, JFrame frame, String text) {
		this.fail = fail;
		this.frame = frame;
		this.text = text;
		fail.setText(text);
	}
	
	@Override
	public void run() {
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		if (fail.getText().equals(text))
			fail.setVisible(false);
		if (frame != null)
			frame.repaint();
	}
}
