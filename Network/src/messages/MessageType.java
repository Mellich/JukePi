package messages;

/**
 * Includes integers that specify message types
 * 
 * @author Mellich
 * @version 1.0
 */
public class MessageType {
	
	public static final int DISCONNECT = 0;
	/**
	 * A link to a video.
	 */
	public static final int YOUTUBE = 1;
	
	/**
	 * A path to a sent file.
	 */
	public static final int SENTFILE = 2;
	
	/**
	 * Command to skip the current track.
	 */
	public static final int SKIP = 3;
	
	/**
	 * Command to pause or resume the current track.
	 */
	public static final int PAUSERESUME = 4;
	
	/**
	 * A link to a video that should be added to the Gaplist.
	 */
	public static final int GAPYOUTUBE = 5;
	
	/**
	 * A path to a sent file that should be added to the Gaplist.
	 */
	public static final int GAPSENTFILE = 6;
	
	/**
	 * Saves the current Gaplist to a file.
	 */
	public static final int GAPLISTSAVETOFILE = 7;
	
	/**
	 * Sends the Gaplist to the Client.
	 */
	public static final int GETGAPLIST = 8;
	
	/**
	 * Send the Wishlist to the Client.
	 */
	public static final int GETWISHLIST = 9;
	
	/**
	 * Deletes one music track from the Gaplist.
	 */
	public static final int DELETEFROMGAPLIST = 10;
	
	/**
	 * Asks the Server if it is ready to receive data.
	 */
	public static final int ISREADY = 11;
	
	/**
	 * Returns the current track that is played.
	 */
	public static final int GETCURRENTTRACK = 12;
	
	/**
	 * Next track will be played.
	 */
	public static final int NEXTTRACKNOTIFY = 13;
	
	/**
	 * A track was added to the Wish- or Gaplist.
	 */
	public static final int GAPLISTUPDATEDNOTIFY = 14;
	
	/**
	 * The amount of Gaplists has changed.
	 */
	public static final int GAPLISTCOUNTCHANGEDNOTIFY = 15;
	
	/**
	 * Gets the current status of the playback.
	 */
	public static final int GETCURRENTPLAYBACKSTATUS = 16;
	
	/**
	 * Ready to receive the file.
	 */
	public static final int READYFORRECEIVENOTIFY = 17;
	
	/**
	 * The response of the Server to a request.
	 */
	public static final int RESPONSENOTIFY = 18;
	
	/**
	 * Declares the Client as notify listener.
	 */
	public static final int REGISTERCLIENT = 20;
	
	/**
	 * Will be sent, if the sent MessageType isn't implemented.
	 */
	public static final int NOTIMPLEMENTEDCOMMANDNOTIFY = 19;
	
	/**
	 * Adds the sent file at the beginning of the list.
	 */
	public static final int GAPBEGINNINGSENTFILE = 21;

	/**
	 * Adds the link at the beginning of the list.
	 */
	public static final int GAPBEGINNINGYOUTUBE = 22;
	
	/**
	 * Adds the sent file at the beginning of the list.
	 */
	public static final int BEGINNINGSENTFILE = 23;
	
	/**
	 * Adds the link at the beginning of the list.
	 */
	public static final int BEGINNINGYOUTUBE = 24;
	
	/**
	 * The playback status has changed.
	 */
	public static final int PAUSERESUMENOTIFY = 25;
	
	/**
	 * Gets the available Gaplists on the Server.
	 */
	public static final int GETAVAILABLEGAPLISTS = 26;
	
	/**
	 * Loads a specified Gaplist.
	 */
	public static final int LOADGAPLIST = 27;
	
	/**
	 * Moves a Track in the Gaplist up.
	 */
	public static final int GAPLISTTRACKUP = 28;
	
	/**
	 * Moves a Track in the Gaplist down.
	 */
	public static final int GAPLISTTRACKDOWN = 29;
	
	/**
	 * Gets the name of the current Gaplist.
	 */
	public static final int GETCURRENTGAPLISTNAME = 30;
	
	/**
	 * Gets all titles of Songs in the given Gaplist.
	 */
	public static final int GETTITLEFROMGAPLIST = 31;
	
	/**
	 * Sets the Client as Player.
	 */
	public static final int SETMEASPLAYER = 32;
	
	/**
	 * Gets the URL of the next Video.
	 */
	public static final int GETNEXTVIDEOURL = 33;
	
	/**
	 * Tells the Server, that the Song is over.
	 */
	public static final int PLAYERFINISHED = 34;

	/**
	 * Deletes the specified Gaplist.
	 */
	public static final int DELETEGAPLIST = 35;
	
	/**
	 * Notifies the Clients, that the current Gaplist has changed.
	 */
	public static final int GAPLISTCHANGEDNOTIFY = 36;
	
	/**
	 * Gets the LoadStatus of the Gaplist.
	 */
	public static final int GETLOADGAPLISTSTATUS = 37;
	
	/**
	 * Notifies the Clients, that the Wishlist was updated.
	 */
	public static final int WISHLISTUPDATEDNOTIFY = 38;
	
	/**
	 * Gets the current amount of connected Clients.
	 */
	public static final int GETCURRENTCLIENTCOUNT = 39;
	
	/**
	 * Gets the current amount of connected Players.
	 */
	public static final int GETCURRENTPLAYERCOUNT = 40;
	
	/**
	 * Notify, that the amount of Clients has changed.
	 */
	public static final int CLIENTCOUNTCHANGEDNOTIFY = 41;
	
	/**
	 * Notify, that the amount of Players has changed.
	 */
	public static final int PLAYERCOUNTCHANGEDNOTIFY = 42;
	
	/**
	 * Notify, that a debugOutput has to be printed.
	 */
	public static final int DEBUGOUTPUTNOTIFY = 43;
	
	/**
	 * Notify, that the song was sought.
	 */
	public static final int SEEKNOTIFY = 44;
	
	/**
	 * Notify to seek the Track forward.
	 */
	public static final int SEEKFORWARD = 45;
	
	/**
	 * Notify to seek the Track backward.
	 */
	public static final int SEEKBACKWARD = 46;	
	
	/**
	 * A Notify to switch to debugNotificationHandler.
	 */
	public static final int SWITCHDEBUGNOTIFY = 47;
	
	/**
	 * A Notify to switch to gaplistNotificationHandler.
	 */
	public static final int SWITCHGAPLISTNOTIFY = 48;
	
	/**
	 * A Notify to switch to pauseResumeNotificationHandler.
	 */
	public static final int SWITCHPAUSERESUMENOTIFY = 49;
	
	/**
	 * A Notify to switch to seekNotificationHandler.
	 */
	public static final int SWITCHSEEKNOTIFY = 50;
	
	/**
	 * A Notify to switch to defaultNotificationHandler.
	 */
	public static final int SWITCHDEFAULTNOTIFY = 51;
	
	/**
	 * Votes for the specified Song.
	 */
	public static final int VOTEFORSONG = 52;
	
	/**
	 * Removes the Client's Vote from the Song.
	 */
	public static final int REMOVEVOTE = 53;
	
	/**
	 * The Seperator for the arguments of a command.
	 */
	public static final String SEPERATOR = ";\t;";
	
}
