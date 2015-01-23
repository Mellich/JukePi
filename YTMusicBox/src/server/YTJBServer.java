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

import messages.MessageType;
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
public class YTJBServer implements Server {
	
	/**
	 * the standard port will be used, when no other port is given
	 */
	public static final int PORT = 12345;
	
	public String workingDirectory;
	
	/**
	 * the server socket to handle connections to the server
	 */
	private ServerSocket server;
	
	private int port;
	
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
	private ArrayList<Connection> notifiables;
	
	private ArrayList<Connection> player;
	
	private InitFileCommunicator initFile;
	
	private String currentGapList;
	
	private String[] gapLists;
	
	private GapListLoader gapListLoader;
	
	private Semaphore closePrompt = new Semaphore(0);
	
	private Semaphore playerFinished = new Semaphore(0);
	
	private int maxGapListTracks = 0;
	
	private Thread connectionBroadcast = null;
	
	
	/**
	 * starts the server and makes him ready for work
	 */
	public void startUp(){
		try {
			waiter.start();
			scheduler.start();
			//ProcessCommunicator.startPlayer(getIpAddress(),port,workingDirectory+"clientplayer.jar");
			this.connectionBroadcast = new Thread(new ConnectionBroadcast(getIpAddress(),port,this));
			this.connectionBroadcast.start();
			closePrompt.acquire();
			IO.saveGapListToFile(gapList, workingDirectory+currentGapList);
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
	
	public String getWorkingDir(){
		return workingDirectory;
	}
	
	private synchronized String[] listToArray(LinkedList<MusicTrack> list){
		String[] result = new String[list.size()];
		for (int i = 0; i < result.length; i++){
			result[i] = list.get(i).getTitle();
		}
		return result;
	}
	
	public int getCurrentClientCount(){
		return notifiables.size();
	}
	
	public int getCurrentPlayerCount(){
		return player.size();
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
			this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
		}
		else{
			if (atFirst)
				gapList.addFirst(track);
			else gapList.add(track);
			this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
		}
		if (isFirstTrack){     //if so, notify waiting scheduler
			scheduler.notifyPlayableTrack();
			IO.printlnDebug(this, "First element in the lists");
		}
	}
	
	public int getPlayerCount(){
		return player.size();
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
				this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
			}
			else{
				temp = gapList.remove(index);
				this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
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
	
	/**chooses the next track for play back
	 * 
	 * @return the musictrack to play back
	 */
	public synchronized MusicTrack chooseNextTrack(){
		MusicTrack temp = null;
		if (!wishList.isEmpty()){
			temp =  wishList.removeFirst();
			notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
		}
		else {
			if (!gapList.isEmpty()){
				temp = gapList.removeFirst();
				gapList.add(temp);
				notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
			}
		}
		return temp;
	}
	
	/**
	 * loads current gap list from a file
	 */
	public void loadGapListFromFile(){
		gapList.clear();
		String[] args = new String[1];
		args[0] = currentGapList;
		this.notifyClients(MessageType.GAPLISTCHANGEDNOTIFY,args);
		this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
		if(!IO.loadGapListFromFile(workingDirectory+currentGapList, this)){
			this.searchGapLists();
			this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY, gapLists);
		}
	}
	
	/**saves the gap list to a file
	 * 
	 * @return true, when no error occurred
	 */	
	public boolean saveGapListToFile(){
		boolean savedCorrectly = IO.saveGapListToFile(gapList, workingDirectory+currentGapList);
		if (savedCorrectly){
			searchGapLists();
			this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY,gapLists);
		}
		return savedCorrectly;
	}
	
	public int getCurrentLoadedGapListTracksCount(){
		return this.gapList.size();
	}
	
	public int getMaxLoadedGapListTracksCount(){
		return this.maxGapListTracks;
	}
	
	public void setMaxGapListTrackCount(int max){
		this.maxGapListTracks = max;
	}
	
	public boolean deleteGapList(String filename){
		boolean deletedCorrectly = false;
		filename = filename +".jb";
		if (!filename.equals(currentGapList)){
			deletedCorrectly = IO.deleteGapList(workingDirectory+filename);
			if (deletedCorrectly){
				this.searchGapLists();
				this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY,gapLists);
			}
		}
		return deletedCorrectly;
	}
	
	public TrackScheduler getScheduler(){
		return scheduler;
	}
	
	public ServerSocket getServerSocket(){
		return server;
	}
	
	public String[] getGapListNames(){
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
		return IO.readOutGapList(workingDirectory+filename);
	}
	
	public synchronized boolean switchWithUpper(int index){
		if (index > 0){
			MusicTrack upper = gapList.get(index - 1);
			gapList.set(index - 1, gapList.get(index));
			gapList.set(index, upper);
			IO.printlnDebug(this, "notify clients");
			this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
			return true;
		}
		else return false;
	}
	
	public synchronized void registerNotifiable(Connection c){
		notifiables.add(c);
		String[] s = new String[1];
		s[0] = ""+notifiables.size();
		this.notifyClients(MessageType.CLIENTCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Notifiables: "+notifiables.size());
	}
	
	public synchronized void registerPlayer(Connection c){
		player.add(c);
		if (player.size() == 1)
			scheduler.notifyPlayerAvailable();
		String[] s = new String[1];
		s[0] = ""+player.size();
		this.notifyClients(MessageType.PLAYERCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Players: "+player.size());
	}
	
	public synchronized void removePlayer(Connection c){
		if(player.contains(c)){
			player.remove(c);
			playerFinished.release();
			String[] s = new String[1];
			s[0] = ""+player.size();
			this.notifyClients(MessageType.PLAYERCOUNTCHANGEDNOTIFY,s);
			IO.printlnDebug(this, "Count of connected Players: "+player.size());
		}
	}
	
	public synchronized void removeNotifiable(Connection c){
		notifiables.remove(c);
		String[] s = new String[1];
		s[0] = ""+notifiables.size();
		this.notifyClients(MessageType.CLIENTCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Notifiables: "+notifiables.size());
	}
	
	public void playerHasFinished(){
		playerFinished.release();
	}
	
	public void waitForPlayerToFinish(){
		try {
			int finishedCount = 0;
			while (finishedCount < player.size()){
					playerFinished.acquire();
					finishedCount++;
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "ERROR: Interrupted while waiting for players to finish");
		}
	}
	
	public void notifyClients(int messageType,String[] args){
		for (Connection h: notifiables){
			h.notify(messageType,args);
		}
	}	
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 */
	public YTJBServer(int port) {
			try {
				this.port = port;
				IO.setServer(this);
				this.workingDirectory = YTJBServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				int lastdir = this.workingDirectory.lastIndexOf("/");
				if (lastdir == this.workingDirectory.length() - 1){
					this.workingDirectory = this.workingDirectory.substring(0, lastdir);
					lastdir = this.workingDirectory.lastIndexOf("/");
					this.workingDirectory = this.workingDirectory.substring(0, lastdir + 1);
				}
				else{
					this.workingDirectory = this.workingDirectory.substring(0, lastdir + 1);					
				}
				System.out.println(this.workingDirectory);
				wishList = new LinkedList<MusicTrack>();
				notifiables = new ArrayList<Connection>();
				player = new ArrayList<Connection>();
				gapList = new LinkedList<MusicTrack>();
				initFile = new InitFileCommunicator(workingDirectory);
				currentGapList = initFile.getStartUpGapList();
				searchGapLists();
				gapListLoader = new GapListLoader(this);
				gapListLoader.start();
				server = new ServerSocket(port);
				scheduler = new TrackScheduler(this);
				waiter = new ConnectionWaiter(this);
				IO.printlnDebug(this, "New server opened on address "+getIpAddress()+" port "+port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	
	public void searchGapLists(){
		gapLists = IO.getGapLists(workingDirectory);
	}
	
	public String getIpAddress() { 
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
	
	public void shutDown(){
		closePrompt.release();
	}
	
	public static void main(String[] args) {
		YTJBServer server = new YTJBServer(22222);
		server.startUp();
	}
	

	
}
