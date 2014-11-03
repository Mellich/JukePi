package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import connection.Collector;


public class SkipButtonListener implements ActionListener{

	private Collector c;
	
	public SkipButtonListener(Collector c) {
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		c.skip();
	}

}
