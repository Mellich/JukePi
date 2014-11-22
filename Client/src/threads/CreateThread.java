package threads;

import javax.swing.JLabel;

import connection.Collector;

public class CreateThread extends Thread{

	private Collector c;
	private String name;
	private JLabel fail;
	
	public CreateThread(Collector c, String name, JLabel fail) {
		this.c = c;
		this.name = name;
		this.fail = fail;
	}
	
	public void run() {
		fail.setText("Pending Server");
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		if (c.createNewList(name))
			fail.setText("Created a new Gaplist with the name: " + name+".jb");
		else
			fail.setText("Failed to create a new Gaplist");
		
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		
		try {Thread.sleep(2000);} catch (Exception e) {}
		fail.setVisible(false);
	}
}
