package clientwrapper;

import clientinterface.listener.NotificationListener;
import clientinterface.listener.ResponseListener;

/**
 * 
 * @author mellich
 *
 *Interface for client side communication with the YTJBServer.
 *Its recommended to use the implementation YTJBClientWrapper to interact with the server
 */
public interface ClientWrapper {
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
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getWishList(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void skip(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void pauseResume(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name
	 */
	public void switchToGapList(ResponseListener response,String name);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name
	 */
	public void deleteGapList(ResponseListener response,String name);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getAvailableGapLists(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void saveGapList(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 */
	public void getCurrentGapListName(ResponseListener response);
	
	/**
	 * 
	 * @param response the response listener, that will be executed, when the server responses the command
	 * @param name
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
	
	/**
	 * 
	 * @return
	 */
	public String[] waitForUDPConnect();
	
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
