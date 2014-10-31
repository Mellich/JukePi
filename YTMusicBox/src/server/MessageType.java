package server;

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
}
