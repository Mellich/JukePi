package threads;

import javax.swing.JLabel;
import javax.swing.JList;

import connection.Collector;

public class LoadThread extends Thread{

	private Collector c;
	private JList<String> gaplists;
	private JLabel fail;
	
	public LoadThread(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}
	
	@Override
	public void run() {
		
		fail.setText("Pending Server");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		
		if (gaplists.getSelectedValue() != null)
			if (c.loadGaplist(gaplists.getSelectedValue()))
				fail.setText("Gaplist " + gaplists.getSelectedValue() + ".jb loaded successfully");
			else
				fail.setText("Loading Gaplist "+ gaplists.getSelectedValue() + ".jb failed");
		else
			fail.setText("Please select a Gaplist");
		
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}