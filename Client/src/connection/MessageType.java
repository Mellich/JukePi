package connection;

/**includes integers that specify message types
 * 
 * @author Mellich
 *
 */
public class MessageType {
	/**
	 * a link to a youtube video
	 */
	public static final int YOUTUBE = 1;
	
	/**
	 * a path to a sended file
	 */
	public static final int SENTFILE = 2;
	
	/**
	 * command to skip the current track
	 */
	public static final int SKIP = 3;
	
	/**
	 * command to pause or resume the current track
	 */
	public static final int PAUSERESUME = 4;
	
	/**
	 * a link to a youtube video that should be inserted to the gaplist
	 */
	public static final int GAPYOUTUBE = 5;
	
	/**
	 * a path to a sended file that should be inserted to the gaplist
	 */
	public static final int GAPSENTFILE = 6;
	
	/**
	 * save the current gap list into a file
	 */
	public static final int GAPLISTSAVETOFILE = 7;
	
	/**
	 * send the gaplist to the client
	 */
	public static final int GETGAPLIST = 8;
	
	/**
	 * send the wish list to the client
	 */
	public static final int GETWISHLIST = 9;
	
	/**
	 * delete one music track from the gap list
	 */
	public static final int DELETEFROMGAPLIST = 10;
	
	/**
	 * asks the server if it is ready to receive data
	 */
	public static final int ISREADY = 11;
	
	/**
	 * return the current track that is played
	 */
	public static final int GETCURRENTTRACK = 12;
	
	/**
	 * next track will be played
	 */
	public static final int NEXTTRACKNOTIFY = 13;
	
	/**
	 * a track was added to the wish or the gap list
	 */
	public static final int LISTSUPDATEDNOTIFY = 14;
	
	/**
	 * gets the current status of the playback
	 */
	public static final int GETCURRENTPLAYBACKSTATUS = 16;
	
	/**
	 * ready to receive the file
	 */
	public static final int READYFORRECEIVENOTIFY = 17;
	
	/**
	 * the response of the server for a request
	 */
	public static final int RESPONSENOTIFY = 18;
	
	/**
	 * declare the client as notify listener
	 */
	public static final int DECLAREMEASNOTIFY = 20;
	
	/**
	 * the shit you sent is not implemented, stupid bitch!
	 */
	public static final int NOTIMPLEMENTEDCOMMANDNOTIFY = 19;
	
	/**
	 * add the sent file at the beginning of the list
	 */
	public static final int GAPBEGINNINGSENTFILE = 21;

	/**
	 * add the youtube link at the beginning of the list
	 */
	public static final int GAPBEGINNINGYOUTUBE = 22;
	
	/**
	 * add the sent file at the beginning of the list
	 */
	public static final int BEGINNINGSENTFILE = 23;
	
	/**
	 * add the youtube link at the beginning of the list
	 */
	public static final int BEGINNINGYOUTUBE = 24;
	
	/**
	 * the playback status has changed
	 */
	public static final int PAUSERESUMENOTIFY = 25;
	
	/**
	 * Returns all saved Gaplists
	 */
	public static final int GETAVAILABLEGAPLISTS = 26;
	
	/**
	 * Load a specific Gaplist
	 */
	public static final int LOADGAPLIST = 27;
	
	/**
	 * Moves a track a position up in the current Gaplist
	 */
	public static final int GAPLISTTRACKUP = 28;
	
	/**
	 * Moves a track a position down in the current Gaplist
	 */
	public static final int GAPLISTTRACKDOWN = 29;
	
	/**
	 * Returns the Name of the current Gaplist
	 */
	public static final int GETCURRENTGAPLISTNAME = 30;
	
	/**
	 * Returns the titles from the Gaplist specified by the following parameter
	 */
	public static final int GETTITLEFROMGAPLIST = 31;
	
	/**
	 * the seerator for the arguments of a command
	 */
	public static final String SEPERATOR = ";\t;";
	
}
