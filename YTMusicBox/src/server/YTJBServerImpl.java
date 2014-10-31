package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

import server.connectivity.ConnectionWaiterImpl;
import server.player.YTTrackScheduler;

public class YTJBServerImpl {
	
	public static int PORT = 12345;
	
	private ServerSocket server;
	private ConnectionWaiterImpl w;
	private YTTrackScheduler player;
	private LinkedList<MusicTrack> wishList;
	private LinkedList<MusicTrack> gapList;
	
	public void start(){
		try {
			w.start();
			player.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			r.readLine();
			player.setRunning(false);
			player.interrupt();
			w.setRunning(false);
			w.interrupt();	
			server.close();
			player.join();
			w.join();
			IO.printlnDebug(this, "Server erfolgreich runtergefahren");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public YTJBServerImpl(int port) {
			try {
				wishList = new LinkedList<MusicTrack>();
				gapList = new LinkedList<MusicTrack>();
				server = new ServerSocket(port);
				player = new YTTrackScheduler(wishList, gapList);
				w = new ConnectionWaiterImpl(server, wishList, gapList,player);
				IO.printlnDebug(this, "New server opened on adress "+ InetAddress.getLocalHost()+" port "+port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public static void main(String[] args) {
		YTJBServerImpl s;
		if (args.length == 1){
			int port = Integer.parseInt(args[0]);
			s = new YTJBServerImpl(port);
		} else {
			s = new YTJBServerImpl(PORT);
		}
		s.start();
	}
	
}
