package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JLabel;
import javax.swing.JTextField;

import exampleResponses.TestClass;

public class AddButtonListener implements ActionListener{

	private JTextField tf;
	private TestClass tc;
	private JLabel WishListCounter;
	
	public AddButtonListener(JTextField tf, TestClass tc, JLabel WishListCounter) {
		this.tf = tf;
		this.tc = tc;
		this.WishListCounter = WishListCounter;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String txt = tf.getText();
		if (!txt.equals("")) {
			if (txt.contains("www.youtube.") && txt.contains("/watch")) {
				tc.sr.addToWishList(txt);
				WishListCounter.setText(""+tc.sr.getWishListSize());
				tf.setText("Insert a YouTube Link here...");
			}
			else {
				tf.setText("Couldn't add the Link to the Wishlist.");
			}
		}
	}

}
