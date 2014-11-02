package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;
import javax.swing.JTextField;

import connection.Collector;


/**
 * The Listener, that will handle the Actions for the Add-Button
 * @author Haeldeus
 *
 */
public class AddButtonListener implements ActionListener{

	/**
	 * The Textfield, where the Link will be posted into.
	 */
	private JTextField tf;
	
	/**
	 * The Collector, where the Lists are saved in.
	 */
	private Collector c;
	
	/**
	 * The Label, that counts the Number of Tracks in the Wishlist.
	 */
	private JLabel WishListCounter;
	
	/**
	 * The Constructor for the Listener.
	 * @param tf	The Textfield, where the YouTube Link will be posted into.
	 * @param tc	The Testclass, where the tracks will be added to
	 * @param WishListCounter	The Label, that counts the Number of Tracks in the 
	 * 							Wishlist.
	 */
	public AddButtonListener(JTextField tf, Collector c, JLabel WishListCounter) {
		this.tf = tf;
		this.c = c;
		this.WishListCounter = WishListCounter;
	}
	
	/**
	 * The Method, that handles all actions to be performed.
	 * @param arg0 	Just a stub.
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String txt = tf.getText();
		if (!txt.equals("")) {
			if (txt.contains("www.youtube.") && txt.contains("/watch")) {
				c.addToWishList(txt);
				WishListCounter.setText(""+c.getWishListSize());
				tf.setText("Track added!");
			}
			else 
				tf.setText("Couldn't add the Link to the Wishlist.");
		}
		else
			tf.setText("Couldn't add the Link to the Wishlist");
	}

}
