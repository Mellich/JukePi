package threads;

import javax.swing.JLabel;

import connection.Collector;

/**
 * The Thread, to create a new Gaplist.
 * @author Haeldeus
 *
 */
public class CreateThread extends Thread{

	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Name of the new Gaplist.
	 */
	private String name;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the Thread.
	 * @param c	The Collector, that will send the Messages.
	 * @param name	The Name of the new Gaplist.
	 * @param fail	The Label, that will display possible Messages.
	 */
	public CreateThread(Collector c, String name, JLabel fail) {
		this.c = c;
		this.name = name;
		this.fail = fail;
	}
	
	/**
	 * Executes the Command.
	 */
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
