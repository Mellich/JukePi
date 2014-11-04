package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import connection.Collector;

public class DisconnectButtonListener implements ActionListener{

	private JFrame frame;
	private Collector c;
	
	public DisconnectButtonListener(JFrame frame, Collector c) {
		this.frame = frame;
		this.c = c;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.disconnect();
		frame.getContentPane().removeAll();
		frame.setEnabled(false);
		frame.setVisible(false);
		GUI.main(null);
	}
}
