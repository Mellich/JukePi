package threads;

import javax.swing.JList;

import connection.Collector;

public class DownThread extends Thread{

	private JList<String> gaplist;
	private Collector c;
	
	public DownThread(JList<String> gaplist, Collector c) {
		this.gaplist = gaplist;
		this.c = c;
	}
	
	public void run() {
		int index = gaplist.getSelectedIndex();
		c.moveTrackDown(index);
		try {Thread.sleep(100);} catch (InterruptedException e ) {}
		gaplist.setSelectedIndex(index+1);
	}
}
