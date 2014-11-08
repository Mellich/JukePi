package threads;

import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;

import connection.Collector;

public class AddThread extends Thread{

	private String link;
	private JButton add;
	private JTextField txt;
	private Collector c;
	private ActionListener al;
	private boolean inFront;
	
	public AddThread(String link, JButton add, JTextField txt, Collector c, ActionListener buttonListener, boolean inFront) {
		this.link = link;
		this.add = add;
		this.txt = txt;
		this.c = c;
		this.al = buttonListener;
		this.inFront = inFront;
	}
	
	@Override
	public void run() {
		txt.setText("Sending URL to Server");
		add.removeActionListener(al);
		if (c.addToList(link, inFront)) {
			txt.setText("Track added");
		} else {
			txt.setText("Couldn't add the Track");
		}
		add.addActionListener(al);
	}
}
