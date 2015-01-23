package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.MulticastSocket;

import utilities.IO;
import messages.MessageType;

public class ConnectionBroadcast implements Runnable {
	
	private static final long SLEEPTIME = 5000L;
	
    // Netzwerk-Gruppe
    private static final String NETWORK_GROUP = "230.0.0.1";
    // Netzwerk-Gruppen Port
    private static final int NETWORK_GROUP_PORT = 4447;
    // Client-Port
    private static final int CLIENT_MULTICAST_PORT = 4446;
   
    // Nachrichten-Codierung
    private static final String TEXT_ENCODING = "UTF8";
    
	private String message;

	private YTJBServer server;
	
	public ConnectionBroadcast(String ipAddress, int port,YTJBServer server) {
		this.message = ipAddress+MessageType.SEPERATOR+port;
		this.server = server;
	}

	@Override
	public void run() {	
		MulticastSocket socket = null;
	    try {
	    	socket =  new MulticastSocket(new InetSocketAddress(server.getIpAddress(),CLIENT_MULTICAST_PORT));
	    	byte[] byteMessage = message.getBytes(TEXT_ENCODING);
	    	InetAddress group = InetAddress.getByName(NETWORK_GROUP) ;
	    	socket.joinGroup(group);
			while(true){
			      // Nachricht an Gruppe senden
				//IO.printlnDebug(this, "Broadcast connection details...: "+message);
			    socket.send(new DatagramPacket(byteMessage, byteMessage.length , group ,NETWORK_GROUP_PORT));
			    Thread.sleep(SLEEPTIME);
			}
		} catch (IOException | InterruptedException e) {
		      e.printStackTrace();
		}finally{
			if (socket != null)
				socket.close();
		}
	}

}
