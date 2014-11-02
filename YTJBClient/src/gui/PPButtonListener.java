package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;

public class PPButtonListener implements ActionListener{

	/**
	 * Indicates, if a track is playing at the moment.
	 */
	private boolean playing = false;
	
	/**
	 * The Reference to the Play/Pause-Button to change it's text and tooltip
	 */
	private JButton bt;
	
	public PPButtonListener(JButton button) {
		bt = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (playing) {
			playing = !playing;
			bt.setText("Play");
			bt.setToolTipText("Click here to play a music file.");
		}
		else {
			playing = !playing;
			bt.setText("Pause");
			bt.setToolTipText("Click here to pause the music file.");
		}
	}
}