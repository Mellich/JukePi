package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

public class RemoveButtonListener implements ActionListener{
	
	private Collector c;
	private JList<String> gaplists;
	
	public RemoveButtonListener(Collector c, JList<String> gaplists) {
		this.c = c;
		this.gaplists = gaplists;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.removeGaplist(gaplists.getSelectedValue());
		c.repaint();
	}
}
