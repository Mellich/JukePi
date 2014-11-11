package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

import threads.UpThread;

public class UpButtonListener implements ActionListener{

	private JList<String> gaplist;
	private Collector c;
	
	public UpButtonListener(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		UpThread ut = new UpThread(gaplist, c);
		ut.start();
	}

}
