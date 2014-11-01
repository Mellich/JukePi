package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

import server.connectivity.ConnectionHandler;
import server.connectivity.ConnectionWaiter;
import server.player.TrackScheduler;
import utilities.IO;

/**A server, that includes classes to stream videos from youtube or audio files given
 * by a client 
 * 
 * @author Mellich
 *
 */
public class YTJBServer {
	
	/**
	 * the standard port will be used, when no other port is given
	 */
	public static final int PORT = 12345;
	
	public static final String GAPLISTFILENAME = "gaplist.jb";
	
	/**
	 * the server socket to handle connections to the server
	 */
	private ServerSocket server;
	
	/**
	 * the connection waiter of the server
	 */
	private ConnectionWaiter waiter;
	
	/**
	 * the music scheduler of the server
	 */
	private TrackScheduler scheduler;
	
	/**
	 * a list of tracks, that are "wished" by clients to play
	 */
	private LinkedList<MusicTrack> wishList;
	
	/**
	 * a list of tracks, that will be used when the wish list is empty
	 */
	private LinkedList<MusicTrack> gapList;
	
	/**
	 * list of clients connected to the server
	 */
	private ArrayList<ConnectionHandler> clients;
	
	/**
	 * starts the server and makes him ready for work
	 */
	public void start(){
		try {
			waiter.start();
			scheduler.start();
			BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			r.readLine(); 																//giving a input will shut down the server
			IO.saveGapListToFile(gapList, GAPLISTFILENAME);
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
	
	public LinkedList<MusicTrack> getGapList(){
		return gapList;
	}
	
	public LinkedList<MusicTrack> getWishList(){
		return wishList;
	}
	
	public TrackScheduler getScheduler(){
		return scheduler;
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public synchronized void registerClient(ConnectionHandler c){
		clients.add(c);
		IO.printlnDebug(this, "Count of connected Clients: "+clients.size());
	}
	
	public synchronized void removeClient(ConnectionHandler c){
		clients.remove(c);
		IO.printlnDebug(this, "Count of connected Clients: "+clients.size());
	}
	
	public void notifyClients(int messageType){
		for (ConnectionHandler h: clients){
			h.notify(messageType);
		}
	}
	
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 */
	public YTJBServer(int port) {
			try {
				wishList = new LinkedList<MusicTrack>();
				clients = new ArrayList<ConnectionHandler>();
				gapList = IO.loadGapListFromFile(GAPLISTFILENAME);
				server = new ServerSocket(port);
				scheduler = new TrackScheduler(this);
				waiter = new ConnectionWaiter(this);
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
		YTJBServer s;
		if (args.length == 1){
			int port = Integer.parseInt(args[0]);
			s = new YTJBServer(port);
		} else {
			s = new YTJBServer(PORT);
		}
		s.start();
	}
	
}
