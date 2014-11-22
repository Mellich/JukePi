package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;

import threads.LoadThread;
import connection.Collector;

public class LoadButtonListener implements ActionListener{
	
	private Collector c;
	private JList<String> gaplists;
	private JLabel fail;
	
	public LoadButtonListener(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		LoadThread lt = new LoadThread(c, gaplists, fail);
		lt.start();
	}

}
