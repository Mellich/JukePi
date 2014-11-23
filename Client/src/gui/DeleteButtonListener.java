package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import threads.DeleteThread;

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
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 * @param gaplistModel	The Gaplist as a selectable List.
	 */
	public DeleteButtonListener(Collector c, JList<String> gaplistModel) {
		this.c = c;
		this.gaplistModel = gaplistModel;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		int i = gaplistModel.getSelectedIndex();
		DeleteThread dt = new DeleteThread(c, i);
		dt.start();
	}

}
