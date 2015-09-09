package client.serverconnection;

import messages.Permission;
import client.ServerAddress;
import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.listener.PauseResumeNotificationListener;
import client.listener.ResponseListener;
import client.listener.SeekNotificationListener;
import client.serverconnection.impl.LoadGapListStatus;

/**
 * Interface for client side communication with the YTJBServer.
 * Its recommended to use the implementation YTJBClientWrapper to interact with the server 
 * @author Mellich
 * @version 1.1
 */
public interface ServerConnection {
	
	/**
	 * Tries to reconnect to the Server.
	 * @return	{@code true}, if the Connection was reestablished, {@code false} else.
	 * @throws PermissionDeniedException thrown if one ore more permissions were denied by the server
	 * @since 1.0
	 */
	public boolean reconnect()  throws PermissionDeniedException;
	
	/**
	 * Adds a notification listener, that will be executed, when the Server sends a 
	 * Notification.
	 * @param listener 	The Notification listener that will be added.
	 * @since 1.0
	 */
	public void addDefaultNotificationListener(DefaultNotificationListener listener);
	
	/**
	 * Removes a Notification listener, so that it won't be executed by a 
	 * notification from the server anymore.
	 * 
	 * @param listener 	The listener, that will be removed.
	 * @since 1.0
	 */
	public void removeDefaultNotificationListener(DefaultNotificationListener listener);	
	
	/**
	 * Adds a Gaplist-Notification listener, that will be executed, when the server sends a 
	 * Notification.
	 * 
	 * @param listener 	The Notification listener that will be added.
	 * @since 1.0
	 */
	public void addGapListNotificationListener(GapListNotificationListener listener);
	
	/**
	 * Removes a Gaplist-Notification listener, so that it won't be executed by a 
	 * Notification from the server anymore.
	 * 
	 * @param listener 	The listener that will be removed.
	 * @since 1.0
	 */
	public void removeGapListNotificationListener(GapListNotificationListener listener);
	
	/**
	 * Adds a PauseResume-Notification listener, that will be executed, when the server sends a 
	 * Notification.
	 * 
	 * @param listener 	The Notification listener that will be added.
	 * @since 1.0
	 */
	public void addPauseResumeNotificationListener(PauseResumeNotificationListener listener);
	
	/**
	 * Removes a PauseResume-Notification listener, so that it won't be executed by a 
	 * Notification from the server anymore.
	 * 
	 * @param listener 	The listener that will be removed.
	 * @since 1.0
	 */
	public void removePauseResumeNotificationListener(PauseResumeNotificationListener listener);
	
	/**
	 * Adds a Seek-Notification listener, that will be executed, when the server sends a 
	 * Notification.
	 * 
	 * @param listener 	The Notification listener that will be added.
	 * @since 1.0
	 */
	public void addSeekNotificationListener(SeekNotificationListener listener);
	
	/**
	 * Removes a Seek-Notification listener, so that it won't be executed by a 
	 * Notification from the server anymore.
	 * 
	 * @param listener 	The listener that will be removed.
	 * @since 1.0
	 */
	public void removeSeekNotificationListener(SeekNotificationListener listener);
	
	/**
	 * Adds a Debug-Notification listener, that will be executed, when the server sends a 
	 * Notification regarding to debug issues.
	 * 
	 * @param listener 	The Notification listener that will be added.
	 * @since 1.0
	 */
	public void addDebugNotificationListener(DebugNotificationListener listener);
	
	/**
	 * Removes a Debug-Notification listener, so that it won't be executed by a 
	 * Notification from the server anymore.
	 * 
	 * @param listener 	The listener that will be removed.
	 * @since 1.0
	 */
	public void removeDebugNotificationListener(DebugNotificationListener listener);
	
	/**
	 * Adds a Music-Link to a list on the server.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param url 	The URL of the Music-Link, that will be added.
	 * @param toWishList 	{@code true}, if the Music-Link should be added to the Wishlist, 
	 * {@code false}, if it should be added to the Gaplist.
	 * @param toBack 	if {@code true}, adds the Music-Link to the end of the list, else to 
	 * the beginning.
	 * @since 1.0
	 */
	public void addToList(ResponseListener response,String url,boolean toWishList,boolean toBack);
	
	/**
	 * Adds a Music-Link to a List on the Server.
	 * @param url	The URL of the Music-Link, that will be added.
	 * @param toWishList	{@code true}, if the Music-Link should be added to the Wishlist, 
	 * {@code false}, if it should be added to the Gaplist.
	 * @param toBack	if {@code true}, adds the Music-Link to the end of the list, else to
	 * the beginning.
	 * @return	{@code true}, if the Link was added, {@code false} else.
	 * @since 1.0
	 */
	public boolean addToList(String url,boolean toWishList,boolean toBack);
	
	/**
	 * Deletes the given Song from the Gaplist.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param song 	The Song in the gap list, that will be removed.
	 * @since 1.0
	 */
	public void deleteFromList(ResponseListener response,Song song);
	
	/**
	 * Deletes the given Song from the Gaplist.
	 * @param song	The Song, that will be deleted.
	 * @return	{@code true}, if the Song was deleted, {@code false} else.
	 * @since 1.0
	 */
	public boolean deleteFromList(Song song);
	
	/**
	 * Gets the title of the track, that is currently played by the server.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getCurrentTrackTitle(ResponseListener response);
	
	/**
	 * Returns the Title of the Current Track.
	 * @return	The Title of the Current Track.
	 * @since 1.0
	 */
	public String getCurrentTrackTitle();
	
	/**
	 * Gets all Songs in the Gaplist.
	 * 
	 * @return The Gaplist as an Array of {@link Song}s.
	 * @since 1.0
	 */
	public Song[] getGapList();
	
	/**
	 * Gets all Songs in the Wishlist.
	 * 
	 * @return The Wishlist as an Array of Songs.
	 * @since 1.0
	 */
	public Song[] getWishList();
	
	/**
	 * Skips the current track. <br>
	 * ResponseListener receives {@code true} if the track could be skipped, 
	 * {@code false} otherwise.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void skip(ResponseListener response);
	
	/**
	 * Skips the current track.
	 * @return	{@code true}, if the Track was skipped, {@code false} else.
	 * @since 1.0
	 */
	public boolean skip();
	
	/**
	 * Pauses or resumes the current track. <br>
	 * ResponseListener receives {@code true} if the track could be paused or resumed, 
	 * {@code false} otherwise.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void pauseResume(ResponseListener response);
	
	/**
	 * Pauses or resumes the current Track.
	 * @return	{@code true}, if the Track was paused or resumed, {@code false} otherwise.
	 * @since 1.0
	 */
	public boolean pauseResume();
	
	/**
	 * Switch to the Gaplist with the given name. Creates a new Gaplist, if the given name is 
	 * not yet used. <br> 
	 * ResponseListener receives {@code true} if the Gaplist was switched, {@code false} 
	 * otherwise.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param name 	The name of the Gaplist.
	 * @since 1.0
	 */
	public void switchToGapList(ResponseListener response,String name);
	
	/**
	 * Switch to the Gaplist with the given name. Creates a new Gaplist, if the given name
	 * is not yet used.
	 * @param name	The name of the Gaplist.
	 * @return	{@code true}, if the Gaplist was switched, {@code false} otherwise.
	 * @since 1.0
	 */
	public boolean switchToGapList(String name);
	
	/**
	 * Deletes the Gaplist with the given name from the hard drive of the server. Only 
	 * Gaplists, that are not currently open can be deleted!
	 * 
	 * @param response 	the response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param name 	The name of the Gaplist.
	 * @since 1.0
	 */
	public void deleteGapList(ResponseListener response,String name);
	
	/**
	 * Deletes the Gaplist with the given Name from the hard drive of the Server. Only 
	 * Gaplists, that are not currently open can be deleted!
	 * @param name	The name of the Gaplist that will be deleted.
	 * @return	{@code true}, if the Gaplist was deleted, {@code false} else.
	 * @since 1.0
	 */
	public boolean deleteGapList(String name);
	
	/**
	 * Gets the names of all available Gaplists from the server.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getAvailableGapLists(ResponseListener response);
	
	/**
	 * Gets the names of all available Gaplists from the Server.
	 * @return	The Names of all Gaplists from the Server as an Array of Strings.
	 * @since 1.0
	 */
	public String[] getAvailableGapLists();
	
	/**
	 * Saves the currently active Gaplist to the hard drive.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void saveGapList(ResponseListener response);
	
	/**
	 * Saves the currently active Gaplist to the hard drive.
	 * @return	{@code true}, if the Gaplist was saved to the hard drive, {@code false} else.
	 * @since 1.0
	 */
	public boolean saveGapList();
	
	/**
	 * Gets the name of the Gaplist, that is currently in use.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getCurrentGapListName(ResponseListener response);
	
	/**
	 * Gets the name of the Gaplist, that is currently in use.
	 * @return	The Name of the Gaplist, that is currently in use.
	 * @since 1.0
	 */
	public String getCurrentGapListName();
	
	/**
	 * Gets the title of the tracks saved in a Gaplist.
	 * 
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param name 	The name of the Gaplist.
	 * @since 1.0
	 */
	public void getTitleFromGapList(ResponseListener response,String name);
	
	/**
	 * Gets the title of the Tracks saved in a Gaplist.
	 * @param name	The name of the Gaplist.
	 * @return	The Tracks saved in the Gaplist.
	 * @since 1.0
	 */
	public Song[] getTitleFromGapList(String name);
	
	/**
	 * Gets the current Playback status of the Server.
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getCurrentPlaybackStatus(ResponseListener response);
	
	/**
	 * Gets the current Playback status of the Server.
	 * @return	{@code true}, if the Track is playing at the moment, {@code false} else.
	 * @since 1.0
	 */
	public boolean getCurrentPlaybackStatus();
	
	/**
	 * Sets the Track given by the trackID one position upwards.
	 * @param response 	The response listener, that will be executed, when the server responses 
	 * to the command
	 * @param trackID	The ID of the Track, that will be moved.
	 * @since 1.0
	 */
	public void setGapListTrackUp(ResponseListener response,long trackID);
	
	/**
	 * Sets the Track with the given trackID one position upwards.
	 * @param trackID	The ID of the Track, that will be moved.
	 * @return	{@code true}, if the Track was moved, {@code false} else.
	 * @since 1.0
	 */
	public boolean setGapListTrackUp(long trackID);
	
	/**
	 * Sets the Track with the given trackID one position downwards.
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @param trackID	The trackID of the Track, that will be moved.
	 * @since 1.0
	 */
	public void setGapListTrackDown(ResponseListener response,long trackID);
	
	/**
	 * Sets the Track with the given trackID one position downwards.
	 * @param trackID	The ID of the Track, that will be moved.
	 * @return	{@code true}, if the Track was moved, {@code false} else.
	 * @since 1.0
	 */
	public boolean setGapListTrackDown(long trackID);
	
	/**
	 * Gets the URL of the next Video.
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getNextVideoURL(ResponseListener response);
	
	/**
	 * Gets the URL of the next Video.
	 * @return	The URL of the next Video as a String.
	 * @since 1.0
	 */
	public String getNextVideoURL();
	
	/**
	 * Gets the LoadStatus of the Gaplist.
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void getLoadGapListStatus(ResponseListener response);
	
	/**
	 * Gets the LoadStatus of the Gaplist.
	 * @return	The LoadStatus of the Gaplist.
	 * @see LoadGapListStatus
	 * @since 1.0
	 */
	public LoadGapListStatus getLoadGapListStatus();
	
	/**
	 * The Method, that will be called, when the Track is finished on the Player.
	 * @param response 	The response listener, that will be executed, when the server 
	 * responses to the command.
	 * @since 1.0
	 */
	public void notifyPlayerFinished(ResponseListener response);
	
	/**
	 * The Method, that will be called, when the Track is finished on the Player.
	 * @return	{@code true}, if the Message to block the Player was sent, {@code false} else.
	 * @since 1.0
	 */
	public boolean notifyPlayerFinished();
	
	/**
	 * Sets the implemented Connection as a Player.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void setMeAsPlayer(ResponseListener response);
	
	/**
	 * Sets the implemented Connection as a Player.
	 * @return	{@code true}, if the Connection was set as a Player, {@code false} else.
	 * @since 1.0
	 */
	public boolean setMeAsPlayer();
	
	/**
	 * Tries to connect to the given IP and Port.
	 * @param ipAddress	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @return	{@code true}, if the Connection was established, {@code false} else.
	 * @since 1.0
	 * @throws PermissionDeniedExeption throws this exception, if a permission was denied by the server
	 */
	public boolean connect(String ipAddress, int port) throws PermissionDeniedException;
	
	/**
	 * Tries to connect to the given {@link ServerAddress}.
	 * @param serverAddress	The Address of the Server.
	 * @return	{@code true}, if the Connection was established, {@code false} else.
	 * @since 1.0
	 * @throws PermissionDeniedExeption throws this exception, if a permission was denied by the server
	 */
	public boolean connect(ServerAddress serverAddress) throws PermissionDeniedException;
	
	/**
	 * Returns, if a Connection is established.
	 * @return	{@code true}, if a Connection is established, {@code false} else.
	 * @since 1.0
	 */
	public boolean isConnected();
	
	/**
	 * Closes the current Connection.
	 * @return	{@code true}, if the Connection was closed {@code false} else.
	 * @since 1.0
	 */
	public boolean close();
	
	/**
	 * Blocks the current thread until a YTJBServer in the network is found. <br>
	 * Connect to the returned ServerAddress with {@link #connect(ServerAddress)} to 
	 * establish a connection.
	 * 
	 * @return 	The ServerAddress of the found server.
	 * @throws UDPTimeoutException 	if no Server was found in time.
	 * @since 1.0
	 */
	public ServerAddress udpScanning() throws UDPTimeoutException;
	
	/**
	 * Returns the IP of the Server, the Client/Player is connected to.
	 * @return	The IP of the Server.
	 * @since 1.0
	 */
	public String getIPAddress();
	
	/**
	 * Returns the Port of the Server, the Client/Player is connected to.
	 * @return	The Port of the Server.
	 * @since 1.0
	 */
	public int getPort();
	
	/**
	 * Gets the current amount of connected Players.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void getCurrentPlayerCount(ResponseListener response);
	
	/**
	 * Gets the current amount of connected Players.
	 * @return	The amount of connected Players.
	 * @since 1.0
	 */
	public int getCurrentPlayerCount();
	
	/**
	 * Gets the current amount of connected Clients.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void getCurrentClientCount(ResponseListener response);
	
	/**
	 * Gets the current amount of connected Clients.
	 * @return	The amount of connected Clients.
	 * @since 1.0
	 */
	public int getCurrentClientCount();
	
	/**
	 * Seeks the current Track forwards.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void seekForward(ResponseListener response);
	
	/**
	 * Seeks the current Track forwards.
	 * @return	{@code true}, if the Track was sought forwards, {@code false} else.
	 * @since 1.0
	 */
	public boolean seekForward();
	
	/**
	 * Seeks the current Track backwards.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void seekBackward(ResponseListener response);
	
	/**
	 * Seeks the current Track backwards.
	 * @return	{@code true}, if the Track was sought backwards, {@code false} else.
	 * @since 1.0
	 */
	public boolean seekBackward();
	
	/**
	 * Votes for the given Song.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @param song	The Song, the Vote will be added to.
	 * @since 1.0
	 */
	public void voteSong(ResponseListener response,Song song);
	
	/**
	 * Votes for the given Song
	 * @param song	The Song, the Vote will be added to.
	 * @return	{@code true}, if the Vote was added, {@code false} else.
	 * @since 1.0
	 */
	public boolean voteSong(Song song);
	
	/**
	 * Removes the Client's Vote.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @since 1.0
	 */
	public void removeVote(ResponseListener response);
	
	/**
	 * Removes the Client's Vote.
	 * @return	{@code true}, if the Vote was removed, {@code false} else.
	 * @since 1.0
	 */
	public boolean removeVote();
	
	/**
	 * Adds the given Song to the other List.
	 * @param response	The response listener, that will be executed, when the server
	 * responses to the command.
	 * @param song	The Song to be moved.
	 * @since 1.1
	 */
	public void addSongToOtherList(ResponseListener response,Song song);
	
	/**
	 * Adds the given Song to the other List.
	 * @param song	The Song to be moved.
	 * @return	{@code true}, if the Song was moved, {@code false} else.
	 */
	public boolean addSongToOtherList(Song song);
	
	/**
	 * adds a permission to the client
	 * @param p permission that shall be added
	 * @param passphrase the pass phrase to authorize the permission addition
	 * @return true, if permission was added
	 * @throws PermissionDeniedExeption throws this exception, if a permission was denied by the server
	 */
	public boolean addPermission(Permission p, String passphrase) throws PermissionDeniedException;
	
	/**
	 * returns a list of the granted permissions for the client
	 * @return the granted permissions
	 */
	public Permission[] getPermissions();
	
}
