package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import threads.DeleteThread;

import connection.Collector;

public class DeleteButtonListener implements ActionListener{

	private Collector c;
	private JList<String> gaplistModel;
	
	public DeleteButtonListener(Collector c, JList<String> gaplistModel) {
		this.c = c;
		this.gaplistModel = gaplistModel;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int i = gaplistModel.getSelectedIndex();
		DeleteThread dt = new DeleteThread(c, i);
		dt.start();
	}

}
