package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import threads.ConnectedThread;
import threads.CreateThread;
import connection.Collector;

public class CreateButtonListener implements ActionListener{

	private Collector c;
	private JTextField tf;
	private JLabel fail;
	
	public CreateButtonListener(Collector c, JTextField tf, JLabel fail) {
		this.c = c;
		this.tf = tf;
		this.fail = fail;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (tf.getText() != null && !tf.getText().equals("")) {
			System.out.println(tf.getText());
			CreateThread ct = new CreateThread(c, tf.getText(), fail);
			ct.start();
		}
		else {
			fail.setText("Please insert a Name for the new Gaplist first");
			fail.setVerticalAlignment(JLabel.CENTER);
			fail.setHorizontalAlignment(JLabel.CENTER);
			fail.setVisible(true);
			ConnectedThread ct = new ConnectedThread(fail, null);
			ct.start();
		}
	}
}
