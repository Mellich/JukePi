package threads;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class ConnectedThread extends Thread{

	private JLabel fail;
	private JFrame frame;
	
	public ConnectedThread(JLabel fail, JFrame frame) {
		this.fail = fail;
		this.frame = frame;
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		frame.getContentPane().remove(fail);
		frame.repaint();
	}
}
