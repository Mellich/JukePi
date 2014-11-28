package connection;

import gui.DisconnectButtonListener;

import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import connection.responseListener.*;
import clientinterface.listener.ResponseListener;
import clientwrapper.YTJBClientWrapper;
import threads.ShowLabelThread;
//import threads.StabilityThread;

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
	 * The Stability-Thread to check the Connection.
	 */
//	private StabilityThread st;
	/**
	 * The Gaplist as a Linked List.
	 */
	private LinkedList<String> gaplist;
	/**
	 * The Wishlist as a Linked List.
	 */
	private LinkedList<String> wishlist;
	
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
	 * The Wrapper for all Messages to the Server.
	 */
	private YTJBClientWrapper wrapper;
	
	/**
	 * The NotifyListener for Notifies from the Server.
	 */
	private NotifyListener notifyListener;
	
	/**
	 * Determines, if the Client is connected to a Server.
	 */
	private boolean connected = false;
	
	/**
	 * The Listener to perform the Action when disconnected from the Server.
	 */
	private DisconnectButtonListener dcListener;
	
	/**
	 * The Constructor for all Collector-Instances.
	 */
	public Collector() {
		gaplist = new LinkedList<String>();
		wishlist = new LinkedList<String>();
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		notifyListener = new NotifyListener(this);
		wrapper.addNotificationListener(notifyListener);
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
	 * Adds the given DisconnectButtonListener as DisconnectListener.
	 * @param dcListener	The Listener to be set.
	 */
	public void addDisconnectListener(DisconnectButtonListener dcListener) {
		this.dcListener = dcListener;
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
			wrapper = new YTJBClientWrapper(IP, iport);
			if (wrapper.connect()) {
				connected = true;
				return true;
			}
			else {
				connected = false;
				return false;
			}
		} catch (NumberFormatException nfe) {
			connected = false;
			return false;
		}
	}
	
	/**
	 * Disconnects from the Server.
	 */
	public void disconnect() {
		if (connected) {
			wrapper.close();
			//TODO Deadlock?
			dcListener.actionPerformed(null);
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
	//	st = new StabilityThread(ip, port, frame, fail, this);
	//	st.start();
	}
	
	/**
	 * Adds the given Link to either the Gaplist or the Wishlist.
	 * @param link	The Link to the YouTube-Video.
	 * @param inFront	Determines, if the Track should be added in front of the List or not.
	 */
	public void addToList(String link, boolean inFront, JLabel fail, JFrame frame) {
		ResponseListener listener = new AddToListListener(fail, new ShowLabelThread(fail, frame));
		wrapper.addToList(listener, link, !gaplistRB.isSelected(), !inFront);
		repaint();
	}
	
	/**
	 * Tries to skip the current track.
	 */
	public void skip(JLabel fail, JFrame frame) {
		SkipListener listener = new SkipListener(fail, new ShowLabelThread(fail, frame));
		wrapper.skip(listener);
	}
	
	/**
	 * Handles the Input, when the PlayButton was pressed.
	 */
	public void playButtonPressed(JLabel fail, JFrame frame) {
		PlayPauseListener listener = new PlayPauseListener(fail, new ShowLabelThread(fail, frame));
		wrapper.pauseResume(listener);
		listener.setMessage(this.getStatus());
	}
	
	/**
	 * Handles the case, when a new Track is played on the Server.
	 */
	public void nextTrack() {
		GetCurrentTrackListener listener = new GetCurrentTrackListener();
		wrapper.getCurrentTrackTitle(listener);
		
		nowPlaying.setText(listener.getName());
		
		if (wishlist.isEmpty())
			if (gaplist.isEmpty())
				nextTrack.setText("No tracks in the lists");
			else
				nextTrack.setText(gaplist.get(0));
		else
			nextTrack.setText(wishlist.get(0));
	}
	
	/**
	 * Sets the currentTrack to the given title.
	 * @param title	The new track's title.
	 */
	public void setTrack(String title) {
		nowPlaying.setText(title);
		
		if (wishlist.isEmpty())
			if (gaplist.isEmpty())
				nextTrack.setText("No tracks in the lists");
			else
				nextTrack.setText(gaplist.get(0));
		else
			nextTrack.setText(wishlist.get(0));
	}
	
	/**
	 * Updates Wishlist and Gaplist.
	 */
	public void updateLists() {
		this.updateGaplist();
		this.updateWishlist();
	}
	
	/**
	 * Updates the Gaplist.
	 */
	public void updateGaplist() {
		UpdateGaplistListener listener = new UpdateGaplistListener();
		wrapper.getGapList(listener);
		this.gaplist = listener.getGaplist();
		
		if (gaplistlabel != null)
			this.gaplistlabel.setText("" + gaplist.size());
		
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
	 * Sets the Gaplist to the given Gaplist and updates the belonging Labels.
	 * @param gaplist	The Gaplist to be saved.
	 */
	public void setGaplist(String[] newGaplist) {
		gaplist.clear();
		for (String s : newGaplist)
			gaplist.add(s);
		
		if (gaplistlabel != null)
			this.gaplistlabel.setText("" + gaplist.size());
		
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
	 * Updates the Wishlist.
	 */
	public void updateWishlist() {
		UpdateWishlistListener listener = new UpdateWishlistListener();
		wrapper.getWishList(listener);
		this.wishlist = listener.getWishlist();
		
		if (wishlistlabel != null)
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
	 * Sets the Wishlist to the given Value of newWishlist and updates the belonging Labels.
	 * @param newWishlist	The new Wishlist.
	 */
	public void setWishlist(String[] newWishlist) {
		wishlist.clear();
		for (String s : newWishlist)
			wishlist.add(s);
		
		if (wishlistlabel != null)
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
	public boolean getStatus() {
		UpdateStatusListener listener = new UpdateStatusListener(play);
		wrapper.getCurrentPlaybackStatus(listener);
		return listener.getPlaying();
	}
	
	/**
	 * Sets the Status to playing/paused.
	 * @param isRunning	The Status of the Server.
	 */
	public void setStatus(boolean isRunning) {
		if (isRunning) {
			this.play.setText("Pause");
			play.setToolTipText("Click here to pause the current Track.");
		}
		else {
			play.setText("Play");
			play.setToolTipText("Click here to resume.");
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
		DeleteTrackListener listener = new DeleteTrackListener();
		wrapper.deleteFromList(listener, index);
		repaint();
		return listener.getDeleted();
	}
	
	/**
	 * Saves the current Gaplist.
	 * @return	True, if the Gaplist was saved, false else.
	 */
	public boolean saveGaplist() {
		SaveGaplistListener listener = new SaveGaplistListener();
		wrapper.saveGapList(listener);
		return listener.getSaved();
	}
	
	/**
	 * Moves the Track at the given index down.
	 * @param index	The index of the Track.
	 */
	public void moveTrackDown(int index) {
		//TODO Add response Listener?
		wrapper.setGapListTrackDown(null, index);
	}
	
	/**
	 * Moves the Track at given index up.
	 * @param index	The index of the Track.
	 */
	public boolean moveTrackUp(int index) {
		MoveTrackUpListener listener = new MoveTrackUpListener();
		wrapper.setGapListTrackUp(listener, index);
		return listener.getMoved();
	}
	
	/**
	 * Updates the Name of the current Gaplist and sets the GaplistNameLabel.
	 */
	public void updateGaplistName() {
		CurrentGaplistListener listener = new CurrentGaplistListener();
		wrapper.getCurrentGapListName(listener);
		if (gaplistName != null) {
			gaplistName.setText("Gaplist - "+listener.getName()+".jb");
			gaplistName.setHorizontalAlignment(JLabel.CENTER);
			gaplistName.setVerticalAlignment(JLabel.CENTER);
		}
	}
	
	/**
	 * Sets the Name of the current Gaplist to the given Value of name.
	 * @param name	The Name of the new Gaplist.
	 */
	public void setGaplistName(String name) {
		if (gaplistName != null) {
			gaplistName.setText("Gaplist - "+name+".jb");
			gaplistName.setHorizontalAlignment(JLabel.CENTER);
			gaplistName.setVerticalAlignment(JLabel.CENTER);
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
		AvailableGaplistListener listener = new AvailableGaplistListener();
		wrapper.getAvailableGapLists(listener);
		if (gaplistCollectionModel != null) {
			gaplistCollectionModel.clear();
			for (String s : listener.getGaplists())
				gaplistCollectionModel.addElement(s);
		}
		repaint();
	}
	
	/**
	 * Sets the values of the gaplistCollectionModel to the given values.
	 * @param newGaplists	All Gaplists as an Array of Strings.
	 */
	public void setGaplists(String[] newGaplists) {
		if (gaplistCollectionModel != null) {
			gaplistCollectionModel.clear();
			for (String s : newGaplists)
				gaplistCollectionModel.addElement(s);
		}
	}
	
	/**
	 * Fills the ContentModel for the Gaplist at the given index.
	 * @param index	The index of the Gaplist.
	 */
	public void fillContentModel(int index) {
		GetTitleListener listener = new GetTitleListener();
		wrapper.getTitleFromGapList(listener, gaplistCollectionModel.get(index));
		for (String s : listener.getContent())
			contentModel.addElement(s);
		contentLabel.setText("Content - " + gaplistCollectionModel.get(index) + ".jb");
		contentLabel.setHorizontalAlignment(JLabel.CENTER);
		contentLabel.setVerticalAlignment(JLabel.CENTER);
		repaint();
	}
	
	/**
	 * Loads the given Gaplist.
	 * @param gaplist	The Gaplist's Name.
	 */
	public boolean loadGaplist(String gaplist) {
		GaplistSwitchListener listener = new GaplistSwitchListener();
		wrapper.switchToGapList(listener, gaplist);
		return listener.getSwitched();
	}
	
	/**
	 * Creates a new Gaplist with the given Name.
	 * @param text	The Name of the new Gaplist.
	 */
	public boolean createNewList(String text) {
		GaplistSwitchListener listener1 = new GaplistSwitchListener();
		wrapper.switchToGapList(listener1, text);
		if (listener1.getSwitched()) {
			SaveGaplistListener listener2 = new SaveGaplistListener();
			wrapper.saveGapList(listener2);
			return listener2.getSaved();
		}
		else
			return false;
	}
	
	/**
	 * Removes the Given Gaplist.
	 * @param text	The Name of the Gaplist to be deleted.
	 */
	public boolean removeGaplist(String text) {
		//TODO Add response Listener
		wrapper.deleteGapList(null, text);
		return true;
	}
}
