package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import threads.SaveThread;

import connection.Collector;

/**
 * The Listener, that will perform Actions for the SaveButton.
 * @author Haeldeus
 *
 */
public class SaveButtonListener implements ActionListener{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 */
	public SaveButtonListener(Collector c) {
		this.c = c;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		SaveThread st = new SaveThread(c);
		st.start();
	}
}
