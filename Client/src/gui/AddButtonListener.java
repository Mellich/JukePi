package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	 * The CheckBox, that will determine, if a Track will be added in Front or at the End of the List.
	 */
	private JCheckBox checkBox;
	
	/**
	 * The selectable RadioButton, that determines, if a Track should be added to the Wishlist.
	 */
	private JRadioButton wishlist;
	
	/**
	 * The Label, that will Display Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Frame, the Button for this Listener is in.
	 */
	private JFrame frame;
	
	/**
	 * The Constructor for the Listener.
	 * @param field	The TextField with the Link in it.
	 * @param wishlist	The RadioButton for the Wishlist.
	 * @param c	The Collector, that will send the Message.
	 * @param checkBox	The CheckBox, that determines if the Track should be added in front of the List.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public AddButtonListener(JTextField field, JRadioButton wishlist, Collector c, JCheckBox checkBox, JLabel fail, JFrame frame) {
		this.tf = field;
		this.c = c;
		this.checkBox = checkBox;
		this.fail = fail;
		this.frame = frame;
		this.wishlist = wishlist;
	}
	
	/**
	 * Performs the Action.
	 * @param e Just a stub.
	 */
	public void actionPerformed(ActionEvent e) {
		String link = tf.getText();
		boolean inFront = checkBox.isSelected();
		if (!link.isEmpty()) {
			c.addToList(link, wishlist.isSelected(), inFront, tf, fail, frame);
		}
		else {
			fail.setText("No valid Link");
			new util.ShowLabelThread(fail, frame).start();
		}
	}

}
