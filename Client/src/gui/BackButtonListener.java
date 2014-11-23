package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The ActionListener for the Back-Button
 * @author Haeldeus
 *
 */
public class BackButtonListener implements ActionListener{

	/**
	 * The Listener, that has the Method, to disconnect from the Server.
	 */
	private EditTrackListener listener;
	
	/**
	 * The Constructor for the Listener.
	 * @param listener	The Disconnect-ActionListener.
	 */
	public BackButtonListener(EditTrackListener listener) {
		this.listener = listener;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		listener.actionPerformed(arg0);
	}

}
