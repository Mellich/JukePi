package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import util.ShowLabelThread;
import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;
import connection.Collector;

public class UDPConnectButtonListener implements ActionListener {

	private Collector c;
	private ServerConnection serverConnection;
	private ConnectButtonListener cbl;
	private JLabel fail;
	
	public UDPConnectButtonListener(Collector c, ConnectButtonListener cbl, JLabel fail) {
		this.c = c;
		serverConnection = ServerConnectionFactory.createServerConnection(15000);
		this.cbl = cbl;
		this.fail = fail;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		new Thread( ()-> {
			client.ServerAddress sa;
			try {
				sa = serverConnection.udpScanning();
				if (c.connect(sa.getIPAddress(), ""+sa.getPort()))
					cbl.actionPerformed(null);
				else {
					fail.setText("Failed to connect to the Server: Invalid connection details sent by server.");
					new ShowLabelThread(fail, null).start();
				}
			} catch (Exception e) {
				fail.setText("Failed to connect to the Server: Timeout, no server available in the network!");
			}}).start();
	}

}
