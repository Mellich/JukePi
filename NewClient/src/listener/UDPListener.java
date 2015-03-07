package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;
import connection.Collector;

public class UDPListener implements ActionListener{
	
	private Collector c;
	private ServerConnection serverConnection;
	
	public UDPListener(Collector c) {
		this.c = c;
		serverConnection = ServerConnectionFactory.createServerConnection(15000);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		c.showUDPFail("Scanning network...");
		new Thread(() -> {
			client.ServerAddress sa;
			try {
				sa = serverConnection.udpScanning();
				c.connect(sa.getIPAddress(), ""+sa.getPort());
			} catch (Exception e) {
				c.showUDPFail("There are no Servers available in your network.");
			}
		}).start();
	}

}
