package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import threads.NotifierReaderThread;
import threads.StabilityThread;

/**
 * A Class, that will handle the Communication to the Server.
 * @author Haeldeus
 *
 */
public class Collector {

	/**
	 * The Label that shows the amount of tracks in the Tracklist.
	 */
	private JLabel gaplistlabel;
	
	/**
	 * The Label that shows the amount of tracks in the Wishlist.
	 */
	private JLabel wishlistlabel;
	
	/**
	 * The RadioButton, that indicates, whether the track should be added to 
	 * the Gaplist or the Wishlist.
	 */
	private JRadioButton gaplistRB;
	
	/**
	 * The Socket, that receives all notifier from the server.
	 */
	private Socket notifier;
	
	/**
	 * The Socket that sends Commands to the Server and receives the answers to these 
	 * commands.
	 */
	private Socket sender;
	
	/**
	 * The BufferedWriter for the Notifier-Socket.
	 */
	private BufferedWriter notifierWriter;	//TODO: Why do you store that?
	
	/**
	 * The BufferedWriter for the Sender-Socket.
	 */
	private BufferedWriter senderWriter;
	/**
	 * The BufferedReader for the Notifier-Socket.
	 */
	private BufferedReader notifierReader;
	
	/**
	 * The BufferedReader for the Sender-Socket.
	 */
	private BufferedReader senderReader;
	
	/**
	 * The Play-Button.
	 */
	private JButton play;
	
	/**
	 * The Label, that shows the current Track.
	 */
	private JLabel nowPlaying;
	
	/**
	 * The Label that shows the next Track.
	 */
	private JLabel nextTrack;
	
	/**
	 * The Label that displays the Name of the current Gaplist.
	 */
	private JLabel gaplistName;
	
	/**
	 * The Label on top of the Content of the Gaplist. Will be changed every time a
	 * new Gaplist is shown to display the name of the shown Gaplist.
	 */
	private JLabel contentLabel;
	
	/**
	 * The Sender to communicate with the Server.
	 */
	private Sender s;
	
	/**
	 * The Stability-Thread to check the Connection.
	 */
	private StabilityThread st;
	/**
	 * The Gaplist as a Linked List.
	 */
	private LinkedList<String> gaplist;
	/**
	 * The Wishlist as a Linked List.
	 */
	private LinkedList<String> wishlist;
	
	/**
	 * The NotifierReaderThread, that will listen to notifies from the Server
	 */
	private NotifierReaderThread nrt;
	
	/**
	 * The Gaplist as a ListModel.
	 */
	private DefaultListModel<String> gaplistModel;
	
	/**
	 * The Wishlist as a ListModel.
	 */
	private DefaultListModel<String> wishlistModel;
	/**
	 * The Collection of the Gaplists as a ListModel.
	 */
	private DefaultListModel<String> gaplistCollectionModel;
	
	/**
	 * The Content as a ListModel.
	 */
	private DefaultListModel<String> contentModel;
	
	/**
	 * The Second Frame of the Application.
	 */
	private JFrame secondFrame;
	
	/**
	 * The Constructor for all Collector-Instances.
	 */
	public Collector() {
		s = new Sender();
		gaplist = new LinkedList<String>();
		wishlist = new LinkedList<String>();
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
	}
	
	/**
	 * Adds the given Label as GaplistLabel.
	 * @param label	The Label to be set as GaplistLabel.
	 */
	public void addGaplistLabel(JLabel label) {
		this.gaplistlabel = label;
	}
	
	/**
	 * Adds the given Label as WishlistLabel.
	 * @param label	The Label to be set as WishlistLabel.
	 */
	public void addWishlistLabel(JLabel label) {
		this.wishlistlabel = label;
	}
	
	/**
	 * Adds the given RadioButton as GaplistRadioButton.
	 * @param gaplistRB	The RadioButton to be set as GaplistRadioButton.
	 */
	public void addGaplistRB(JRadioButton gaplistRB) {
		this.gaplistRB = gaplistRB;
	}
	
	/**
	 * Adds the given Label as NowPlayingLabel.
	 * @param nowPlaying	The Label to be set as NowPlayingLabel.
	 */
	public void addNowPlayingLabel(JLabel nowPlaying) {
		this.nowPlaying = nowPlaying;
	}
	
	/**
	 * Adds the given Label as NextTrackLabel.
	 * @param nextTrack	The Label to be set as NextTrackLabel.
	 */
	public void addNextTrackLabel(JLabel nextTrack) {
		this.nextTrack = nextTrack;
	}
	
	/**
	 * Adds the given Button as PlayButton
	 * @param play	The Button to be set as the PlayButton.
	 */
	public void addPlayButton(JButton play) {
		this.play = play;
	}
	
	/**
	 * Adds the given ListModel as GaplistModel.
	 * @param gaplistModel	The ListModel to be set as GaplistModel.
	 */
	public void addGaplistModel(DefaultListModel<String> gaplistModel) {
		this.gaplistModel = gaplistModel;
	}
	
	/**
	 * Adds the given ListModel as WishlistModel
	 * @param wishlistModel	The ListModel to be set as WishlistModel.
	 */
	public void addWishlistModel(DefaultListModel<String> wishlistModel) {
		this.wishlistModel = wishlistModel;
	}
	
	/**
	 * Adds the given ListModel as GaplistCollectionModel.
	 * @param gaplistCollectionModel	The ListModel to be set as GaplistCollectionModel.
	 */
	public void addGaplistCollectionModel(DefaultListModel<String> gaplistCollectionModel) {
		this.gaplistCollectionModel = gaplistCollectionModel;
	}
	
	/**
	 * Adds the given ListModel as ContentModel.
	 * @param contentModel	The ListModel to be set as ContentModel.
	 */
	public void addContentModel(DefaultListModel<String> contentModel) {
		this.contentModel = contentModel;
	}
	
	/**
	 * Adds the given Frame as SecondFrame.
	 * @param frame	The second Frame to be set.
	 */
	public void addSecondFrame(JFrame frame) {
		this.secondFrame = frame;
	}
	
	/**
	 * Adds the given Label as GaplistName.
	 * @param gaplistName	The Label to be set as GaplistName.
	 */
	public void addGaplistNameLabel(JLabel gaplistName) {
		this.gaplistName = gaplistName;
	}
	
	/**
	 * Adds the given Label as ContentLabel.
	 * @param contentLabel	The Label to be set as ContentLabel.
	 */
	public void addContentLabel(JLabel contentLabel) {
		this.contentLabel = contentLabel;
	}
	
	/**
	 * Tries to connect to the given IP and Port.
	 * @param IP	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @return	True, if the Connection was established, false else.
	 */
	public boolean connect(String IP, String port) {
		try {
			int iport = Integer.parseInt(port);
			notifier = new Socket(IP, iport);
			sender = new Socket(IP, iport);
			notifierWriter = new BufferedWriter(new OutputStreamWriter(notifier.getOutputStream()));
			senderWriter = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
			notifierReader = new BufferedReader(new InputStreamReader(notifier.getInputStream()));
			senderReader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
			nrt = new NotifierReaderThread(notifierReader, this);
			nrt.start();
			notifierWriter.write("20");
			notifierWriter.newLine();
			notifierWriter.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	/**
	 * Disconnects from the Server.
	 */
	public void disconnect() {
		try {
			nrt.interrupt();
			notifier.close();
			sender.close();
			st.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Starts the Stable-Test, to maintain the Connection.
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @param frame	The Frame of the Application.
	 * @param fail	The Label, that will display a Message, when the Connection is lost.
	 */
	public void startStableTest(String ip, int port, JFrame frame, JLabel fail) {
		st = new StabilityThread(ip, port, frame, fail, this);
	//	st.start();
	}
	
	/**
	 * Adds the given Link to either the Gaplist or the Wishlist.
	 * @param link	The Link to the YouTube-Video.
	 * @param inFront	Determines, if the Track should be added in front of the List or not.
	 * @return	True, if the Track was added, false else.
	 */
	public boolean addToList(String link, boolean inFront) {
		if (gaplistRB.isSelected())
			if (!inFront)
				s.sendMessage(MessageType.GAPYOUTUBE, link, senderWriter);
			else 
				s.sendMessage(MessageType.GAPBEGINNINGYOUTUBE, link, senderWriter);
		else
			if (!inFront)
				s.sendMessage(MessageType.YOUTUBE, link, senderWriter);
			else
				s.sendMessage(MessageType.BEGINNINGYOUTUBE, link, senderWriter);
		
		repaint();
		
		try {
			String answer = senderReader.readLine();
			String pos[] = answer.split(MessageType.SEPERATOR);
			if (pos[1].equals("false")) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Tries to skip the current track.
	 * @return	True, if the Track was skipped, false else.
	 */
	public boolean skip() {
		s.sendMessage(MessageType.SKIP, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			repaint();
			if (answerparts[1].equals("true"))
				return true;
			else
				return false;
		} catch (IOException e) {
			repaint();
			return false;
		}
	}
	
	/**
	 * Handles the Input, when the PlayButton was pressed.
	 * @return True, if Track was paused/resumed, false else.
	 */
	public boolean playButtonPressed() {
		s.sendMessage(MessageType.PAUSERESUME, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true"))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Handles the case, when a new Track is played on the Server.
	 */
	public void nextTrack() {
		s.sendMessage(MessageType.GETCURRENTTRACK, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			nowPlaying.setText(answerparts[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (wishlist.isEmpty())
			if (gaplist.isEmpty())
				nextTrack.setText("No tracks in the lists");
			else
				nextTrack.setText(gaplist.get(0));
		else
			nextTrack.setText(wishlist.get(0));
	}
	
	/**
	 * Updates all Lists, whenever it is necessary.
	 */
	public void updateLists() {
		s.sendMessage(MessageType.GETGAPLIST, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			gaplist.clear();
			for (int i = 1; i < answerparts.length; i++) {
				gaplist.add(answerparts[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gaplistlabel.setText(""+gaplist.size());
		
		s.sendMessage(MessageType.GETWISHLIST, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String answerparts[] = answer.split(MessageType.SEPERATOR);
			wishlist.clear();
			for (int i = 1; i < answerparts.length; i++) {
				wishlist.add(answerparts[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		wishlistlabel.setText(""+wishlist.size());
		
		if (nextTrack != null)
			if (wishlist.isEmpty())
				if (gaplist.isEmpty())
					nextTrack.setText("No tracks in the lists");
				else
					nextTrack.setText(gaplist.get(0));
			else
				nextTrack.setText(wishlist.get(0));
		repaint();
	}
	
	/**
	 * Updates the Status of the Server (Track playing or Track paused).
	 * @return True, if a Track is playing, false else.
	 */
	public boolean updateStatus() {
		s.sendMessage(MessageType.GETCURRENTPLAYBACKSTATUS, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true")) {
				play.setText("Pause");
				play.setToolTipText("Click here to pause the current track");
				return true;
			}
			else {
				play.setText("Play");
				play.setToolTipText("Click here to play the current track");
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Fills the Gaplist- and WishlistModel.
	 */
	public void fillModels() {
		gaplistModel.clear();
		wishlistModel.clear();
		for (String i : gaplist) {
			gaplistModel.addElement(i);
		}
		for (String i : wishlist) {
			wishlistModel.addElement(i);
		}
		repaint();
	}
	
	/**
	 * Deletes the Track at the given index.
	 * @param index	The index of the Track to be deleted.
	 * @return	True, if the Track was deleted, false else.
	 */
	public boolean deleteTrack(int index) {
		s.sendMessage(MessageType.DELETEFROMGAPLIST, ""+index, senderWriter);
		try {
			String[] answer = senderReader.readLine().split(MessageType.SEPERATOR);
			
			if (answer[1].equals("true")) {
				repaint();
				return true;
			}
			else {
				repaint();
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			repaint();
			return false;
		}
	}
	
	/**
	 * Saves the current Gaplist.
	 */
	public void saveGaplist() {
		s.sendMessage(MessageType.GAPLISTSAVETOFILE, "", senderWriter);
		try {
			senderReader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the Track at the given index down.
	 * @param index	The index of the Track.
	 */
	public void moveTrackDown(int index) {
		s.sendMessage(MessageType.GAPLISTTRACKDOWN, ""+index, senderWriter);
		try {
			senderReader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Moves the Track at given index up.
	 * @param index	The index of the Track.
	 */
	public void moveTrackUp(int index) {
		s.sendMessage(MessageType.GAPLISTTRACKUP, ""+index, senderWriter);
		try {
			senderReader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Updates the Name of the current Gaplist and sets the GaplistNameLabel.
	 */
	public void updateGaplistName() {
		s.sendMessage(MessageType.GETCURRENTGAPLISTNAME, "", senderWriter);
		try {
			String[] answerparts = senderReader.readLine().split(MessageType.SEPERATOR);
			if (gaplistName != null) {
				gaplistName.setText("Gaplist - "+answerparts[1]);
				gaplistName.setHorizontalAlignment(JLabel.CENTER);
				gaplistName.setVerticalAlignment(JLabel.CENTER);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Repaints the Second Frame.
	 */
	public void repaint() {
		if (secondFrame != null) {
			secondFrame.repaint();
			secondFrame.getContentPane().repaint();
		}
	}
	
	/**
	 * Fills the GaplistModel.
	 */
	public void fillGaplistModel() {
		s.sendMessage(MessageType.GETAVAILABLEGAPLISTS, "", senderWriter);
		try {
			String[] answerparts = senderReader.readLine().split(MessageType.SEPERATOR);
			gaplistCollectionModel.clear();
			for (int i = 1; i < answerparts.length; i++) {
				gaplistCollectionModel.addElement(answerparts[i]);
			}
		} catch (Exception e) {
			// Disconnected or window for gaplists wasn't open yet
		}
		repaint();
	}
	
	/**
	 * Fills the ContentModel for the Gaplist at the given index.
	 * @param index	The index of the Gaplist.
	 * @return	True, if the ContentModel was filled, false else.
	 */
	public boolean fillContentModel(int index) {
		s.sendMessage(MessageType.GETTITLEFROMGAPLIST, gaplistCollectionModel.get(index), senderWriter);
		try {
			String[] answerparts = senderReader.readLine().split(MessageType.SEPERATOR);
			contentModel.clear();
			for (int i = 1; i < answerparts.length; i++) {
				contentModel.addElement(answerparts[i]);
			}
			contentLabel.setText("Content - " + gaplistCollectionModel.get(index) + ".jb");
			contentLabel.setHorizontalAlignment(JLabel.CENTER);
			contentLabel.setVerticalAlignment(JLabel.CENTER);
		} catch (Exception e) {
			return false;
		}
		repaint();
		return true;
	}
	
	/**
	 * Loads the given Gaplist.
	 * @param gaplist	The Gaplist's Name.
	 * @return	True, if the Gaplist was loaded, false else.
	 */
	public boolean loadGaplist(String gaplist) {
		s.sendMessage(MessageType.LOADGAPLIST, gaplist, senderWriter);
		try {senderReader.readLine();} catch(Exception e) {return false;}
		return true;
	}
	
	/**
	 * Creates a new Gaplist with the given Name.
	 * @param text	The Name of the new Gaplist.
	 * @return	True, if the Gaplist was created, false else.
	 */
	public boolean createNewList(String text) {
		s.sendMessage(MessageType.LOADGAPLIST, text, senderWriter);
		try {senderReader.readLine();} catch (Exception e) {return false;}
		s.sendMessage(MessageType.GAPLISTSAVETOFILE, "", senderWriter);
		try {senderReader.readLine();} catch (Exception e) {return false;}
		return true;
	}
	
	/**
	 * Removes the Given Gaplist.
	 * @param text	The Name of the Gaplist to be deleted.
	 * @return	True, if the Gaplist was deleted, false else.
	 */
	public boolean removeGaplist(String text) {
		s.sendMessage(MessageType.DELETEGAPLIST, text, senderWriter);
		try {senderReader.readLine();} catch (Exception e) {return false;}
		return true;
	}
}
