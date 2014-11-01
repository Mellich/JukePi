package connection;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * A Class to send the commands to the Server.
 * @author Haeldeus
 *
 */
public class Sender {
	
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

	public Sender() {
	}
	
	public boolean sendMessage(int messageType, String message) {
	
		
		try {
			Socket socket = new Socket("192.168.178.34",12345);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			
			switch (messageType) {
			case YOUTUBE: writer.write("1;\t;"+message);break;
			}
			socket.close(); //Just for performance reasons atm
		}
		catch (Exception e) {
				e.printStackTrace();
		}
		return true;
	}
}