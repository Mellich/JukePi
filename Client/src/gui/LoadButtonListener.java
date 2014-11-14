package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

public class LoadButtonListener implements ActionListener{
	
	private Collector c;
	private JList<String> gaplists;
	
	public LoadButtonListener(Collector c, JList<String> gaplists) {
		this.c = c;
		this.gaplists = gaplists;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.loadGaplist(gaplists.getSelectedValue());
	}

}
