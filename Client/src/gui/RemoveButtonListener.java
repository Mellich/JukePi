package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;

import threads.RemoveThread;
import connection.Collector;

public class RemoveButtonListener implements ActionListener{
	
	private Collector c;
	private JList<String> gaplists;
	private JLabel fail;
	
	public RemoveButtonListener(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		RemoveThread rt = new RemoveThread(c, gaplists, fail);
		rt.start();
	}
}
