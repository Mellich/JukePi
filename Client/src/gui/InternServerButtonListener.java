package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import connection.Collector;

public class InternServerButtonListener implements ActionListener {
	
	private Collector c;
	private ConnectButtonListener cbl;

	public InternServerButtonListener(Collector c,ConnectButtonListener cbl) {
		this.c = c;
		this.cbl = cbl;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.createLocalServer(22222);
		cbl.actionPerformed(null);
	}

}
