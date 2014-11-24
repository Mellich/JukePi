package threads;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import connection.Collector;

/**
 * The Thread to add a Track to the List.
 * @author Haeldeus
 *
 */
public class AddThread extends Thread{

	/**
	 * The Link to the YouTube-Video.
	 */
	private String link;
	
	/**
	 * The AddButton.
	 */
	private JButton add;
	
	/**
	 * The TextField, that contains the YouTube-Link.
	 */
	private JTextField txt;
	
	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The ActionListener of the AddButton.
	 */
	private ActionListener al;
	
	/**
	 * Determines, if the Track should be added in front of the List or at the end of it.
	 */
	private boolean inFront;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the Thread.
	 * @param link	The Link to the YouTube-Video.
	 * @param add	The AddButton.
	 * @param txt	The TextField, that contains the YouTube-Link.
	 * @param c	The Collector, that will send the Messages.
	 * @param buttonListener	The Listener for the AddButton.
	 * @param inFront	Determines, if the Track should be added in front or at the End of the List.
	 * @param fail	The Label, that displays possible Messages.
	 */
	public AddThread(String link, JButton add, JTextField txt, Collector c, ActionListener buttonListener, boolean inFront, JLabel fail) {
		this.link = link;
		this.add = add;
		this.txt = txt;
		this.c = c;
		this.al = buttonListener;
		this.inFront = inFront;
		this.fail = fail;
	}
	
	/**
	 * Executes the Command.
	 */
	public void run() {
		fail.setText("Sending URL to Server");
		fail.setVisible(true);
		if (c.addToList(link, inFront)) {
			fail.setText("Track added");
		} else {
			fail.setText("Couldn't add the Track");
		}
		txt.setText("Insert a YouTube Link here.");
		add.addActionListener(al);
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}
