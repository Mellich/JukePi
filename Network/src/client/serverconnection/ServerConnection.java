package client.serverconnection;

import client.ServerAddress;
import client.listener.DebugNotificationListener;
import client.listener.NotificationListener;
import client.listener.ResponseListener;

/**
 * 
 * @author mellich
 *
 *Interface for client side communication with the YTJBServer.
 *Its recommended to use the implementation YTJBClientWrapper to interact with the server
 */
public interface ServerConnection {
	/**Adds a notification listener, that will be executed, when the server sends a notification
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addNotificationListener(NotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeNotificationListener(NotificationListener listener);
	
	/**Adds a debug notification listener, that will be executed, when the server sends a notifications regarding to debug issues
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addDebugNotificationListener(DebugNotificationListener listener);
	
	/**Removes a debug notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeDebugNotificationListener(DebugNotificationListener listener);
	
	/**
	 * Adds a Youtube-Video to a list on the server
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param url the URL of the Youtube-Video that should be added
	 * @param toWishList set to true, when the video should be added to the wish list, false to the gap list
	 * @param toBack if true, adds the video on the end of the list, else to the beginning
	 */
	public void addToList(ResponseListener response,String url,boolean toWishList,boolean toBack);
	
	/**Deletes the video on position "index" in the gap list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index the index of the track in the gap list, that should be removed
	 */
	public void deleteFromList(ResponseListener response,int index);
	
	/**gets the title of the track, that is currently played by the server
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentTrackTitle(ResponseListener response);
	
	/**Gets the title of all videos in the gap list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getGapList(ResponseListener response);
	
	/**gets the title of all tracks in the wish list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getWishList(ResponseListener response);
	
	/**skips the current track.
	 * ResponseListener receives true if track could be skipped, false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void skip(ResponseListener response);
	
	/**pauses or resumes the current track.
	 * ResponseListener receives true if track could be paused or stopped, false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void pauseResume(ResponseListener response);
	
	/**switch to the gap list with the given name. Creates a new gap list, if name is not used, yet. 
	 * ResponseListener receives true if gap list was switched. false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name the name of the new gap list
	 */
	public void switchToGapList(ResponseListener response,String name);
	
	/**deletes a gap list from the hard drive of the server. Only gap lists, that are not currently open can be deleted!
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name the name of the gap list
	 */
	public void deleteGapList(ResponseListener response,String name);
	
	/**gets the names of all available gap list of the server.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getAvailableGapLists(ResponseListener response);
	
	/**saves the currently active gap list to hard drive
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void saveGapList(ResponseListener response);
	
	/**gets the name of the gap list, that is currently in use.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentGapListName(ResponseListener response);
	
	/**gets the title of the tracks saved in a gap list.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name name of the gap list
	 */
	public void getTitleFromGapList(ResponseListener response,String name);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentPlaybackStatus(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index
	 */
	public void setGapListTrackUp(ResponseListener response,int index);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index
	 */
	public void setGapListTrackDown(ResponseListener response,int index);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getNextVideoURL(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getLoadGapListStatus(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void notifyPlayerFinished(ResponseListener response);
	
	/**
	 * 
	 */
	public void setMeAsPlayer();
	
	/**
	 * 
	 * @param ipAddress
	 * @param port
	 * @return
	 */
	public boolean connect(String ipAddress, int port);
	
	public boolean connect(ServerAddress serverAddress);
	
	/**
	 * 
	 * @return
	 */
	public boolean isConnected();
	
	/**
	 * 
	 * @return
	 */
	public boolean close();
	
	/**blocks the current thread until a YTJBServer in the network is found.
	 * connect to the returned ServerAddress with the connect()-method to establish a connection.
	 * 
	 * @return the ServerAddress of the found server
	 */
	public ServerAddress waitForUDPConnect();
	
	/**
	 * 
	 * @return
	 */
	public String getIPAddress();
	
	/**
	 * 
	 * @return
	 */
	public int getPort();
	
	public void getCurrentPlayerCount(ResponseListener response);
	
	public void getCurrentClientCount(ResponseListener response);
}
