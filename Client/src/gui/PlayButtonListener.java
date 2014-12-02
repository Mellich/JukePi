package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;

/**
 * The ActionListener for the PlayButton.
 * @author Haeldeus
 *
 */
public class PlayButtonListener implements ActionListener{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, that contains the PlayButton.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector, that will send the Messages.
	 * @param fail	The Label, that will display possible Messages.
	 * @param frame	The Frame, that contains the PlayButton.
	 */
	public PlayButtonListener(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		c.playButtonPressed(fail, frame);
		frame.repaint();
	}

}
