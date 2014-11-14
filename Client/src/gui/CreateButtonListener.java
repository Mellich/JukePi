package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;

import threads.CreateThread;

import connection.Collector;

public class CreateButtonListener implements ActionListener{

	private Collector c;
	private JTextField tf;
	
	public CreateButtonListener(Collector c, JTextField tf) {
		this.c = c;
		this.tf = tf;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		CreateThread ct = new CreateThread(c, tf.getText());
		ct.start();
	}
}
