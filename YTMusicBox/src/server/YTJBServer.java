package server;

import java.io.IOException;
import java.net.BindException;
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
import utilities.ProcessCommunicator;

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
	
	private ArrayList<String> gapLists;
	
	private GapListLoader gapListLoader;
	
	private Semaphore playerFinished = new Semaphore(0);
	
	private int maxGapListTracks = 0;
	
	private Thread connectionBroadcast = null;
	
	private VotingController votingController;
	
	
	/**
	 * starts the server and makes him ready for work
	 */
	public void startUp(){
			ProcessCommunicator.updateYoutubeDL(workingDirectory);
			currentGapList = initFile.getStartUpGapList();
			searchGapLists();				
			gapListLoader = new GapListLoader(this);
			gapListLoader.start();
			waiter.start();
			scheduler.start();
			ProcessCommunicator.startPlayer(getIpAddress(),port,workingDirectory);
			this.connectionBroadcast = new Thread(new ConnectionBroadcast(getIpAddress(),port,this));
			this.connectionBroadcast.start();
			IO.printlnDebug(this, "New server opened on address "+getIpAddress()+" port "+port);
	}
	
	public String getWorkingDir(){
		return workingDirectory;
	}
	
	public long getVote(long macAddress){
		return votingController.getVotedTrackID(macAddress);
	}
	
	public boolean removeVote(long macAddress){
		if (votingController.removeVote(macAddress)){
			this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY, this.listToArray(wishList));
			return true;
		}
		return false;		
	}
	
	public boolean addVote(long trackID,long macAddress){
		if (votingController.addVote(trackID, macAddress)){
			this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY, this.listToArray(wishList));
			return true;
		}
		return false;
	}
	
	private synchronized ArrayList<String> listToArray(LinkedList<MusicTrack> list){
		ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++){
			result.add(""+list.get(i).getTrackID());
			result.add(list.get(i).getTitle());
			result.add(""+list.get(i).getVoteCount());
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
			if (atFirst){
				if (!wishList.isEmpty()){
					int i = wishList.size();
					while(wishList.get(i - 1).getVoteCount() == 0){
						i--;
					}
					wishList.add(i, track);
				} else wishList.add(track);
			}
			else wishList.add(track);
			this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
		}
		else{
			if (atFirst)
				gapList.addFirst(track);
			else gapList.add(track);
			if (!track.isFromSavedGapList())
				this.setMaxGapListTrackCount(this.getMaxLoadedGapListTracksCount() + 1);
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
	public synchronized MusicTrack deleteFromList(boolean fromWishList,long trackID){
		MusicTrack temp = null;
		try{
			if (fromWishList){
				for (MusicTrack m: wishList){
					if (m.getTrackID() == trackID){
						temp = m;
						break;
					}
				}
				wishList.remove(temp);
				this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
			}
			else{
				for (MusicTrack m: gapList){
					if (m.getTrackID() == trackID){
						temp = m;
						break;
					}
				}
				gapList.remove(temp);
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
				response.append(m.getTrackID()+MessageType.SEPERATOR+m.getTitle()+MessageType.SEPERATOR+m.getVoteCount()+MessageType.SEPERATOR);
			}
		else
			for (MusicTrack m: gapList){
				response.append(m.getTrackID()+MessageType.SEPERATOR+m.getTitle()+MessageType.SEPERATOR+m.getVoteCount()+MessageType.SEPERATOR);
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
			votingController.removeTrack(temp.getTrackID());
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
		ArrayList<String> args = new ArrayList<String>();
		args.add(currentGapList);
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
		boolean savedCorrectly = IO.saveGapListToFile(gapList, workingDirectory+currentGapList,this);
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
		return gapLists.toArray(new String[gapLists.size()]);
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
		IO.printlnDebug(this, "Reading out gap list: "+filename);
		return IO.readOutGapList(workingDirectory+filename);
	}
	
	public synchronized boolean switchTrackPosition(long trackID, boolean withUpper){
		if (trackID > -1L){
			MusicTrack track = null;
			for (MusicTrack m : gapList){
				if (trackID == m.getTrackID()){
					track = m;
					break;
				}
			}
			int oldTrackIndex = gapList.indexOf(track) ;
			int newTrackIndex;
			if (withUpper)
				newTrackIndex = oldTrackIndex - 1;
			else newTrackIndex = oldTrackIndex + 1;
			if (newTrackIndex < 0)
				newTrackIndex = gapList.size() - 1;
			if (newTrackIndex >= gapList.size())
				newTrackIndex = 0;
			gapList.set(oldTrackIndex, gapList.get(newTrackIndex));
			gapList.set(newTrackIndex, track);
			this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
			return true;
		}
		else return false;
	}
	
	public synchronized void registerNotifiable(Connection c){
		notifiables.add(c);
		ArrayList<String> s = new ArrayList<String>();
		s.add( ""+notifiables.size());
		this.notifyClients(MessageType.CLIENTCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Notifiables: "+notifiables.size());
	}
	
	public synchronized void registerPlayer(Connection c){
		player.add(c);
		c.setIsPlayer(true);
		if (player.size() == 1)
			scheduler.notifyPlayerAvailable();
		ArrayList<String> s = new ArrayList<String>();
		s.add(""+player.size());
		this.notifyClients(MessageType.PLAYERCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Players: "+player.size());
	}
	
	public synchronized void removePlayer(Connection c){
		if(player.contains(c)){
			player.remove(c);
			c.setIsPlayer(false);
			playerFinished.release();
			ArrayList<String> s = new ArrayList<String>();
			s.add(""+player.size());
			this.notifyClients(MessageType.PLAYERCOUNTCHANGEDNOTIFY,s);
			IO.printlnDebug(this, "Count of connected Players: "+player.size());
		}
	}
	
	public synchronized void removeNotifiable(Connection c){
		notifiables.remove(c);
		ArrayList<String> s = new ArrayList<String>();
		s.add(""+notifiables.size());
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
	
	public void notifyClients(int messageType,ArrayList<String> arrayList){
		for (Connection h: notifiables){
			h.notify(messageType,arrayList);
		}
	}	
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 * @throws BindException is thrown if the port is already in use
	 */
	public YTJBServer(int port) throws BindException {
			try {
				this.port = port;
				IO.setServer(this);
				this.workingDirectory = YTJBServer.class.getProtectionDomain().getCodeSource().getLocation().getPath();
				this.workingDirectory = this.workingDirectory.replace("%20", " ");
				int lastdir = this.workingDirectory.lastIndexOf("/");
				if (lastdir == this.workingDirectory.length() - 1){
					this.workingDirectory = this.workingDirectory.substring(0, lastdir);
					lastdir = this.workingDirectory.lastIndexOf("/");
					this.workingDirectory = this.workingDirectory.substring(0, lastdir + 1);
				}
				else{
					this.workingDirectory = this.workingDirectory.substring(0, lastdir + 1);					
				}
				wishList = new LinkedList<MusicTrack>();
				notifiables = new ArrayList<Connection>();
				votingController = new VotingController(wishList);
				player = new ArrayList<Connection>();
				gapList = new LinkedList<MusicTrack>();
				initFile = new InitFileCommunicator(workingDirectory);
				server = new ServerSocket(port);
				scheduler = new TrackScheduler(this);
				waiter = new ConnectionWaiter(this);
			} catch(BindException e){
				throw new BindException();
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
		try {
			scheduler.setRunning(false);
			scheduler.interrupt();
			waiter.setRunning(false);
			waiter.interrupt();	
			server.close();
			scheduler.join();
			waiter.join();
			connectionBroadcast.interrupt();
			IO.printlnDebug(this, "Server was shut down");
		} catch (IOException | InterruptedException e) {
			IO.printlnDebug(this, "Error while closing server");
		}
	}
	
	public static void main(String[] args) {
		YTJBServer server;
		try {
			server = new YTJBServer(22222);
			server.startUp();
		} catch (BindException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	
}
