package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;
import connection.Collector;

/**
 * The Listener for the UDP-Connect-Button.
 * @author Haeldeus
 * @version 1.0
 */
public class UDPListener implements ActionListener{
	
	/**
	 * The {@link Collector}, that will perform the {@link Collector#connect(String, String)}
	 * - Method after finding a Server by UDP-scanning.
	 */
	private Collector collector;
	
	/**
	 * The {@link ServerConnection}, that will scan the network.
	 */
	private ServerConnection serverConnection;
	
	/**
	 * The Constructor for the UDP-Listener. Creates a new {@link ServerConnection}.
	 * @param collector	The {@link Collector}, that will perform the 
	 * {@link Collector#connect(String, String)}-Method.
	 * @since 1.0
	 */
	public UDPListener(Collector collector) {
		this.collector = collector;
		serverConnection = ServerConnectionFactory.createServerConnection(15000);
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		collector.showUDPFail("Scanning network...");
		new Thread(() -> {
			client.ServerAddress sa;
			try {
				sa = serverConnection.udpScanning();
				collector.connect(sa.getIPAddress(), ""+sa.getPort());
			} catch (Exception e) {
				collector.showUDPFail("There are no Servers available in your network.");
			}
		}).start();
	}

}
