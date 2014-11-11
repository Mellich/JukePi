package threads;

import javax.swing.JList;

import connection.Collector;

public class UpThread extends Thread{

	private JList<String> gaplist;
	private Collector c;
	
	public UpThread(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	public void run() {
		int index = gaplist.getSelectedIndex();
		c.moveTrackUp(index);
		try {Thread.sleep(100);} catch (InterruptedException e ) {}
		gaplist.setSelectedIndex(index-1);
	}
}
