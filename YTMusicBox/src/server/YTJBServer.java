package server;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.concurrent.Semaphore;

import javafx.application.Platform;
import network.MessageType;
import server.connectivity.Connection;
import server.connectivity.ConnectionWaiter;
import server.player.TrackScheduler;
import server.visuals.IdleViewer;
import utilities.IO;

/**A server, that includes classes to stream videos from youtube or audio files given
 * by a client 
 * 
 * @author Mellich
 *
 */
public class YTJBServer extends Thread {
	
	/**
	 * the standard port will be used, when no other port is given
	 */
	public static final int PORT = 12345;
	
	public static final String GAPLISTDIRECTORY = "/home/pi/.jbserver/";
	
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

	private IdleViewer idelViewer;
	
	private InitFileCommunicator initFile;
	
	private String currentGapList;
	
	private String[] gapLists;
	
	private GapListLoader gapListLoader;
	
	private Semaphore closePrompt = new Semaphore(0);
	
	
	/**
	 * starts the server and makes him ready for work
	 */
	public void startUp(){
		try {
			waiter.start();
			scheduler.start();
			//BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
			//r.readLine(); //giving a input will shut down the server
			closePrompt.acquire();
			IO.saveGapListToFile(gapList, GAPLISTDIRECTORY+currentGapList);
			scheduler.setRunning(false);
			scheduler.interrupt();
			waiter.setRunning(false);
			waiter.interrupt();	
			server.close();
			scheduler.join();
			waiter.join();
			this.showLogo(false);
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
		this.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
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
		MusicTrack temp = null;
		try{
			if (fromWishList){
				temp = wishList.remove(index);
				this.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
			}
			else{
				temp = gapList.remove(index);
				this.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
			}
		}
		catch (IndexOutOfBoundsException e){
			IO.printlnDebug(this, "ERROR: Could not delete track from list: Index out of bounds!");
		}
		return temp;
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
		MusicTrack temp = null;
		if (!wishList.isEmpty()){
			temp =  wishList.removeFirst();
			notifyClients(MessageType.LISTSUPDATEDNOTIFY);
		}
		else {
			if (!gapList.isEmpty()){
				temp = gapList.removeFirst();
				gapList.add(temp);
				notifyClients(MessageType.LISTSUPDATEDNOTIFY);
			}
		}
		return temp;
	}
	
	public void loadGapListFromFile(){
		gapList.clear();
		idelViewer.currentGaplist(currentGapList);
		this.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
		IO.loadGapListFromFile(GAPLISTDIRECTORY+currentGapList, this, idelViewer);		
	}
	
	public boolean saveGapListToFile(){
		boolean savedCorrectly = IO.saveGapListToFile(gapList, GAPLISTDIRECTORY+currentGapList);
		if (savedCorrectly){
			searchGapLists();
			this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY);
		}
		return savedCorrectly;
	}
	
	public boolean deleteGapList(String filename){
		filename = filename +".jb";
		boolean deletedCorrectly = IO.deleteGapList(GAPLISTDIRECTORY+filename);
		if (deletedCorrectly){
			this.searchGapLists();
			this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY);
		}
		return deletedCorrectly;
	}
	
	public TrackScheduler getScheduler(){
		return scheduler;
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public String[] getGapLists(){
		return gapLists;
	}
	
	public String getCurrentGapListName(){
		return currentGapList;
	}
	
	public boolean setGapList(String filename){
		IO.printlnDebug(this, "Setting as new gap list");
		filename = filename+".jb";
		currentGapList = filename;
		initFile.setStartUpGapList(currentGapList);
		IO.printlnDebug(this, "Start loading new gaplist");
		if (gapListLoader.isAlive()){
			gapListLoader.interrupt(); 
			try {
				gapListLoader.join();
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "ERROR: Exception thrown while waiting for gap list loader to stop");
			}
		}
		gapListLoader = new GapListLoader(this);
		gapListLoader.start();
		return true;
	}
	
	public String[] readOutGapList(String filename){
		IO.printlnDebug(this, "Reading out gap list");
		return IO.readOutGapList(GAPLISTDIRECTORY+filename);
	}
	
	public synchronized boolean switchWithUpper(int index){
		if (index > 0){
			MusicTrack upper = gapList.get(index - 1);
			gapList.set(index - 1, gapList.get(index));
			gapList.set(index, upper);
			IO.printlnDebug(this, "notify clients");
			this.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
			return true;
		}
		else return false;
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
	
	public void showLogo(boolean show){
		Platform.runLater(() -> idelViewer.showLogo(show));
	}
	
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 */
	public YTJBServer(int port,IdleViewer idelViewer) {
			try {
				this.idelViewer = idelViewer;
				wishList = new LinkedList<MusicTrack>();
				clients = new ArrayList<Connection>();
				gapList = new LinkedList<MusicTrack>();
				initFile = new InitFileCommunicator(GAPLISTDIRECTORY);
				currentGapList = initFile.getStartUpGapList();
				searchGapLists();
				gapListLoader = new GapListLoader(this);
				gapListLoader.start();
				server = new ServerSocket(port);
				scheduler = new TrackScheduler(this);
				waiter = new ConnectionWaiter(this);
				String ip = getIpAddress();
				Platform.runLater(() -> idelViewer.editConnectionDetails(ip, port));
				IO.printlnDebug(this, "New server opened on address "+getIpAddress()+" port "+port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void searchGapLists(){
		gapLists = IO.getGapLists(GAPLISTDIRECTORY);
	}
	
	private String getIpAddress() { 
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        String ipAddress=inetAddress.getHostAddress().toString();
                        return ipAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        	IO.printlnDebug(this, "Could not find out ip address");
        }
        return null; 
}
	
	@Override
	public void run() {
		super.run();
		this.startUp();
	}
	

	
}
