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
import client.listener.NotificationListener;
import client.listener.ResponseListener;
import client.serverconnection.ServerConnectionNotifier;
import client.serverconnection.ServerConnection;
import client.serverconnection.functionality.LowLevelServerConnection;
import client.serverconnection.functionality.YTJBLowLevelServerConnection;

public class YTJBServerConnection implements ServerConnection, ServerConnectionNotifier {
	
	private List<NotificationListener> notificationListener;
	private List<DebugNotificationListener> debugNotificationListener;
	private LowLevelServerConnection serverConnection;
	private boolean connected = false;
	private int checkIntervall = 0;
	
	public YTJBServerConnection() {
		this(0);
	}
	
	public YTJBServerConnection(int checkIntervall) {
		notificationListener = new ArrayList<NotificationListener>();
		debugNotificationListener = new ArrayList<DebugNotificationListener>();
		connected = false;
		this.checkIntervall = checkIntervall;
	}

	@Override
	public void onNotify(int notifyType,String[] args) {
		switch(notifyType){
		case MessageType.PAUSERESUMENOTIFY: for(NotificationListener l: notificationListener) l.onPauseResumeNotify(Boolean.parseBoolean(args[0]));
			break;
		case MessageType.NEXTTRACKNOTIFY:for(NotificationListener l: notificationListener) l.onNextTrackNotify(args[0],args[1],Boolean.parseBoolean(args[2]));
			break;
		case MessageType.GAPLISTUPDATEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListUpdatedNotify(args);
			break;
		case MessageType.WISHLISTUPDATEDNOTIFY:for(NotificationListener l: notificationListener) l.onWishListUpdatedNotify(args);
			break;
		case MessageType.GAPLISTCOUNTCHANGEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListCountChangedNotify(args);
			break;
		case MessageType.GAPLISTCHANGEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListChangedNotify(args[0]);
			break;
		case MessageType.CLIENTCOUNTCHANGEDNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onClientCountChangedNotify(Integer.parseInt(args[0]));
			break;
		case MessageType.PLAYERCOUNTCHANGEDNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onPlayerCountChangedNotify(Integer.parseInt(args[0]));
			break;
		case MessageType.DEBUGOUTPUTNOTIFY: for(DebugNotificationListener l: debugNotificationListener) l.onNewOutput(args[0]);
		break;
		case MessageType.SEEKNOTIFY: for(NotificationListener l: notificationListener) l.onSeekNotify(Boolean.parseBoolean(args[0]));
			break;
		case MessageType.DISCONNECT: for(NotificationListener l: notificationListener) l.onDisconnect();
										connected = false;
		}

	}

	@Override
	public void addNotificationListener(NotificationListener listener) {
		this.notificationListener.add(listener);

	}

	@Override
	public void removeNotificationListener(NotificationListener listener) {
		this.notificationListener.remove(listener);

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
	public void deleteFromList(ResponseListener response, int index) {
		this.serverConnection.sendMessage(response, MessageType.DELETEFROMGAPLIST, ""+index);
	}

	@Override
	public void getCurrentTrackTitle(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETCURRENTTRACK);
		
	}

	@Override
	public void getGapList(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETGAPLIST);
	}

	@Override
	public void getWishList(ResponseListener response) {
		this.serverConnection.sendMessage(response, MessageType.GETWISHLIST);
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
	public void setGapListTrackUp(ResponseListener response, int index) {
		this.serverConnection.sendMessage(response, MessageType.GAPLISTTRACKUP,""+index);
	}

	@Override
	public void setGapListTrackDown(ResponseListener response, int index) {
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
	public void setMeAsPlayer() {
		this.serverConnection.sendMessage(MessageType.SETMEASPLAYER);
	}

	@Override
	public boolean connect(String ipAddress, int port) {
		this.serverConnection = new YTJBLowLevelServerConnection(this,ipAddress,port,checkIntervall);
		if (serverConnection.connect()){
			connected = true;
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
	public ServerAddress waitForUDPConnect() {
	    // Netzwerk-Gruppe
	    String NETWORK_GROUP = "230.0.0.1";
	    // Netzwerk-Gruppen Port
	    int NETWORK_GROUP_PORT = 4447;
	   
	    // Nachrichten-Codierung
	    String TEXT_ENCODING = "UTF8";
	   
	    MulticastSocket socket;
	 
	      // Gruppe anlegen
	      try {
			socket = new MulticastSocket(NETWORK_GROUP_PORT);
		      InetAddress socketAddress = InetAddress.getByName(NETWORK_GROUP);

		      socket.joinGroup(socketAddress);
	   
	     
	      byte[] bytes = new byte[65536];
	      DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
	     
	      while(true){
	        // Warten auf Nachricht
	  	    try {
		        socket.receive(packet);
		        String message = new String(packet.getData(),0,packet.getLength(), TEXT_ENCODING);
		        socket.leaveGroup(socketAddress);
		        socket.close();
		        String[] values = message.split(MessageType.SEPERATOR);
		        return new ServerAddress(values[0],Integer.parseInt(values[1]));
		    } catch (IOException e) {
			      e.printStackTrace();
			    }
	      }   
		} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
		}
	    return null;
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
	public void addDebugNotificationListener(DebugNotificationListener listener) {
		debugNotificationListener.add(listener);
	}

	@Override
	public void removeDebugNotificationListener(DebugNotificationListener listener) {
		debugNotificationListener.remove(listener);
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
	public boolean deleteFromList(int index) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.DELETEFROMGAPLIST, ""+index)[0]);
	}

	@Override
	public String getCurrentTrackTitle() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETCURRENTTRACK)[0];
	}

	@Override
	public String[] getGapList() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETGAPLIST);
	}

	@Override
	public String[] getWishList() {
		return this.serverConnection.sendBlockingMessage(MessageType.GETWISHLIST);
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
	public boolean setGapListTrackUp(int index) {
		return Boolean.parseBoolean(this.serverConnection.sendBlockingMessage(MessageType.GAPLISTTRACKUP,""+index)[0]);
	}

	@Override
	public boolean setGapListTrackDown(int index) {
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

}
