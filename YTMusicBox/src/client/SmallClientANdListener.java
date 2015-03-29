package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;

public class SmallClientANdListener implements DefaultNotificationListener, DebugNotificationListener {
	
	private static ServerConnection server;	
	
	public static void main(String[] args) {
		server = new YTJBServerConnection(15000);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		SmallClientANdListener l = new SmallClientANdListener();
		try {
			System.out.print("IP-Adresse: ");
			String ip = reader.readLine();
			System.out.print("Port: ");
			String port = reader.readLine();
			if (ip.equals(""))
				ip = "localhost";
			if (port.equals(""))
				port = "22222";
			int iport = Integer.parseInt(port);
			System.out.println("Verbinde zu "+ip+" auf Port "+port);
			if (server.connect(ip,iport)){
				server.addDebugNotificationListener(l);
				server.addDefaultNotificationListener(l);
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
