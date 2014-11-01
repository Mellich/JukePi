package network;

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
	public static final int SENDEDFILE = 2;
	
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
	public static final int GAPSENDEDFILE = 6;
	
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
	
	
	
}
