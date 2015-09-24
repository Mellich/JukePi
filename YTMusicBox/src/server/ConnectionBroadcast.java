package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
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
    //private static final int CLIENT_MULTICAST_PORT = 4446;
   
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
		MulticastSocket sendSocket = null;
	    try {
	    	sendSocket =  new MulticastSocket(NETWORK_GROUP_PORT);
	    	byte[] byteMessage = message.getBytes(TEXT_ENCODING);
	    	InetAddress group = InetAddress.getByName(NETWORK_GROUP) ;
	    	sendSocket.setInterface(InetAddress.getByName(server.getIpAddress()));
	    	sendSocket.joinGroup(group);
	    	
	    	byte[] bytes = new byte[200];
		      DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
		      
			while(true){
			      // Nachricht an Gruppe senden
				//IO.printlnDebug(this, "Broadcast connection details...: "+message);
				try{
					//sendSocket.send(new DatagramPacket(byteMessage, byteMessage.length , group ,NETWORK_GROUP_PORT));
			  	    try {
			  	    	IO.printlnDebug(this, "Wait for incoming UDP request...");
				        sendSocket.receive(packet);
				    } catch (IOException e) {
					     //IO Exception occured while receiving data
					    }
				        if (packet.getLength() > 0){
				        	String message = new String(packet.getData(),0,packet.getLength(), TEXT_ENCODING);
					        IO.printlnDebug(this, "UDP message received: "+message);
				        	if (message.equals("REQUEST")){
				        		sendSocket.send(new DatagramPacket(byteMessage, byteMessage.length , group ,NETWORK_GROUP_PORT));
				        		IO.printlnDebug(this, "server information sent!");
				        	}
				        }
				}
			    catch (IOException e){
			    	IO.printlnDebug(this, "Error while waiting for UDP connection... trying again...");
			    }
			    if (Thread.interrupted())
			    	break;
			    Thread.sleep(SLEEPTIME);
			}
		} catch (IOException | InterruptedException e) {
		      e.printStackTrace();
		}finally{
			if (sendSocket != null){
				sendSocket.close();
			}
			IO.printlnDebug(this, "UDP broadcast shut down!");
		}
	}

}
