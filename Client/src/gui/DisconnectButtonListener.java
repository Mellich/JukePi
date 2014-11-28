package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import connection.Collector;

/**
 * The ActionListener for the DisconnectButton
 * @author Haeldeus
 *
 */
public class DisconnectButtonListener implements ActionListener{

	/**
	 * The Frame, that will be changed.
	 */
	private JFrame frame;
	
	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Frame, that could be open (Edit Tracks or Edit Gaplists).
	 */
	private JFrame secondFrame;
	
	/**
	 * Determines, if the action was already performed.
	 */
	private boolean disconnected;
	/**
	 * The Constructor for the ActionListener.
	 * @param frame	The Frame, that will be changed.
	 * @param c	The Collector, that will send the Messages.
	 * @param secondFrame	The second Frame that can be open.
	 */
	public DisconnectButtonListener(JFrame frame, Collector c, JFrame secondFrame) {
		this.frame = frame;
		this.c = c;
		this.secondFrame = secondFrame;
		this.disconnected = false;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		if (arg0 != null)
			c.disconnect();
		if (!disconnected) {
			frame.getContentPane().removeAll();
			frame.setEnabled(false);
			frame.setVisible(false);
			if (secondFrame != null) {
				secondFrame.setEnabled(false);
				secondFrame.setVisible(false);
			}
			GUI.main(null);
			disconnected = true;
		}
	}
}
