package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

import threads.DownThread;

public class DownButtonListener implements ActionListener{

	private JList<String> gaplist;
	private Collector c;
	
	public DownButtonListener(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		DownThread dt = new DownThread(gaplist, c);
		dt.start();
	}

}
