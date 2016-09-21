package listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;
import connection.Collector;

/**
 * <p>The Listener for the UDP-Connect-Button.</p>
 * 
 * <h3>Provided Methods:</h3> 
 * <ul>
 *	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #UDPListener(Collector)}: 
 * 				The Constructor for the Listener. Will set the given {@link Collector} as 
 * 				Value for the Field {@link #collector} and creates a new 
 * 				{@link ServerConnection}.</li>
 * 			
 * 			<li>{@link #actionPerformed(ActionEvent)}:
 * 				The Method, that is called, whenever the Button, this Listener is added to, 
 * 				was pressed. Will search for a Server in the Network, using UDP. If a Server 
 * 				is found, this Method will call the 
 * 				{@link Collector#connect(String, String)}-Method to connect to that Server. If 
 * 				no Server is found, a Message will be printed on the LoginWindow, using 
 * 				{@link Collector#showUDPFail(String)}.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #collector}:
 * 				The {@link Collector}, that provides the Methods to connect to the found 
 * 				Server.</li>
 * 			<li>{@link #serverConnection}:
 * 				The {@link ServerConnection}, that scans the Network for a Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class UDPListener implements ActionListener{
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private final Collector collector}</p>
	 * <p style="margin-left: 20px">The {@link Collector}, that will perform the 
	 * {@link Collector#connect(String, String)}-Method after finding a Server by UDP-scanning.
	 * </p>
	 */
	private final Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>serverConnection</b></em></p>
	 * <p style="margin-left: 20px">{@code private final ServerConnection serverConnection}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will scan the network.
	 * </p>
	 */
	private final ServerConnection serverConnection;
	
	/**
	 * <p style="margin-left: 10px"><em><b>UDPListener</b></em></p>
	 * <p style="margin-left: 20px">{@code public UDPListener(Collector)}</p>
	 * <p style="margin-left: 20px">The Constructor for the UDP-Listener. Creates a new 
	 * {@code ServerConnection} and sets the given {@code Collector} as Value for the Field 
	 * {@code collector}.</p>
	 * @param collector	The {@link Collector}, that will perform the 
	 * {@link Collector#connect(String, String)}-Method.
	 * @since 1.0
	 * @see ServerConnection
	 * @see Collector
	 * @see #collector
	 */
	public UDPListener(Collector collector) {
		this.collector = collector;
		serverConnection = ServerConnectionFactory.createServerConnection(15000);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>actionPerformed</b></em></p>
	 * <p style="margin-left: 20px">{@code public void actionPerformed(ActionEvent)}</p>
	 * <p style="margin-left: 20px">The Method, that is called, whenever the Button, this 
	 * Listener is added to, was pressed. Will search for a Server in the Network, using UDP. 
	 * If a Server is found, this Method will call the 
	 * {@link Collector#connect(String, String)}-Method to connect to that Server. If no 
	 * Server is found, a Message will be printed on the LoginWindow, using 
	 * {@link Collector#showUDPFail(String)}.</p>
	 * @param arg0	Not used.
	 * @since 1.0
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		collector.showUDPFail("Scanning network...");
		new Thread(() -> {
			client.ServerAddress sa;
			try {
				sa = serverConnection.udpScanning()[0];
				collector.connect(sa.getIPAddress(), ""+sa.getPort());
			} catch (Exception e) {
				collector.showUDPFail("There are no Servers available in your network.");
			}
		}).start();
	}
}
