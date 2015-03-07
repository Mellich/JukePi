package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * The Listener for all TextFields, that will Clear the Field.
 * @author Haeldeus
 *
 */
public class TextFieldListener implements MouseListener{

	/**
	 * The Array, that will save the Strings, that will be deleted, when selecting the TextField.
	 */
	private String[] delete;
	
	/**
	 * The TextField, this Listener will be added to.
	 */
	private JTextField tf;
	
	/**
	 * The Constructor for the Listener.
	 * @param delete	The Array, that will contain the Strings to be deleted.
	 * @param tf	The TextField, this Listener will be added to.
	 */
	public TextFieldListener(String[] delete, JTextField tf) {
		this.delete = delete;
		this.tf = tf;
	}
	
	/**
	 * Performs the Action, when the Mouse is clicked.
	 * @param arg0	Just a stub.
	 */
	public void mouseClicked(MouseEvent arg0) {
		for (String i : delete) {
			if (tf.getText().contains(i)) {
				tf.setText("");
				return;
			}
		}
		
	}

	/**
	 * Just a stub.
	 * @param arg0 Just a stub.
	 */
	public void mouseEntered(MouseEvent arg0) {
	}

	/**
	 * Just a stub.
	 * @param arg0 Just a stub.
	 */
	public void mouseExited(MouseEvent arg0) {
	}

	/**
	 * Just a stub.
	 * @param arg0 Just a stub.
	 */
	public void mousePressed(MouseEvent arg0) {
		
	}

	/**
	 * Just a stub.
	 * @param arg0 Just a stub.
	 */
	public void mouseReleased(MouseEvent arg0) {
		
	}

}