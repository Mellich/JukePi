package clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import messages.Permission;
import client.ServerAddress;
import client.listener.DefaultNotificationListener;
import client.listener.PauseResumeNotificationListener;
import client.listener.SeekNotificationListener;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;

/**
 * A client that signals the server that it is a player.
 * Execute the main method of this class to run the program.
 * When connected to a server, press ENTER to send a player finished notify or 
 * type 'close' to exit the program.
 * 
 * @author mellich
 * @version 1.0
 */
public class PseudoPlayer implements DefaultNotificationListener, PauseResumeNotificationListener, SeekNotificationListener  {

	/**
	 * The ServerConnection-Instance.
	 */
	private static YTJBServerConnection server;

	/**
	 * Runs the PseudoPlayer.
	 * @param args	Just a stub.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		PseudoPlayer l = new PseudoPlayer();
		server = new YTJBServerConnection(15000);
		server.addPauseResumeNotificationListener(l);
		server.addSeekNotificationListener(l);
		server.addDefaultNotificationListener(l);
		server.addPermission(Permission.PLAYER, "player");
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			ServerAddress sa;
			System.out.print("IP-Adresse (leer lassen f�r UDP connect): ");
			String ip = reader.readLine();
			if (ip.toLowerCase().equals("")){
				sa = server.udpScanning();
			}
			else{
				System.out.print("Port (leer lassen f�r Standard): ");
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
				server.setMeAsPlayer();
				System.out.println("Dr�cke 'Enter', um Wiedergabeende zu simulieren.");
				while(true){
					String input = reader.readLine();
					if (input.equals("close")){
						break;
					}else if (input.equals("")){
						server.notifyPlayerFinished();
						System.out.println("Notification sent!");
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
	public void onWishListUpdatedNotify(Song[] songs) {
		System.out.println("Wishlist update erhalten");
		
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		System.out.println("NextTrack notify erhalten. Is ein Video: "+isVideo+" Titel: "+title);
		
	}

	@Override
	public void onDisconnect() {
		System.out.println("Disconnect!");
		System.exit(0);
		
	}

	@Override
	public void onSeekNotify(boolean forward) {
		System.out.println("Seeknotify erhalten: "+forward);
		
	}

	@Override
	public void onPauseResumeNotify(boolean isPlaying) {
		System.out.println("Pause/Resume notify erhalten. Wiedergabe l�uft: "+isPlaying);
		
	}

}
