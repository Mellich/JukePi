package threads;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import connection.Collector;

public class AddThread extends Thread{

	private String link;
	private JButton add;
	private JTextField txt;
	private Collector c;
	private ActionListener al;
	private boolean inFront;
	private JLabel fail;
	
	public AddThread(String link, JButton add, JTextField txt, Collector c, ActionListener buttonListener, boolean inFront, JLabel fail) {
		this.link = link;
		this.add = add;
		this.txt = txt;
		this.c = c;
		this.al = buttonListener;
		this.inFront = inFront;
		this.fail = fail;
	}
	
	@Override
	public void run() {
		fail.setText("Sending URL to Server");
		fail.setVisible(true);
		if (c.addToList(link, inFront)) {
			fail.setText("Track added");
		} else {
			fail.setText("Couldn't add the Track");
		}
		txt.setText("Insert a YouTube Link here.");
		add.addActionListener(al);
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}
