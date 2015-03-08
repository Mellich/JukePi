package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import connection.Collector;

public class InternServerButtonListener implements ActionListener {
	
	private Collector c;
	private ConnectButtonListener cbl;
	private int port;

	public InternServerButtonListener(Collector c,int port,ConnectButtonListener cbl) {
		this.c = c;
		this.cbl = cbl;
		this.port = port;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.createLocalServer(port);
		cbl.setIP("localhost");
		cbl.setPort(port);
		cbl.actionPerformed(null);
	}

}
