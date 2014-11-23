package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.Collector;

import threads.DownThread;

/**
 * The ActionListener for the DownButton.
 * @author Haeldeus
 *
 */
public class DownButtonListener implements ActionListener{

	/**
	 * The Gaplist as a selectable List.
	 */
	private JList<String> gaplist;
	
	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the ActionListener
	 * @param gaplist	The Gaplist as a selectable List.
	 * @param c	The Collector, that will send the Messages.
	 */
	public DownButtonListener(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		DownThread dt = new DownThread(gaplist, c);
		dt.start();
	}

}
