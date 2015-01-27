package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

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
	 * The Label, that will display Responses.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, that contains the Fail-Label.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public SaveButtonListener(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		c.saveGaplist(fail, frame);
	}
}
