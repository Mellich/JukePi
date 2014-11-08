package threads;

import connection.Collector;

public class DeleteThread extends Thread{

	private Collector c;
	private int index;
	
	public DeleteThread(Collector c, int index) {
		this.c = c;
		this.index = index;
	}
	
	public void run() {
		c.deleteTrack(index);
	}
}
