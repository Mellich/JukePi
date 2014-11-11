package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import threads.UpdateThread;

public class BackButtonListener implements ActionListener{

	private EditTrackListener listener;
	private UpdateThread ut;
	
	public BackButtonListener(EditTrackListener listener, UpdateThread ut) {
		this.listener = listener;
		this.ut = ut;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ut.pause();
		listener.actionPerformed(arg0);
	}

}
