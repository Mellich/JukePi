package threads;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;

import connection.Collector;

public class UpdateThread extends Thread{

	private JList<String> gaplists;
	private JList<String> content;
	private boolean running;
	private Collector c;
	private JFrame frame;
	private JPanel contentPane;
	
	public UpdateThread(JList<String> gaplists, JList<String> content, Collector c, JFrame frame) {
		this.gaplists = gaplists;
		this.content = content;
		running = true;
		this.c = c;
		this.frame = frame;
	}
	
	public void addContentPane(JPanel contentPane) {
		this.contentPane = contentPane;
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
					if (index != -1) {
						lastindex = index;
						c.fillContentModel(index);
						content.repaint();
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
