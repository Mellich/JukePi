package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;

public class PlayPauseThread extends Thread{

	private Collector c;
	private JLabel fail;
	private JFrame frame;
	
	public PlayPauseThread(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	public void run() {
		if (c.playButtonPressed()) {
			if (c.getStatus())
				fail.setText("Track resumed");
			else
				fail.setText("Track paused");

			fail.setVisible(true);
		}
		else {
			if (c.getStatus())
				fail.setText("Couldn't resume the track");
			else
				fail.setText("Couldn't pause the track");
			fail.setVisible(true);
		}
		try {Thread.sleep(2000);} catch (Exception e) {e.printStackTrace();}
		fail.setVisible(false);
		frame.repaint();
	}
	
}