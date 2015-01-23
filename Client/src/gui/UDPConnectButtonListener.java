package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import util.ShowLabelThread;
import clientwrapper.ServerAddress;
import clientwrapper.YTJBClientWrapper;
import connection.Collector;

public class UDPConnectButtonListener implements ActionListener {

	private Collector c;
	private YTJBClientWrapper wrapper;
	private ConnectButtonListener cbl;
	private JLabel fail;
	
	public UDPConnectButtonListener(Collector c, ConnectButtonListener cbl, JLabel fail) {
		this.c = c;
		wrapper = new YTJBClientWrapper();
		this.cbl = cbl;
		this.fail = fail;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		ServerAddress sa = wrapper.waitForUDPConnect();
		if (c.connect(sa.getIPAddress(), ""+sa.getPort()))
			cbl.actionPerformed(null);
		else {
			fail.setText("Failed to connect to the Server. Please check for correct spelling.");
			new ShowLabelThread(fail, null).start();
		}
	}

}
