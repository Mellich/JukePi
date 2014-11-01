package gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

public class TextFieldClickListener implements MouseListener{

	private JTextField tf;
	
	public TextFieldClickListener(JTextField tf) {
		this.tf = tf;
	}
	@Override
	public void mouseClicked(MouseEvent e){
		if(tf.getText().contains("Insert a YouTube Link"))
			tf.setText("");
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
