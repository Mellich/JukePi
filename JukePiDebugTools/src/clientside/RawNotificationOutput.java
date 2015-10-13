package clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import messages.Permission;
import client.ServerAddress;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;

/**
 * A NotificationListener, that will print the Responses into the Console.
 * @author Mellich
 * @version 1.0
 */
public class RawNotificationOutput implements DefaultNotificationListener,
		GapListNotificationListener {
	
	/**
	 * Runs the Listener.
	 * @param args	Just a stub.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		RawNotificationOutput l = new RawNotificationOutput();
		ServerConnection server;
		server = new YTJBServerConnection(15000);
		server.addPermission(Permission.ADMIN, "gaplist");
		server.addDefaultNotificationListener(l);
		server.addGapListNotificationListener(l);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			ServerAddress sa;
			System.out.print("IP-Adresse (leer lassen f�r UDP connect): ");
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
				System.out.println("Verbunden!");
				while(true){
					String input = reader.readLine();
					if (input.equals("close")){
						break;
					}else if (input.equals("")){
						server.notifyPlayerFinished();
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
	public void onGapListCountChangedNotify(String[] gapLists) {
		for (String s: gapLists){
			System.out.print(s+", ");
		}
		System.out.println();

	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		System.out.println(gapListName);

	}

	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		System.out.println("GapList update:");
		for (Song s: songs){
			System.out.println(s.getName()+", "+s.getVotes()+", "+s.getTrackID()+", "+s.getParseStatus()+", "+s.isOwnVote());
		}
	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		System.out.println("WishList update:");
		for (Song s: songs){
			System.out.println(s.getName()+", "+s.getVotes()+", "+s.getTrackID()+", "+s.getParseStatus()+", "+s.isOwnVote());
		}

	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		System.out.println(title);

	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub

	}

}
