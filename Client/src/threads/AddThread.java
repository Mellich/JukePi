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
	
	public AddThread(String link, JButton add, JTextField txt, Collector c, ActionListener buttonListener) {
		this.link = link;
		this.add = add;
		this.txt = txt;
		this.c = c;
		this.al = buttonListener;
	}
	
	@Override
	public void run() {
		txt.setText("Pending Server");
		add.removeActionListener(al);
		if (c.addToList(link)) {
			txt.setText("Track added");
		} else {
			txt.setText("Couldn't add the Track");
		}
		add.addActionListener(al);
	}
}
