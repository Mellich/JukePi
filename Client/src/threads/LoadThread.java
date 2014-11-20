package threads;

import javax.swing.JList;

import connection.Collector;

public class LoadThread extends Thread{

	private Collector c;
	private JList<String> gaplists;
	
	public LoadThread(Collector c, JList<String> gaplists) {
		this.c = c;
		this.gaplists = gaplists;
	}
	
	@Override
	public void run() {
		if (gaplists.getSelectedValue() != null)
			c.loadGaplist(gaplists.getSelectedValue());
		else
			//TODO
			return;
	}
}
