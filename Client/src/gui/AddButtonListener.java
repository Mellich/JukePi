package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import threads.AddThread;

import connection.Collector;

/**
 * The Listener for the Add Button.
 * @author Haeldeus
 *
 */
public class AddButtonListener implements ActionListener{

	/**
	 * The TextField, that contains the YouTube Link.
	 */
	private JTextField tf;
	
	/**
	 * The Collector, that will send the Message.
	 */
	private Collector c;
	
	/**
	 * The AddButton.
	 */
	private JButton button;
	
	/**
	 * The CheckBox, that will determine, if a Track will be added in Front or at the End of the List.
	 */
	private JCheckBox checkBox;
	
	/**
	 * The Label, that will Display Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the Listener.
	 * @param field	The TextField.
	 * @param c	The Collector.
	 * @param button	The AddButton
	 * @param checkBox	The CheckBox.
	 * @param fail	The Label.
	 */
	public AddButtonListener(JTextField field, Collector c, JButton button, JCheckBox checkBox, JLabel fail) {
		this.tf = field;
		this.c = c;
		this.button = button;
		this.checkBox = checkBox;
		this.fail = fail;
	}
	
	/**
	 * Performs the Action.
	 * @param e Just a stub.
	 */
	public void actionPerformed(ActionEvent e) {
		button.removeActionListener(this);		//If you do that in the thread, sending this message multiple times is possible
		String link = tf.getText();
		boolean inFront = checkBox.isSelected();
		if (link.contains("youtube.") && link.contains("/watch")) {
			AddThread at = new AddThread(link, button, tf, c, this, inFront, fail);
			at.start();
		}
		else
			tf.setText("No valid Link");
	}

}
