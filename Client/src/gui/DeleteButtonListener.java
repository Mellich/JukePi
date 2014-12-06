package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import connection.Collector;

/**
 * The ActionListener for the DeleteButton.
 * @author Haeldeus
 *
 */
public class DeleteButtonListener implements ActionListener{

	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Gaplist as a selectable List.
	 */
	private JList<String> gaplistModel;
	
	/**
	 * The Label that displays responses.
	 */
	private JLabel fail;
	
	/**
	 * The Frame of the Application.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 * @param gaplistModel	The Gaplist as a selectable List.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public DeleteButtonListener(Collector c, JList<String> gaplistModel, JLabel fail, JFrame frame) {
		this.c = c;
		this.gaplistModel = gaplistModel;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		int i = gaplistModel.getSelectedIndex();
		c.deleteFromGaplist(i, fail, frame);
	}

}
