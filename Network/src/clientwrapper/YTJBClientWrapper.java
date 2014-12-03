package clientwrapper;

import java.util.ArrayList;
import java.util.List;

import messages.MessageType;
import clientinterface.ServerConnection;
import clientinterface.YTJBServerConnection;
import clientinterface.listener.NotificationListener;
import clientinterface.listener.ResponseListener;

public class YTJBClientWrapper implements ClientWrapper, ClientNotifyWrapper {
	
	private List<NotificationListener> notificationListener;
	private ServerConnection serverConnection;
	private boolean connected = false;
	
	public YTJBClientWrapper(String ipAddress,int port) {
		this(ipAddress,port,0);
	}
	
	public YTJBClientWrapper(String ipAddress,int port, int checkIntervall) {
		serverConnection = new YTJBServerConnection(this,ipAddress,port,checkIntervall);
		notificationListener = new ArrayList<NotificationListener>();
		connected = false;
	}

	@Override
	public void onNotify(int notifyType,String[] args) {
		switch(notifyType){
		case MessageType.PAUSERESUMENOTIFY: for(NotificationListener l: notificationListener) l.onPauseResumeNotify(Boolean.parseBoolean(args[0]));
			break;
		case MessageType.NEXTTRACKNOTIFY:for(NotificationListener l: notificationListener) l.onNextTrackNotify(args[0],args[1]);
			break;
		case MessageType.GAPLISTUPDATEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListUpdatedNotify(args);
			break;
		case MessageType.WISHLISTUPDATEDNOTIFY:for(NotificationListener l: notificationListener) l.onWishListUpdatedNotify(args);
			break;
		case MessageType.GAPLISTCOUNTCHANGEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListCountChangedNotify(args);
			break;
		case MessageType.GAPLISTCHANGEDNOTIFY:for(NotificationListener l: notificationListener) l.onGapListChangedNotify(args[0]);
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
	public boolean connect() {
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

}
