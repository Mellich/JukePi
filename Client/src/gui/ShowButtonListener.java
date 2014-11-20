package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

public class ShowButtonListener implements ActionListener{

	private Collector c;
	private JList<String> gaplists;
	
	public ShowButtonListener(Collector c, JList<String> gaplists) {
		this.c = c;
		this.gaplists = gaplists;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (gaplists.getSelectedIndex() > -1) {
			c.fillContentModel(gaplists.getSelectedIndex());
			c.repaint();
		}
	}
}
