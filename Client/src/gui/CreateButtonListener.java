package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JTextField;

import threads.ShowLabelThread;
import threads.CreateThread;
import connection.Collector;

/**
 * The ActionListener for the CreateButton
 * @author Haeldeus.
 *
 */
public class CreateButtonListener implements ActionListener{

	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The TextField, that contains the Name of the Gaplist.
	 */
	private JTextField tf;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param c	The Collector that will send the Messages.
	 * @param tf	The TextField, that contains the Name for the List.
	 * @param fail	The Label, that will display possible Messages.
	 */
	public CreateButtonListener(Collector c, JTextField tf, JLabel fail) {
		this.c = c;
		this.tf = tf;
		this.fail = fail;
	}

	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
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
			ShowLabelThread ct = new ShowLabelThread(fail, null);
			ct.start();
		}
	}
}
