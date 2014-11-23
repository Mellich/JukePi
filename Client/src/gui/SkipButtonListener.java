package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import threads.SkipThread;

import connection.Collector;

/**
 * The ActionListener, that will perform Actions for the Skip Button.
 * @author Haeldeus
 *
 */
public class SkipButtonListener implements ActionListener{

	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Frame that contains this Button.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector, that will send the Messages.
	 * @param fail	The Label, that will display possible Messages.
	 * @param frame	The Frame, that contains this Button.
	 */
	public SkipButtonListener(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		SkipThread st = new SkipThread(c, fail, frame);
		st.start();
		frame.repaint();
	}

}
