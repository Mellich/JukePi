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
import messages.Permission;
import server.connectivity.Connection;
import server.connectivity.ConnectionWaiter;
import server.player.TrackScheduler;
import utilities.ConditionVariable;
import utilities.IO;
import utilities.InitFileCommunicator;
import utilities.ProcessCommunicator;
import utilities.ColumnType;

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
	
	public static final long CURRENT_VERSION = 902L;

	private static final String INITFILENAME = "jukepi.ini";
	
	private long version = -1L;
	
	public String workingDirectory;
	
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

	private URLParser urlParser;
	
	private ConditionVariable con = new ConditionVariable();
	
	
	public ConditionVariable getLock(){
		return con;
	}
	
	public boolean removeAllVotes(){
		return votingController.removeAllVotes();
	}
	
	public String getPW(Permission p){
		if (p.equals(Permission.ADMIN)){
			int adminCount = 0;
			ArrayList<Connection> timeoutConns = new ArrayList<Connection>(); //check for outdated connections
			for (Connection c : notifiables){
				if (c.checkPermission(p)){
					adminCount++;
					if (c.checkTimeout()){
						timeoutConns.add(c);
					}
				}
			}
			if (adminCount >= Long.valueOf(initFile.getValue(ColumnType.MAXADMINCOUNT)) && timeoutConns.size() == 0){
				IO.printlnDebug(this, "Maximal Admin count reached! Permission denied! Admin count: "+adminCount);
				return null;
			}
			for (Connection c : timeoutConns){
				c.closeConnection();
				notifiables.remove(c);
			}
		}
		if (p.equals(Permission.PLAYER) && player.size() >= Long.valueOf(initFile.getValue(ColumnType.MAXPLAYERCOUNT))){
			ArrayList<Connection> timeoutConns = new ArrayList<Connection>(); //check for outdated connections
			for (Connection c :player){
				if (c.checkTimeout())
					timeoutConns.add(c);
			}
			if (timeoutConns.size() == 0){
				IO.printlnDebug(this, "Maximal Player count reached! Permission denied! Player count: "+player.size());
				return null;
			}
			for (Connection c : timeoutConns){
				c.closeConnection();
				player.remove(c);
			}
		}
		switch (p){
		case PLAYER: return initFile.getValue(ColumnType.PLAYERPW);
		case PLAYBACK: return initFile.getValue(ColumnType.PLAYBACKPW);
		case DEBUGGING: return initFile.getValue(ColumnType.DEBUGPW);
		case ADMIN: return initFile.getValue(ColumnType.ADMINPW);
		default: return null;
		}
	}
	
	
	/**
	 * starts the server and makes him ready for work
	 */
	public boolean startUp() throws IOException{
			server = new ServerSocket(Integer.parseInt(initFile.getValue(ColumnType.PORT)));
			currentGapList = initFile.getValue(ColumnType.STARTUPGAPLIST);
			searchGapLists();	
			urlParser = new URLParser(this);
			gapListLoader = new GapListLoader(this);
			gapListLoader.start();
			waiter.start();
			scheduler.start();
			urlParser.startUp();
			int port = Integer.parseInt(initFile.getValue(ColumnType.PORT));
			if (Boolean.parseBoolean(initFile.getValue(ColumnType.STARTPLAYER)))
				ProcessCommunicator.startPlayer(getIpAddress(),port,workingDirectory);
			this.connectionBroadcast = new Thread(new ConnectionBroadcast(getIpAddress(),port,this));
			this.connectionBroadcast.start();
			IO.printlnDebug(this, "New server opened on address "+getIpAddress()+" port "+port);
			return true;
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
			result.add(""+list.get(i).getParseStatus());
			result.add(""+list.get(i).getShortURL());
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
		con.lock();
		boolean existsParsed = this.existsParsedURL();
		if (toWishList){
			if (atFirst){
				if (!wishList.isEmpty()){
					int i = wishList.size();
					while(i > 0 && wishList.get(i - 1).getVoteCount() == 0){
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
			if (this.getMaxLoadedGapListTracksCount() <= gapList.size())
				this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
		}
		if (!existsParsed && this.existsParsedURL()){
			con.getPlayableTrackAvailable().signal();
			IO.printlnDebug(this, "Neuer track verfügbar");
		}
		con.unlock();
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
				this.setMaxGapListTrackCount(this.getMaxLoadedGapListTracksCount() - 1);
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
				response.append(m.getTrackID()+MessageType.SEPERATOR+m.getTitle()+MessageType.SEPERATOR+m.getVoteCount()+MessageType.SEPERATOR+m.getParseStatus()+MessageType.SEPERATOR+m.getShortURL()+MessageType.SEPERATOR);
			}
		else
			for (MusicTrack m: gapList){
				response.append(m.getTrackID()+MessageType.SEPERATOR+m.getTitle()+MessageType.SEPERATOR+m.getVoteCount()+MessageType.SEPERATOR+m.getParseStatus()+MessageType.SEPERATOR+m.getShortURL()+MessageType.SEPERATOR);
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
	
	public synchronized MusicTrack getNextNotParsedURL(){
		for (MusicTrack m : wishList){
			if (!m.isParsing() && !m.isReady() && !m.isError()){
				return m;
			}
		}
		for (MusicTrack m : gapList){
			if (!m.isReady() && !m.isParsing() && !m.isError()){
				return m;
			}
		}
		return null;
	}
	
	public synchronized boolean existsParsedURL(){
		for (MusicTrack m : wishList){
			if (m.isReady()){
				return true;
			}
		}
		for (MusicTrack m : gapList){
			if (m.isReady()){
				return true;
			}
		}
		return false;		
	}
	
	/**
	 * loads current gap list from a file
	 */
	public void loadGapListFromFile(){
		con.lock();
		gapList.clear();
		ArrayList<String> args = new ArrayList<String>();
		args.add(currentGapList);
		this.notifyClients(MessageType.GAPLISTCHANGEDNOTIFY,args);
		this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
		if(!IO.loadGapListFromFile(workingDirectory+currentGapList, this)){
			this.searchGapLists();
			this.notifyClients(MessageType.GAPLISTCOUNTCHANGEDNOTIFY, gapLists);
		}
		else{
			con.getNotParsedTrackAvailable().signalAll();;
		}
		con.unlock();
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
		initFile.setValue(ColumnType.STARTUPGAPLIST,currentGapList);
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
	
	public boolean addSongToOtherList(long trackID){
		for (MusicTrack m : gapList){
			if (m.getTrackID() == trackID){
				wishList.add(new MusicTrack(m));
				this.notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(wishList));
				return true;
			}
		}
		for (MusicTrack m : wishList){
			if (m.getTrackID() == trackID){
				gapList.add(new MusicTrack(m));
				this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
				return true;
			}
		}
		return false;
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
			if (track != null){
				int oldTrackIndex = gapList.indexOf(track);
				gapList.remove(track);
				if ((gapList.size() == oldTrackIndex && !withUpper)){ //if track is last element in list set it to first
					gapList.add(0,track);
				}
				else if ((0 == oldTrackIndex && withUpper)){ // if track is first element in list set it to last
					gapList.add(track);			
				}
				else{
					int newTrackIndex;
					if (withUpper)
						newTrackIndex = oldTrackIndex - 1;
					else newTrackIndex = oldTrackIndex + 1;
					gapList.add(newTrackIndex, track);
					/*int newTrackIndex;
					if (withUpper)
						newTrackIndex = oldTrackIndex - 1;
					else newTrackIndex = oldTrackIndex + 1;
					if (newTrackIndex < 0)
						newTrackIndex = gapList.size() - 1;
					if (newTrackIndex >= gapList.size())
						newTrackIndex = 0;
					gapList.set(oldTrackIndex, gapList.get(newTrackIndex));
					gapList.set(newTrackIndex, track);*/
				}
				this.notifyClients(MessageType.GAPLISTUPDATEDNOTIFY,this.listToArray(gapList));
				return true;
			}
			else return false;
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
		con.lock();
		player.add(c);
		c.setIsPlayer(true);
		if (player.size() == 1)
			con.getPlayerAvailable().signal();;
		ArrayList<String> s = new ArrayList<String>();
		s.add(""+player.size());
		this.notifyClients(MessageType.PLAYERCOUNTCHANGEDNOTIFY,s);
		IO.printlnDebug(this, "Count of connected Players: "+player.size());
		con.unlock();
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
					IO.printlnDebug(this, "Playercount: "+player.size()+", finishedCount: "+finishedCount);
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
		if (messageType == MessageType.GAPLISTUPDATEDNOTIFY ||
			messageType == MessageType.WISHLISTUPDATEDNOTIFY){		
			con.lock();
			con.getNotParsedTrackAvailable().signal();
			con.unlock();
		}
	}	
	
	public void notifyListUpdate(MusicTrack track){
		if (wishList.contains(track)){
			notifyClients(MessageType.WISHLISTUPDATEDNOTIFY,this.listToArray(this.wishList));
		}else{
			notifyClients(MessageType.GAPLISTUPDATEDNOTIFY, this.listToArray(gapList));
		}
	}
	
	/**
	 * creates new instance of a server
	 * 
	 * @param port the port used for the server socket
	 * @throws BindException is thrown if the port is already in use
	 */
	public YTJBServer() throws BindException {
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
			initFile = new InitFileCommunicator(workingDirectory,INITFILENAME);
			scheduler = new TrackScheduler(this);
			waiter = new ConnectionWaiter(this);
			version = CURRENT_VERSION;
	}
	
	public YTJBServer(int port, String adminPW, String playerPW) throws BindException {
		this(port);
		initFile.setValue(ColumnType.ADMINPW, adminPW);
		initFile.setValue(ColumnType.PLAYERPW, playerPW);
}
	
	public YTJBServer(int port) throws BindException {
		this();
		if (port > 0){
			initFile.setValue(ColumnType.PORT, ""+port);
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
			urlParser.shutDown();
			IO.printlnDebug(this, "Server was shut down");
		} catch (IOException | InterruptedException e) {
			IO.printlnDebug(this, "Error while closing server");
		}
	}
	
	private boolean parseArguments(String[] args){
		for (String s: args){
			int equalPos = s.indexOf("=");
			String input = "";
			if (equalPos < s.length())
				input = s.substring(equalPos + 1);
			String call = s.substring(0, equalPos);
			if (call.equals("-playerPW")){
				initFile.setValue(ColumnType.PLAYERPW, input);
			}else if (call.equals("-debugPW")){
				initFile.setValue(ColumnType.DEBUGPW, input);
			}else if (call.equals("-gaplistPW")){
				initFile.setValue(ColumnType.ADMINPW, input);
			}else if (call.equals("-playbackPW")){
				initFile.setValue(ColumnType.PLAYBACKPW, input);
			}else if (call.equals("-port")){
				initFile.setValue(ColumnType.PORT, input);
			}else if (call.equals("-help")){
				printHelp();
				return false;
			}else{
				IO.printlnDebug(this, "Unknown argument: "+call+". Aborting...");
				printHelp();
				return false;
			}
		}
		return true;
	}
	
	private static void printHelp() {
		System.out.println("Here you can see the help info of this program soon. ;)");
	}


	public static void main(String[] args) {
		YTJBServer server;
		try {
			server = new YTJBServer();
			if (server.parseArguments(args)){
				server.updateYoutubeDL();
				server.startUp();
			}
		} catch (IOException e) {
			IO.printlnDebug(null, "given port is in use!");
			e.printStackTrace();
		}
	}


	public long getVersion() {
		return version;
	}

	@Override
	public boolean updateYoutubeDL() {
		return 	ProcessCommunicator.updateYoutubeDL(workingDirectory);
	}

	@Override
	public void searchGapListFiles() {
		searchGapLists();
		
	}
	
	
}
