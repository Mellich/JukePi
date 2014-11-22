package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;

import threads.ConnectedThread;
import connection.Collector;

public class ShowButtonListener implements ActionListener{

	private Collector c;
	private JList<String> gaplists;
	private JLabel fail;
	
	public ShowButtonListener(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gaplists.getSelectedIndex() > -1) {
			fail.setText("Pending Server");
			fail.setVerticalAlignment(JLabel.CENTER);
			fail.setHorizontalAlignment(JLabel.CENTER);
			fail.setVisible(true);
			if (c.fillContentModel(gaplists.getSelectedIndex()))
				fail.setText("Showing Gaplist " + gaplists.getSelectedValue() + ".jb");
			else
				fail.setText("Failed to show the Gaplist");
		}
		else {
			fail.setText("Please select a Gaplist first");
			fail.setVisible(true);
		}
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		
		ConnectedThread ct = new ConnectedThread(fail, null);
		ct.start();
		
		c.repaint();
	}
}
