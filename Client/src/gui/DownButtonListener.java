package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;

import connection.Collector;

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
	 * The Label that displays Responses.
	 */
	private JLabel fail;
	
	/**
	 * The Frame of the Application.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the ActionListener
	 * @param gaplist	The Gaplist as a selectable List.
	 * @param c	The Collector, that will send the Messages.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public DownButtonListener(JList<String> gaplist, Collector c, JLabel fail, JFrame frame) {
		this.gaplist = gaplist;
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		int index = gaplist.getSelectedIndex();
		c.moveTrackDown(index, fail, frame);
		try{Thread.sleep(100);}catch(Exception e) {}
		gaplist.setSelectedIndex(index+1);
	}

}
