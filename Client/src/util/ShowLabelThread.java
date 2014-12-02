package util;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ShowLabelThread extends Thread{

	private JLabel fail;
	private JFrame frame;
	
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
