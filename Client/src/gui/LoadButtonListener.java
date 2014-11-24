package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JList;

import threads.LoadThread;
import connection.Collector;

/**
 * The ActionListener for the LoadButton.
 * @author Haeldeus.
 *
 */
public class LoadButtonListener implements ActionListener{
	
	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Gaplists as a selectable List.
	 */
	private JList<String> gaplists;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 * @param gaplists	The Gaplists as a selectable List.
	 * @param fail	The Label that will will display possible Messages.
	 */
	public LoadButtonListener(Collector c, JList<String> gaplists, JLabel fail) {
		this.c = c;
		this.gaplists = gaplists;
		this.fail = fail;
	}

	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		LoadThread lt = new LoadThread(c, gaplists, fail);
		lt.start();
	}

}
