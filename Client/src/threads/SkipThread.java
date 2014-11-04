package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;

public class SkipThread extends Thread{

	private Collector c;
	private JLabel fail;
	private JFrame frame;
	
	public SkipThread(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	public void run() {
		if (c.skip(fail)) {
			fail.setText("Skipped Track.");
			fail.setVisible(true);
		}
		else {
			fail.setText("Couldn't Skip Track");
			fail.setVisible(true);
		}
		try {Thread.sleep(2000);} catch (Exception e) {e.printStackTrace();}
		fail.setVisible(false);
		frame.repaint();
	}
	
}
