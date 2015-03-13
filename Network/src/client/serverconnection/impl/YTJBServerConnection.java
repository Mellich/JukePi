package client.serverconnection.impl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.ArrayList;
import java.util.List;

import messages.MessageType;
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

public class YTJBServerConnection implements ServerConnection, ServerConnectionNotifier {
	
	private List<SeekNotificationListener> seekNotificationListener;
	private List<GapListNotificationListener> gapListNotificationListener;
	private List<PauseResumeNotificationListener> pauseResumeNotificationListener;
	private List<DefaultNotificationListener> defaultNotificationListener;
	private List<DebugNotificationListener> debugNotificationListener;
	private LowLevelServerConnection serverConnection;
	private boolean connected = false;
	private int checkIntervall = 0;
	
	public YTJBServerConnection() {
		this(0);
	}
	
	public YTJBServerConnection(int checkIntervall) {
		defaultNotificationListener = new ArrayList<DefaultNotificationListener>();
		debugNotificationListener = new ArrayList<DebugNotificationListener>();
		seekNotificationListener = new ArrayList<SeekNotificationListener>();
		gapListNotificationListener = new ArrayList<GapListNotificationListener>();
		pauseResumeNotificationListener = new ArrayList<PauseResumeNotificationListener>();
		connected = false;
		this.checkIntervall = checkIntervall;
	}
	
	private Song[] stringArrayToSongArray(String[] table){
		long ownVote = Long.parseLong(table[0]);
		int i = 0;
		Song[] result = new Song[(table.length - 1) / 3];
		while (i < result.length){
			long trackID = Long.parseLong(table[3*i + 1]);
			if (ownVote == trackID)
				result[i] = new Song(trackID,table[3*i + 2],Integer.parseInt(table[3*i + 3]),true);
			else result[i] = new Song(trackID,table[3*i + 2],Integer.parseInt(table[3*i + 3]),false);
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
		this.serverConnection = new YTJBLowLevelServerConnection(this,ipAddress,port,checkIntervall);
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
	    // Netzwerk-Gruppe
	    String NETWORK_GROUP = "230.0.0.1";
	    // Netzwerk-Gruppen Port
	    int NETWORK_GROUP_PORT = 4447;
	   
	    // Nachrichten-Codierung
	    String TEXT_ENCODING = "UTF8";
	    
	    int TIMEOUT = 6000;
	   
	    MulticastSocket socket;
	 
	      // Gruppe anlegen
	     try {
			socket = new MulticastSocket(NETWORK_GROUP_PORT);
		      InetAddress socketAddress = InetAddress.getByName(NETWORK_GROUP);
		      socket.joinGroup(socketAddress);

	   
	     
	      byte[] bytes = new byte[65536];
	      DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
	      
	      Thread timeoutThread = new Thread(() -> {
	    	  try {
				Thread.sleep(TIMEOUT);
				socket.leaveGroup(socketAddress);
				socket.close();
			} catch (Exception e) {
				//timeout was not necessary
			}
	      });
	      timeoutThread.start();
	      
	      sendUDPRequest(socket,socketAddress,NETWORK_GROUP_PORT);
	      while(true){
		        // Warten auf Nachricht
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
				//fehler beim erstellen des sockets etc
		}
	    return null;
	}
	
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
	public String[] getTitleFromGapList(String name) {
		return this.serverConnection.sendBlockingMessage(MessageType.GETTITLEFROMGAPLIST);
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

}
