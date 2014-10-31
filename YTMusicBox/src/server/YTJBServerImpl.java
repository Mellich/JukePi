package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.LinkedList;

import server.connectivity.ConnectionWaiterImpl;
import server.player.YTTrackScheduler;

/**A server, that includes classes to stream videos from youtube or audio files given
 * by a client 
 * 
 * @author Mellich
 *
 */
public class YTJBServerImpl {
	
	/**
	 * the standard port will be used, when no other port is given
	 */
	public static final int PORT = 12345;
	
	/**
	 * the server socket to handle connections to the server
	 */
	private ServerSocket server;
	
	/**
	 * the connection waiter of the server
	 */
	private ConnectionWaiterImpl waiter;
	
	/**
	 * the music scheduler of the server
	 */
	private YTTrackScheduler scheduler;
	
	/**
	 * a list of tracks, that are "wished" by clients to play
	 */
	private LinkedList<MusicTrack> wishList;
	
	/**
	 * a list of tracks, that will be used when the wish list is empty
	 */
	private LinkedList<MusicTrack> gapList;
	
	/**
	 * starts the server and makes him ready for work
	 */
	public void start(){
		try {
			waiter.start();
			scheduler.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			r.readLine(); 																//giving a input will shut down the server
			scheduler.setRunning(false);
			scheduler.interrupt();
			waiter.setRunning(false);
			waiter.interrupt();	
			server.close();
			scheduler.join();
			waiter.join();
			IO.printlnDebug(this, "Server erfolgreich runtergefahren");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 */
	public YTJBServerImpl(int port) {
			try {
				wishList = new LinkedList<MusicTrack>();
				gapList = new LinkedList<MusicTrack>();
				server = new ServerSocket(port);
				scheduler = new YTTrackScheduler(wishList, gapList);
				waiter = new ConnectionWaiterImpl(server, wishList, gapList,scheduler);
				IO.printlnDebug(this, "New server opened on adress "+ InetAddress.getLocalHost()+" port "+port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	/**starts the whole programm
	 * 
	 * @param args if given, it will be interpreted as a port
	 */
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
