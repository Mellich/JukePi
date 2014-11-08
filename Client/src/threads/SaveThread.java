package threads;

import connection.Collector;

public class SaveThread extends Thread{

	private Collector c;
	
	public SaveThread(Collector c) {
		this.c = c;
	}
	
	public void run() {
		c.saveGaplist();
	}
}
