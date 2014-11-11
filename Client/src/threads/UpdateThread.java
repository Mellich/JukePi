package threads;

import javax.swing.DefaultListModel;
import javax.swing.JList;

import connection.Collector;

public class UpdateThread extends Thread{

	private JList<String> gaplists;
	private JList<String> content;
	private boolean running;
	private Collector c;
	
	public UpdateThread(JList<String> gaplists, JList<String> content, Collector c) {
		this.gaplists = gaplists;
		this.content = content;
		running = true;
		this.c = c;
	}
	
	public void pause() {
		this.running = false;
	}
	
	public void run() {
		int lastindex = -1, index;
		
		try {
			while (running) {
				index = gaplists.getSelectedIndex();
				if (index != lastindex) {
					lastindex = index;
					c.fillContentModel(index);
					c.repaint();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
