package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.LinkedList;

import network.MessageType;
import server.connectivity.Connection;
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
	private ArrayList<Connection> clients;
	
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
			IO.printlnDebug(this, "Server was shut down");
		} catch (InterruptedException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**adds a MusicTrack to a list
	 * 
	 * @param track the track to be added
	 * @param toWishList if true, track will be added to wish list
	 * @param atFirst if true, track will be added at the beginning of the list and at the and, if the value is false
	 */
	public synchronized void addToList(MusicTrack track,boolean toWishList, boolean atFirst){
		boolean isFirstTrack = false;		//are the lists empty before this track is added?
		if (gapList.isEmpty() && wishList.isEmpty())
			isFirstTrack = true;
		if (toWishList){
			if (atFirst)
				wishList.addFirst(track);
			else wishList.add(track);
		}
		else{
			if (atFirst)
				gapList.addFirst(track);
			else gapList.add(track);
		}
		if (isFirstTrack){     //if so, notify waiting scheduler
			scheduler.playableTrack.release();
			IO.printlnDebug(this, "First element in the lists");
		}
	}
	
	/**
	 * deletes a track from a list
	 * 
	 * @param fromWishList if true, the track will be deleted from the wish list, else from the gap list
	 * @param index the index of the track in the list
	 * @return the deleted track or null if the track does not exist
	 */
	public synchronized MusicTrack deleteFromList(boolean fromWishList,int index){
		try{
			if (fromWishList){
				return wishList.remove(index);
			}
			else{
				return gapList.remove(index);
			}
		}
		catch (IndexOutOfBoundsException e){
			IO.printlnDebug(this, "ERROR: Could not delete track from list: Index out of bounds!");
		}
		return null;
	}
	
	/**
	 * returns the titles of all tracks in a list 
	 * 
	 * @param fromWishList if true, use the wish list else the gap list
	 * @return a string with all the titles seperated through the standard seperator command
	 */
	public String getTitle(boolean fromWishList){
		StringBuilder response = new StringBuilder();
		if (fromWishList)
			for (MusicTrack m: wishList){
				response.append(m.getTitle()+MessageType.SEPERATOR);
			}
		else
			for (MusicTrack m: gapList){
				response.append(m.getTitle()+MessageType.SEPERATOR);
			}
		return response.toString();
	}
	
	public synchronized MusicTrack chooseNextTrack(){
		if (!wishList.isEmpty()){
			return wishList.removeFirst();
		}
		else {
			if (!gapList.isEmpty()){
				MusicTrack nextURL = gapList.removeFirst();
				gapList.add(nextURL);
				return nextURL;
			}
		}
		return null;
	}
	
	public void loadGapListFromFile(){
		IO.loadGapListFromFile(GAPLISTFILENAME, this);		
	}
	
	public boolean saveGapListToFile(){
		return IO.saveGapListToFile(gapList, GAPLISTFILENAME);
	}
	
	public TrackScheduler getScheduler(){
		return scheduler;
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public synchronized void registerClient(Connection c){
		clients.add(c);
		IO.printlnDebug(this, "Count of connected Clients: "+clients.size());
	}
	
	public synchronized void removeClient(Connection c){
		clients.remove(c);
		IO.printlnDebug(this, "Count of connected Clients: "+clients.size());
	}
	
	public void notifyClients(int messageType){
		for (Connection h: clients){
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
				clients = new ArrayList<Connection>();
				gapList = new LinkedList<MusicTrack>();
				GapListLoader listLoader = new GapListLoader(this);
				listLoader.start();
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
