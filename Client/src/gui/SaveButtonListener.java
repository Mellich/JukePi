package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import threads.SaveThread;

import connection.Collector;

public class SaveButtonListener implements ActionListener{

	private Collector c;
	
	public SaveButtonListener(Collector c) {
		this.c = c;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		SaveThread st = new SaveThread(c);
		st.start();
	}

}
