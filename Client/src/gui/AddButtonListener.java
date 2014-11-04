package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import threads.AddThread;

import connection.Collector;

public class AddButtonListener implements ActionListener{

	private JTextField tf;
	private Collector c;
	private JButton button;
	
	public AddButtonListener(JTextField field, Collector c, JButton button) {
		this.tf = field;
		this.c = c;
		this.button = button;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		String link = tf.getText();
		AddThread at = new AddThread(link, button, tf, c, this);
		at.start();
	}

}
