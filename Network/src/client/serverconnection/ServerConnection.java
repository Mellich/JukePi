package client.serverconnection;

import client.ServerAddress;
import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.listener.ResponseListener;
import client.serverconnection.impl.LoadGapListStatus;

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
	public void addDefaultNotificationListener(DefaultNotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeDefaultNotificationListener(DefaultNotificationListener listener);
	
	/**Adds a notification listener, that will be executed, when the server sends a notification
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addDebugNotificationListener(DefaultNotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeDebugNotificationListener(DefaultNotificationListener listener);	
	
	/**Adds a notification listener, that will be executed, when the server sends a notification
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addGapListNotificationListener(DefaultNotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeGapListNotificationListener(DefaultNotificationListener listener);
	
	/**Adds a notification listener, that will be executed, when the server sends a notification
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addPauseResumeNotificationListener(DefaultNotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removePauseResumeNotificationListener(DefaultNotificationListener listener);
	
	/**Adds a notification listener, that will be executed, when the server sends a notification
	 * 
	 * @param listener the notification listener that should be added
	 */
	public void addSeekNotificationListener(DefaultNotificationListener listener);
	
	/**Removes a notification listener, so that he no longer will be executed by a notification from the server
	 * 
	 * @param listener the listener that should be removed
	 */
	public void removeSeekNotificationListener(DefaultNotificationListener listener);
	
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
	public boolean addToList(String url,boolean toWishList,boolean toBack);
	
	/**Deletes the video on position "index" in the gap list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index the index of the track in the gap list, that should be removed
	 */
	public void deleteFromList(ResponseListener response,int index);
	public boolean deleteFromList(int index);
	
	/**gets the title of the track, that is currently played by the server
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentTrackTitle(ResponseListener response);
	public String getCurrentTrackTitle();
	
	/**Gets the title of all videos in the gap list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getGapList(ResponseListener response);
	public String[] getGapList();
	
	/**gets the title of all tracks in the wish list
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getWishList(ResponseListener response);
	public String[] getWishList();
	
	/**skips the current track.
	 * ResponseListener receives true if track could be skipped, false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void skip(ResponseListener response);
	public boolean skip();
	
	/**pauses or resumes the current track.
	 * ResponseListener receives true if track could be paused or stopped, false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void pauseResume(ResponseListener response);
	public boolean pauseResume();
	
	/**switch to the gap list with the given name. Creates a new gap list, if name is not used, yet. 
	 * ResponseListener receives true if gap list was switched. false otherwise.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name the name of the new gap list
	 */
	public void switchToGapList(ResponseListener response,String name);
	public boolean switchToGapList(String name);
	
	/**deletes a gap list from the hard drive of the server. Only gap lists, that are not currently open can be deleted!
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name the name of the gap list
	 */
	public void deleteGapList(ResponseListener response,String name);
	public boolean deleteGapList(String name);
	
	/**gets the names of all available gap list of the server.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getAvailableGapLists(ResponseListener response);
	public String[] getAvailableGapLists();
	
	/**saves the currently active gap list to hard drive
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void saveGapList(ResponseListener response);
	public boolean saveGapList();
	
	/**gets the name of the gap list, that is currently in use.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentGapListName(ResponseListener response);
	public String getCurrentGapListName();
	
	/**gets the title of the tracks saved in a gap list.
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name name of the gap list
	 */
	public void getTitleFromGapList(ResponseListener response,String name);
	public String[] getTitleFromGapList(String name);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentPlaybackStatus(ResponseListener response);
	public boolean getCurrentPlaybackStatus();
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index
	 */
	public void setGapListTrackUp(ResponseListener response,int index);
	public boolean setGapListTrackUp(int index);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param index
	 */
	public void setGapListTrackDown(ResponseListener response,int index);
	public boolean setGapListTrackDown(int index);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getNextVideoURL(ResponseListener response);
	public String getNextVideoURL();
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getLoadGapListStatus(ResponseListener response);
	public LoadGapListStatus getLoadGapListStatus();
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void notifyPlayerFinished(ResponseListener response);
	public boolean notifyPlayerFinished();
	
	/**
	 * 
	 */
	public void setMeAsPlayer(ResponseListener response);
	public boolean setMeAsPlayer();
	
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
	public ServerAddress udpScanning() throws UDPTimeoutException;
	
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
	public int getCurrentPlayerCount();
	
	public void getCurrentClientCount(ResponseListener response);
	public int getCurrentClientCount();
	
	public void seekForward(ResponseListener response);
	public boolean seekForward();
	
	public void seekBackward(ResponseListener response);
	public boolean seekBackward();
	
	
}
