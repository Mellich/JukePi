package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class BackButtonListener implements ActionListener{

	private EditTrackListener listener;
	
	public BackButtonListener(EditTrackListener listener) {
		this.listener = listener;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		listener.actionPerformed(arg0);
	}

}
