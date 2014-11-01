package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * The Listener for the Textfield, so it will be empty automatically.
 * @author Haeldeus
 *
 */
public class TextFieldClickListener implements MouseListener{

	/**
	 * The Textfield, that will be listened to.
	 */
	private JTextField tf;
	
	/**
	 * The Constructor for the Listener.
	 * @param tf	The Textfield, that will be listened to.
	 */
	public TextFieldClickListener(JTextField tf) {
		this.tf = tf;
	}
	
	/**
	 * Just a stub.
	 */
	@Override
	public void mouseClicked(MouseEvent e){
		if(tf.getText().contains("Insert a YouTube Link") || tf.getText().contains("Couldn't add the Link") || tf.getText().contains("Track added!"))
			tf.setText("");
	}
	
	/**
	 * Just a stub.
	 */
	@Override
	public void mouseEntered(MouseEvent arg0) {
	}
	
	/**
	 * Just a stub.
	 */
	@Override
	public void mouseExited(MouseEvent arg0) {
	}
	
	/**
	 * Just a stub.
	 */
	@Override
	public void mousePressed(MouseEvent arg0) {
	}
	
	/**
	 * Just a stub.
	 */
	@Override
	public void mouseReleased(MouseEvent arg0) {
	}
}
