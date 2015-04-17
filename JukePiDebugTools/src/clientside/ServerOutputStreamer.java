package clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.ServerAddress;
import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;


/**Streams the output of a server to the console. Nice for debugging purposes.
 * Simply execute the main method of this class to start the program.
 * 
 * @author mellich
 *
 */
public class ServerOutputStreamer implements DefaultNotificationListener, DebugNotificationListener {
	
	private static ServerConnection server;	
	
	public static void main(String[] args) {
		ServerOutputStreamer l = new ServerOutputStreamer();
		server = new YTJBServerConnection(15000);
		server.addDebugNotificationListener(l);
		server.addDefaultNotificationListener(l);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			ServerAddress sa;
			System.out.print("IP-Adresse (leer lassen für UDPconnect): ");
			String ip = reader.readLine();
			if (ip.toLowerCase().equals("")){
				sa = server.udpScanning();
			}
			else{
				System.out.print("Port: ");
				String port = reader.readLine();
				if (ip.equals(""))
					ip = "localhost";
				if (port.equals(""))
					port = "22222";
				int iport = Integer.parseInt(port);
				sa = new ServerAddress(ip,iport);
			}
			System.out.println("Verbinde zu "+sa.getIPAddress() +" auf Port: "+sa.getPort());
			if (server.connect(sa)){
				while(true){
					String input = reader.readLine();
					if (input.equals("close")){
						break;
					}
					
				}				
			}else{
				System.out.println("Fehler beim verbinden!");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL,boolean isVideo) {

	}

	@Override
	public void onDisconnect() {
		System.out.println();
		System.out.println("Disconnected!");
		System.exit(0);

	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		
	}

	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewOutput(String output) {
		System.out.println(output);
		
	}

}
