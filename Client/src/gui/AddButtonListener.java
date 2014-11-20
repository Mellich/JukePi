package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import threads.AddThread;

import connection.Collector;

public class AddButtonListener implements ActionListener{

	private JTextField tf;
	private Collector c;
	private JButton button;
	private JCheckBox checkBox;
	private JLabel fail;
	
	public AddButtonListener(JTextField field, Collector c, JButton button, JCheckBox checkBox, JLabel fail) {
		this.tf = field;
		this.c = c;
		this.button = button;
		this.checkBox = checkBox;
		this.fail = fail;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		button.removeActionListener(this);		//If you do that in the thread, sending this message multiple times is possible
		String link = tf.getText();
		boolean inFront = checkBox.isSelected();
		if (link.contains("youtube.") && link.contains("/watch")) {
			AddThread at = new AddThread(link, button, tf, c, this, inFront, fail);
			at.start();
		}
		else
			tf.setText("No valid Link");
	}

}
