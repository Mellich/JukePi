package client.serverconnection.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import messages.MessageType;
import messages.ParseStatus;
import client.ServerAddress;
import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.listener.PauseResumeNotificationListener;
import client.listener.ResponseListener;
import client.listener.SeekNotificationListener;
import client.serverconnection.ServerConnectionNotifier;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.UDPTimeoutException;
import client.serverconnection.functionality.LowLevelServerConnection;
import client.serverconnection.functionality.YTJBLowLevelServerConnection;

/**
 * The Implementation of {@link ServerConnection} and {@link ServerConnectionNotifier}.
 * @author Mellich
 * @version 1.0
 */
public class YTJBServerConnection implements ServerConnection, ServerConnectionNotifier {
	
	/**
	 * The Listeners for seekNotifications.
	 */
	private List<SeekNotificationListener> seekNotificationListener;
	
	/**
	 * The Listeners for gapListNotifications.
	 */
	private List<GapListNotificationListener> gapListNotificationListener;
	
	/**
	 * The Listeners for pauseResumeNotifications.
	 */
	private List<PauseResumeNotificationListener> pauseResumeNotificationListener;
	
	/**
	 * The Listeners for defaultNotifications.
	 */
	private List<DefaultNotificationListener> defaultNotificationListener;
	
	/**
	 * The Listeners for debugNotifications.
	 */
	private List<DebugNotificationListener> debugNotificationListener;
	
	/**
	 * The {@link LowLevelServerConnection} to the Server.
	 */
	private LowLevelServerConnection serverConnection;
	
	/**
	 * Determines, if the Connection was established.
	 */
	private boolean connected = false;
	
	/**
	 * The interval between each connectivity check.
	 */
	private int checkInterval = 0;
	
	/**
	 * Determines, if the Client is an Android Application.
	 */
	private boolean isAndroid;
	
	/**
	 * Creates a new Instance of the ServerConnection with default values of 
	 * {@link #checkInterval} ({@code 0}) and {@link #isAndroid} ({@code false}).
	 * @since 1.0
	 */
	public YTJBServerConnection() {
		this(0,false);
	}
	
	/**
	 * Creates a new ServerConnection with the given Parameters.
	 * @param checkInterval	The Interval between each connectivity check.
	 * @param isAndroid	Determines, if the Client is an Android Application.
	 * @since 1.0
	 */
	public YTJBServerConnection(int checkInterval,boolean isAndroid) {
		defaultNotificationListener = new ArrayList<DefaultNotificationListener>();
		debugNotificationListener = new ArrayList<DebugNotificationListener>();
		seekNotificationListener = new ArrayList<SeekNotificationListener>();
		gapListNotificationListener = new ArrayList<GapListNotificationListener>();
		pauseResumeNotificationListener = new ArrayList<PauseResumeNotificationListener>();
		connected = false;
		this.isAndroid = isAndroid;
		this.checkInterval = checkInterval;
	}
	
	/**
	 * Creates a new ServerConnection with the given checkInterval and {@code false} as value
	 * of {@link #isAndroid}.
	 * @param checkInterval2	The Interval between each connectivity check.
	 * @since 1.0
	 */
	public YTJBServerConnection(int checkInterval2) {
		this(checkInterval2,false);
	}

	/**
	 * Converts the String-Array of Song-Names to an Array of Songs.
	 * @param table	The String-Array with the names of the Songs.
	 * @return	The Songs as an Array.
	 * @since 1.0
	 */
	private Song[] stringArrayToSongArray(String[] table){
		long ownVote = Long.parseLong(table[0]);
		int itemCount = 5;
		int i = 0;
		Song[] result = new Song[(table.length - 1) / itemCount];
		while (i < result.length){
			long trackID = Long.parseLong(table[itemCount*i + 1]);
			result[i] = new Song(trackID,table[itemCount*i + 2],Integer.parseInt(table[itemCount*i + 3]),(ownVote == trackID),ParseStatus.valueOf(table[itemCount*i + 4]),table[itemCount*i + 5]);
			i++;
		}
		return result;
	}

	@Override
	public void onNotify(int notifyType,String[] args) {
		switch(notifyType){
		case MessageType.PAUSERESUMENOTIFY: for(PauseResumeNotificationListener l: pauseResumeNotificationListener) l.onPauseResumeNotify(Boolean.parseBoolean(args[0]));
			break;
		case MessageType.NEXTTRACKNOTIFY:for(DefaultNotificationListener l: defaultNotificationListener) l.onNextTrackNotify(args[0],args[1],Boolean.parseBoolean(args[2]));
			break;
		case MessageType.GAPLISTUPDATEDNOTIFY:for(GapListNotificationListener l: gapListNotificationListener) l.onGapListUpdatedNotify(stringArrayToSongArray(args));
			break;
		case MessageType.WISHLISTUPDATEDNOTIFY:for(DefaultNotificationListener l: defaultNotificationListener) l.onWishListUpdatedNotify(stringArrayToSongArray(args));
			break;
		case MessageType.GAPLISTCOUNTCHANGEDNOTIFY:for(GapListNotificationListener l: gapListNotificationListener) l.onGapListCountChangedNotify(args);
			break;
		case MessageType.GAPLISTCHANGEDNOTIFY:for(GapListNotificationListener l: gapListNotificationListener) l.onGapListChangedNotify(args[0]);
			break;
		case MessageType.CLIENTCOUNTCHANGEDNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onClientCountChangedNotify(Integer.parseInt(args[0]));
			break;
		case MessageType.PLAYERCOUNTCHANGEDNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onPlayerCountChangedNotify(Integer.parseInt(args[0]));
			break;
		case MessageType.DEBUGOUTPUTNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onNewOutput(args[0]);
		break;
		case MessageType.SEEKNOTIFY: for(SeekNotificationListener l: seekNotificationListener) l.onSeekNotify(Boolean.parseBoolean(args[0]));
			break;
		case MessageType.DISCONNECT: for(DefaultNotificationListener l: defaultNotificationListener) l.onDisconnect();
										connected = false;
		}

	}

	@Override
	public void addToList(ResponseListener response, String url,
			boolean toWishList, boolean toBack) {
		if (toWishList && toBack)
			this.serverConnection.sendMessage(response, MessageType.YOUTUBE, url);
		else if (!toWishList && toBack)
			this.serverConnection.sendMessage(response, MessageType.GAPYOUTUBE, url);
		else if (toWishList && !toBack)
			this.serverConnection.sendMessage(response, MessageType.BEGINNINGYOUTUBE, url);
		else if (!toWishList && !toBack)
			this.serverConnection.sendMessage(response, MessageType.GAPBEGINNINGYOUTUBE, url);	
	}

	@Override
	public void deleteFromList(ResponseListener response, Song s) {
		this.serverConnection.sendMessage(response, MessageType.DELETEFROMGAPLIST, ""+s.getTrackID());
	}

	@Override
	public void getCurrentTrackTitle(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETCURRENTTRACK);
		
	}

	@Override
	public void skip(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.SKIP);
		
	}

	@Override
	public void pauseResume(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.PAUSERESUME);
	}

	@Override
	public void switchToGapList(ResponseListener response, String name) {
		this.serverConnection.sendMessage(response, MessageType.LOADGAPLIST,name);
		
	}

	@Override
	public void deleteGapList(ResponseListener response, String name) {
		this.serverConnection.sendMessage(response, MessageType.DELETEGAPLIST,name);
	}

	@Override
	public void getAvailableGapLists(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETAVAILABLEGAPLISTS);
		
	}

	@Override
	public void saveGapList(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GAPLISTSAVETOFILE);
		
	}

	@Override
	public void getCurrentGapListName(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETCURRENTGAPLISTNAME);
	}

	@Override
	public void getTitleFromGapList(ResponseListener response, String name) {
		this.serverConnection.sendMessage(response, MessageType.GETTITLEFROMGAPLIST,name);
	}

	@Override
	public void getCurrentPlaybackStatus(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETCURRENTPLAYBACKSTATUS);
	}

	@Override
	public void setGapListTrackUp(ResponseListener response, long trackID) {
		this.serverConnection.sendMessage(response, MessageType.GAPLISTTRACKUP,""+trackID);
	}

	@Override
	public void setGapListTrackDown(ResponseListener response, long index) {
		this.serverConnection.sendMessage(response, MessageType.GAPLISTTRACKDOWN,""+index);
	}

	@Override
	public void getNextVideoURL(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETNEXTVIDEOURL);
	}

	@Override
	public void notifyPlayerFinished(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.PLAYERFINISHED);
	}

	@Override
	public boolean close() {
		if(serverConnection.close()){
			connected = false;
			return true;
		}else{
			connected = true;
			return false;
		}
	}

	@Override
	public boolean setMeAsPlayer() {
		String[] result = this.serverConnection.sendBlockingMessage(MessageType.SETMEASPLAYER);
		return Boolean.parseBoolean(result[0]);
	}

	@Override
	public boolean connect(String ipAddress, int port) {
		this.serverConnection = new YTJBLowLevelServerConnection(this,ipAddress,port,checkInterval,isAndroid);
		if (serverConnection.connect()){
			connected = true;
			if (!defaultNotificationListener.isEmpty())
				this.serverConnection.sendMessage(MessageType.SWITCHDEFAULTNOTIFY);
			if (!gapListNotificationListener.isEmpty())
				this.serverConnection.sendMessage(MessageType.SWITCHGAPLISTNOTIFY);
			if (!pauseResumeNotificationListener.isEmpty())
				this.serverConnection.sendMessage(MessageType.SWITCHPAUSERESUMENOTIFY);
			if (!seekNotificationListener.isEmpty())
				this.serverConnection.sendMessage(MessageType.SWITCHSEEKNOTIFY);
			if (!debugNotificationListener.isEmpty())
				this.serverConnection.sendMessage(MessageType.SWITCHDEBUGNOTIFY);
			return true;
		}else{
			connected = false;
			return false;			
		}
	}

	@Override
	public void getLoadGapListStatus(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.GETLOADGAPLISTSTATUS);
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public ServerAddress udpScanning() throws UDPTimeoutException {
	    // Network-Group
	    String NETWORK_GROUP = "230.0.0.1";
	    // The Port of the Network-Group
	    int NETWORK_GROUP_PORT = 4447;
	   
	    // Coding of the Messages
	    String TEXT_ENCODING = "UTF8";
	    
	    final int TIMEOUT = 15000;
	   
	    final MulticastSocket socket;
	 
	      // Create the Group
	     try {
			socket = new MulticastSocket(NETWORK_GROUP_PORT);
			socket.setTimeToLive(3);
		      final InetAddress socketAddress = InetAddress.getByName(NETWORK_GROUP);
		      socket.joinGroup(socketAddress);

	   
	     
	      byte[] bytes = new byte[65536];
	      DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
	      
	      Thread timeoutThread = new Thread(){
	    	  public void run(){
	    	  try {
				Thread.sleep(TIMEOUT);
				socket.leaveGroup(socketAddress);
				socket.close();
			} catch (Exception e) {
				//timeout was not necessary
			}
	      }};
	      timeoutThread.start();
	      
	      sendUDPRequest(socket,socketAddress,NETWORK_GROUP_PORT);
	      while(true){
		        // Waiting for Messages
		  	    try {
			        socket.receive(packet);
			    } catch (IOException e) {
				     //IO Exception occured while receiving data
				    }
		        if (packet.getLength() == 0)
		        	throw new UDPTimeoutException();
		        String message = new String(packet.getData(),0,packet.getLength(), TEXT_ENCODING);
		        if (!message.equals("REQUEST")){
					socket.leaveGroup(socketAddress);
					socket.close();
			        timeoutThread.interrupt();
			        String[] values = message.split(MessageType.SEPERATOR);
			        return new ServerAddress(values[0],Integer.parseInt(values[1]));
		        }
	      }   
	     }catch (UDPTimeoutException e){
		    	 throw new UDPTimeoutException();
		     } catch (IOException e1) {
				//Error at creating the Socket etc.
		}
	    return null;
	}
	
	/**
	 * Sends an UDPRequest.
	 * @param socket	The Socket, that will send the request.
	 * @param group	The Group, that will receive the Message.
	 * @param port	The Port of the Group, that will receive the Message.
	 * @throws IOException	If an I/O error occurs while sending.
	 */
	private void sendUDPRequest(MulticastSocket socket,InetAddress group, int port) throws IOException{
		byte[] byteMessage;
		byteMessage = "REQUEST".getBytes("UTF8");
		socket.send(new DatagramPacket(byteMessage, byteMessage.length , group ,port));
	}

	@Override
	public String getIPAddress() {
		return this.serverConnection.getIPAddress();
	}

	@Override
	public int getPort() {
		return this.serverConnection.getPort();
	}

	@Override
	public void getCurrentPlayerCount(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.GETCURRENTPLAYERCOUNT);
	}

	@Override
	public void getCurrentClientCount(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETCURRENTCLIENTCOUNT);
	}

	@Override
	public boolean connect(ServerAddress serverAddress) {
		return connect(serverAddress.getIPAddress(),serverAddress.getPort());
	}

	@Override
	public void seekForward(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.SEEKFORWARD);
		
	}

	@Override
	public void seekBackward(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.SEEKBACKWARD);
		
	}

	@Override
	public boolean addToList(String url, boolean toWishList, boolean toBack) {
		String[] result = {"false"};
		if (toWishList && toBack)
			result = this.serverConnection.sendBlockingMessage(MessageType.YOUTUBE, url);
		else if (!toWishList && toBack)
			result = this.serverConnection.sendBlockingMessage(MessageType.GAPYOUTUBE, url);
		else if (toWishList && !toBack)
			result = this.serverConnection.sendBlockingMessage(MessageType.BEGINNINGYOUTUBE, url);
		else if (!toWishList && !toBack)
			result = this.serverConnection.sendBlockingMessage(MessageType.GAPBEGINNINGYOUTUBE, url);	
		return Boolean.parseBoolean(result[0]);
	}

	@Override
	public boolean deleteFromList(Song s) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.DELETEFROMGAPLIST, ""+s.getTrackID())[0]);
	}

	@Override
	public String getCurrentTrackTitle() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTTRACK)[0];
	}

	@Override
	public Song[] getGapList() {
		String[] table = this.serverConnection.sendBlockingMessage(MessageType.GETGAPLIST);
		return this.stringArrayToSongArray(table);
	}

	@Override
	public Song[] getWishList() {
		String[] table = this.serverConnection.sendBlockingMessage(MessageType.GETWISHLIST);
		return this.stringArrayToSongArray(table);
	}

	@Override
	public boolean skip() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.SKIP)[0]);
	}

	@Override
	public boolean pauseResume() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.PAUSERESUME)[0]);
	}

	@Override
	public boolean switchToGapList(String name) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.LOADGAPLIST,name)[0]);
	}

	@Override
	public boolean deleteGapList(String name) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.DELETEGAPLIST,name)[0]);
	}

	@Override
	public String[] getAvailableGapLists() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETAVAILABLEGAPLISTS);
	}

	@Override
	public boolean saveGapList() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.GAPLISTSAVETOFILE)[0]);
	}

	@Override
	public String getCurrentGapListName() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTGAPLISTNAME)[0];
	}

	@Override
	public Song[] getTitleFromGapList(String name) {
		String[] result =  this.serverConnection.sendBlockingMessage(MessageType.GETTITLEFROMGAPLIST);
		Song[] songList = new Song[result.length / 2];
		for (int i = 0; i < result.length; i = i +2){
			songList[i/2] = new Song(-1L, result[i], -1, false, null, result[i + 1]);
		}
		return songList;
	}

	@Override
	public boolean getCurrentPlaybackStatus() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTPLAYBACKSTATUS)[0]);
	}

	@Override
	public boolean setGapListTrackUp(long index) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.GAPLISTTRACKUP,""+index)[0]);
	}

	@Override
	public boolean setGapListTrackDown(long index) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.GAPLISTTRACKDOWN,""+index)[0]);
	}

	@Override
	public String getNextVideoURL() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETNEXTVIDEOURL)[0];
	}

	@Override
	public LoadGapListStatus getLoadGapListStatus() {
		String[] temp = this.serverConnection.sendBlockingMessage(MessageType.GETLOADGAPLISTSTATUS);
		return new LoadGapListStatus(Integer.parseInt(temp[0]),Integer.parseInt(temp[1]));
	}

	@Override
	public boolean notifyPlayerFinished() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.PLAYERFINISHED)[0]);
	}

	@Override
	public int getCurrentPlayerCount() {
		return Integer.parseInt(this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTPLAYERCOUNT)[0]);
	}

	@Override
	public int getCurrentClientCount() {
		return Integer.parseInt(this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTCLIENTCOUNT)[0]);
	}

	@Override
	public boolean seekForward() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.SEEKFORWARD)[0]);
	}

	@Override
	public boolean seekBackward() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.SEEKBACKWARD)[0]);
	}

	@Override
	public void setMeAsPlayer(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.SETMEASPLAYER);
	}
	

	@Override
	public void addDebugNotificationListener(DebugNotificationListener listener) {
		if (!debugNotificationListener.contains(listener)){
			debugNotificationListener.add(listener);
			if (debugNotificationListener.size() == 1 && connected)
				this.serverConnection.sendMessage(MessageType.SWITCHDEBUGNOTIFY);
		}
	}

	@Override
	public void removeDebugNotificationListener(DebugNotificationListener listener) {
		debugNotificationListener.remove(listener);
		if (debugNotificationListener.size() == 0 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHDEBUGNOTIFY);
	}

	@Override
	public void addDefaultNotificationListener(
			DefaultNotificationListener listener) {
		if (!defaultNotificationListener.contains(listener)){
			defaultNotificationListener.add(listener);
			if (defaultNotificationListener.size() == 1 && connected)
				this.serverConnection.sendMessage(MessageType.SWITCHDEFAULTNOTIFY);
		}
		
	}

	@Override
	public void removeDefaultNotificationListener(
			DefaultNotificationListener listener) {
		defaultNotificationListener.remove(listener);
		if (defaultNotificationListener.size() == 0 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHDEFAULTNOTIFY);
	}


	@Override
	public void addGapListNotificationListener(
			GapListNotificationListener listener) {
		if (!gapListNotificationListener.contains(listener)){
		gapListNotificationListener.add(listener);
		if (gapListNotificationListener.size() == 1 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHGAPLISTNOTIFY);
		}
	}

	@Override
	public void removeGapListNotificationListener(
			GapListNotificationListener listener) {
		gapListNotificationListener.remove(listener);
		if (gapListNotificationListener.size() == 0 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHGAPLISTNOTIFY);
	}

	@Override
	public void addPauseResumeNotificationListener(
			PauseResumeNotificationListener listener) {
		if (!pauseResumeNotificationListener.contains(listener)){
		pauseResumeNotificationListener.add(listener);
		if (pauseResumeNotificationListener.size() == 1 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHPAUSERESUMENOTIFY);
		}
	}

	@Override
	public void removePauseResumeNotificationListener(
			PauseResumeNotificationListener listener) {
		pauseResumeNotificationListener.remove(listener);
		if (pauseResumeNotificationListener.size() == 0 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHPAUSERESUMENOTIFY);
	}

	@Override
	public void addSeekNotificationListener(SeekNotificationListener listener) {
		if (!seekNotificationListener.contains(listener)){
		seekNotificationListener.add(listener);
		if (seekNotificationListener.size() == 1 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHSEEKNOTIFY);
		}
	}

	@Override
	public void removeSeekNotificationListener(SeekNotificationListener listener) {
		seekNotificationListener.remove(listener);
		if (seekNotificationListener.size() == 0 && connected)
			this.serverConnection.sendMessage(MessageType.SWITCHSEEKNOTIFY);
	}

	@Override
	public void voteSong(ResponseListener response, Song song) {
		this.serverConnection.sendMessage(response,MessageType.VOTEFORSONG,""+song.getTrackID());
		
	}

	@Override
	public boolean voteSong(Song song) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.VOTEFORSONG, ""+song.getTrackID())[0]);
	}

	@Override
	public void removeVote(ResponseListener response) {
		this.serverConnection.sendMessage(response,MessageType.REMOVEVOTE);
		
	}

	@Override
	public boolean removeVote() {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.REMOVEVOTE)[0]);
	}

	@Override
	public boolean reconnect() {
		if (this.serverConnection != null)
			return this.serverConnection.connect();
		else return false;
	}

	@Override
	public void addSongToOtherList(ResponseListener response, Song song) {
		this.serverConnection.sendMessage(response, MessageType.ADDTOOTHERLIST,""+song.getTrackID());
		
	}

	@Override
	public boolean addSongToOtherList(Song song) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.ADDTOOTHERLIST, ""+song.getTrackID())[0]);
	}

}
