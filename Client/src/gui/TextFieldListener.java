package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class TextFieldListener implements MouseListener{

	private String[] delete;
	private JTextField tf;
	
	public TextFieldListener(String[] delete, JTextField tf) {
		this.delete = delete;
		this.tf = tf;
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		for (String i : delete) {
			if (tf.getText().contains(i))
				tf.setText("");
		}
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}

}
