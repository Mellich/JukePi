package connection;

import gui.DisconnectButtonListener;
import gui.EditTrackListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import util.ShowLabelThread;
import clientinterface.listener.NotificationListener;
import clientwrapper.YTJBClientWrapper;

/**
 * A Class that will implement the NotificationListener and connects to the ClientWrapper to 
 * send Messages to the Server.
 * @author Haeldeus
 *
 */
public class Collector implements NotificationListener{
	
	/**
	 * Time in ms when the wrapper should check the connectivity of the server if no response 
	 * arrived.
	*/
	private static final int CONNECTIONCHECKINTERVALL = 15000;
	
	/**
	 * The wrapper, that will send the Messages.
	 */
	private YTJBClientWrapper wrapper;
	
	/**
	 * Determines, if a Track is played at the moment.
	 */
	private boolean isPlaying;
	
	/**
	 * The PlayButton, that appears on the screen.
	 */
	private JButton playButton;
	
	/**
	 * The Label, that shows the current Gaplist.
	 */
	private JLabel gaplistName;
	
	/**
	 * The Label, that shows the current Track.
	 */
	private JLabel nowPlaying;
	
	/**
	 * The Label, that shows the size of the Gaplist.
	 */
	private JLabel gaplistLabel;
	
	/**
	 * The Label that shows the size of the Wishlist.
	 */
	private JLabel wishlistLabel;
	
	/**
	 * The Label, that shows the next Track.
	 */
	private JLabel nextTrackLabel;
	
	/**
	 * The Label, that shows the Name of the displayed content's Gaplist.
	 */
	private JLabel contentLabel;
	
	/**
	 * The EditTrackListener, which is needed to get the right Informations at the EditTrack Window.
	 */
	private EditTrackListener etl;
	
	/**
	 * The Gaplist as an Array of Strings.
	 */
	private volatile String[] gaplist;
	
	/**
	 * The Wishlist as an Array of Strings.
	 */
	private volatile String[] wishlist;
	
	/**
	 * All Gaplists on the Server as an Array of Strings.
	 */
	private volatile String[] gaplists;
	
	/**
	 * The second Frame (TrackEdit- or GaplistEditWindow).
	 */
	private volatile JFrame secondFrame;
	
	/**
	 * The Listener for the Disconnect Button. Will be "pressed", when the Client loses the
	 * connection to the Server.
	 */
	private DisconnectButtonListener dcListener;
	
	/**
	 * The ListModel, that displays each Track of the Gaplist.
	 */
	private volatile DefaultListModel<String> gaplistModel;
	
	/**
	 * The ListModel, that displays each Track of the Wishlist.
	 */
	private volatile DefaultListModel<String> wishlistModel;
	
	/**
	 * The ListModel, that displays each Gaplist saved on the Server.
	 */
	private volatile DefaultListModel<String> gaplistCollectionModel;
	
	/**
	 * The ListModel, that displays each Track of the given Gaplist.
	 */
	private volatile DefaultListModel<String> contentModel;
	
	/**
	 * The Constructor for the Collector.
	 */
	public Collector() {
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		gaplistCollectionModel = new DefaultListModel<String>();
		contentModel = new DefaultListModel<String>();
		wrapper = new YTJBClientWrapper(CONNECTIONCHECKINTERVALL);
	}
	
	/**
	 * Adds the given Button as PlayButton.
	 * @param playButton	The new PlayButton.
	 */
	public void addPlayButton(JButton playButton) {
		this.playButton = playButton;
	}
	
	/**
	 * Adds the given Label as GaplistLabel.
	 * @param gaplistLabel	The new GaplistLabel.
	 */
	public void addGaplistLabel(JLabel gaplistLabel) {
		this.gaplistLabel = gaplistLabel;	
	}
	
	/**
	 * Adds the given Label as WishlistLabel.
	 * @param wishlistLabel	The new WishlistLabel.
	 */
	public void addWishlistLabel(JLabel wishlistLabel) {
		this.wishlistLabel = wishlistLabel;
	}
	
	/**
	 * Adds the given Label as GaplistNameLabel.
	 * @param gaplistName	The new GaplistNameLabel.
	 */
	public void addGaplistNameLabel(JLabel gaplistName) {
		this.gaplistName = gaplistName;
	}
	
	/**
	 * Adds the given Label as NowPlayingLabel.
	 * @param nowPlaying	The new NowPlayingLabel.
	 */
	public void addNowPlayingLabel(JLabel nowPlaying) {
		this.nowPlaying = nowPlaying;
	}
	
	/**
	 * Adds the given Label as NextTrackLabel.
	 * @param nextTrackLabel	The new NextTrackLabel.
	 */
	public void addNextTrackLabel(JLabel nextTrackLabel) {
		this.nextTrackLabel = nextTrackLabel;
	}
	
	/**
	 * Adds the given Listener as DisconnectButtonListener.
	 * @param dcListener 	The new DisconnectButtonListener.
	 */
	public void addDisconnectListener(DisconnectButtonListener dcListener) {
		this.dcListener = dcListener;
	}
	
	/**
	 * Adds the given ListModel as GaplistModel.
	 * @param gaplistModel	The new GaplistModel.
	 */
	public void addGaplistModel(DefaultListModel<String> gaplistModel) {
		this.gaplistModel = gaplistModel;
	}
	
	/**
	 * Adds the given ListModel as WishlistModel.
	 * @param wishlistModel	The new WishlistModel.
	 */
	public void addWishlistModel(DefaultListModel<String> wishlistModel) {
		this.wishlistModel = wishlistModel;
	}
	
	/**
	 * Adds the given ListModel as GaplistCollectionModel.
	 * @param gaplistCollectionModel	The new GaplistCollectionModel.
	 */
	public void addGaplistCollectionModel(DefaultListModel<String> gaplistCollectionModel) {
		this.gaplistCollectionModel = gaplistCollectionModel;
	}
	
	/**
	 * Adds the given ListModel as ContentModel.
	 * @param contentModel	The new ContentModel.
	 */
	public void addContentModel(DefaultListModel<String> contentModel) {
		this.contentModel = contentModel;
	}
	
	/**
	 * Adds the given Label as ContentLabel.
	 * @param contentLabel	The new ContentLabel.
	 */
	public void addContentLabel(JLabel contentLabel) {
		this.contentLabel = contentLabel;
	}
	
	/**
	 * Adds the given Frame as SecondFrame.
	 * @param frame	The new SecondFrame.
	 */
	public void addSecondFrame(JFrame frame) {
		this.secondFrame = frame;
	}
	
	/**
	 * Adds the given Listener as etl.
	 * @param etl
	 */
	public void addEditTrackListener(EditTrackListener etl) {
		this.etl = etl;
	}
	
	@Override
	public void onDisconnect() {
		dcListener.actionPerformed(null);
	}

	@Override
	public void onPauseResumeNotify(boolean isRunning) {
		if (isRunning) {
			playButton.setText("Pause");
			playButton.setToolTipText("Click here to pause the current Track.");
		}
		else {
			playButton.setText("Play");
			playButton.setToolTipText("Click here to resume the Track.");
		}	
		isPlaying = isRunning;
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		setGaplists(gapLists);
		fillGaplistModel();
		repaint();
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		setGaplistName(gapListName);
		fillModels();
		repaint();
	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		this.gaplist = title;
		gaplistLabel.setText("" + gaplist.length);
		getNextTrack();
		fillModels();
		if (secondFrame.isVisible() && secondFrame.getTitle().contains("Track"))
			etl.actionPerformed(null);
		repaint();
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		wishlist = title;
		wishlistLabel.setText("" + wishlist.length);
		getNextTrack();
		fillModels();
		if (secondFrame.isVisible() && secondFrame.getTitle().contains("Track"))
			etl.actionPerformed(null);
		repaint();
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL, boolean isVideo) {
		nowPlaying.setText(title);
		fillModels();
		if (secondFrame.isVisible() && secondFrame.getTitle().contains("Track"))
			etl.actionPerformed(null);
		repaint();
	}

	
	/**
	 * Tries to connect to the given IP and Port.
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @return	True, if the connection was established, false else.
	 */
	public boolean connect(String ip, String port) {
		int iport = 0;
		try {
			iport = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return false;
		}
		wrapper.addNotificationListener(this);
		
		if (wrapper.connect(ip, iport))
			return true;
		else
			return false;
	}
	
	/**
	 * Disconnects from the Server.
	 */
	public void disconnect() {
		dcListener.actionPerformed(null);
		wrapper.close();
	}

	/**
	 * Sets the given Array of Strings as GaplistCollection.
	 * @param gaplists	The new GaplistCollection
	 */
	public synchronized void setGaplists(String[] gaplists) {
		this.gaplists = gaplists;
	}

	/**
	 * Fils the GaplistModel with all Gaplists saved on the Server.
	 */
	public synchronized void fillGaplistModel() {
		gaplistCollectionModel.clear();
		
		try {
			for (String s : gaplists)
				gaplistCollectionModel.addElement(s);
		} catch (NullPointerException e) {
			wrapper.getAvailableGapLists((String[] s) -> {gaplists = s;for (String l : gaplists) gaplistCollectionModel.addElement(l);});
		}
	}

	/**
	 * Sets the Name of the current Gaplist to the given String value.
	 * @param gapListName	The new Name of the Gaplist.
	 */
	public void setGaplistName(String gapListName) {
		gaplistName.setText("Gaplist: "+ gapListName);
	}
	
	/**
	 * Fills the GaplistModel and the WishlistModel.
	 */
	public synchronized void fillModels() {
		gaplistModel.clear();
		wishlistModel.clear();
		
		for (String s : gaplist)
			gaplistModel.addElement(s);
		for (String s : wishlist)
			wishlistModel.addElement(s);
		repaint();
	}
	
	/**
	 * Adds the given Link to a List.
	 * @param link	The Link to the YouTube-Video.
	 * @param toWishlist	True, if the Track should be added to the Wishlist, false else.
	 * @param inFront	True, if the Track should be added in front of the List, false else.
	 * @param tf	The TextField, that contained the Link.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void addToList(String link, boolean toWishlist, boolean inFront, JTextField tf, JLabel fail, JFrame frame) {
		tf.setText("Insert a YouTube Link here.");
		fail.setText("Pending Server.");
		fail.setVisible(true);
		wrapper.addToList((String[] s) -> {if (s[0].equals("true"))fail.setText("Track added!");else fail.setText("Couldn't add the Track.");
										fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);new ShowLabelThread(fail,frame).start();}, 
										link, toWishlist, !inFront);
	}
	
	/**
	 * Updates Wishlist and Gaplist.
	 */
	public void updateLists() {
		wrapper.getWishList((String[] s) -> {wishlist = s;wishlistLabel.setText("" + wishlist.length);nextTrack();});
		wrapper.getGapList((String[] s) -> {gaplist = s;gaplistLabel.setText("" + gaplist.length);nextTrack();});
	}

	/**
	 * Updates the Wishlist.
	 */
	public synchronized void updateWishlist() {
		wrapper.getWishList((String[] s) -> {onWishListUpdatedNotify(s);});
	}

	/**
	 * Updates the Gaplist.
	 */
	public synchronized void updateGaplist() {
		wrapper.getGapList((String[] s) -> {onGapListUpdatedNotify(s);});
	}

	/**
	 * Gets the next Track from the Server and sets the NowPlayingLabel and NextTrackLabel.
	 */
	public void getNextTrack() {
		wrapper.getCurrentTrackTitle((String[] s) -> nowPlaying.setText(s[0]));
		try {
			if (wishlist.length == 0)
				if (gaplist.length == 0)
					nextTrackLabel.setText("No Tracks in the Lists");
				else
					nextTrackLabel.setText(gaplist[0]);
			else
				nextTrackLabel.setText(wishlist[0]);
		}
		catch (NullPointerException e) {
		}
	}
	
	/**
	 * Sets the NextTrackLabel without contacting the Server.
	 */
	private void nextTrack() {
		try {
			if (wishlist.length == 0)
				if (gaplist.length == 0)
					nextTrackLabel.setText("No Tracks in the Lists");
				else
					nextTrackLabel.setText(gaplist[0]);
			else
				nextTrackLabel.setText(wishlist[0]);
		}
		catch (NullPointerException e) {
		}
	}
	
	/**
	 * Gets the Status of the Server (Track playing or Track paused). Needed at the Start of 
	 * the Client.
	 */
	public void getFirstStatus() {
		wrapper.getCurrentPlaybackStatus((String[] s) -> {onPauseResumeNotify(Boolean.parseBoolean(s[0]));});
	}
	
	/**
	 * Creates a Gaplist with the given Name.
	 * @param name	The Name of the new Gaplist.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void createGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.switchToGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Created a new Gaplist.");else fail.setText("Failed to create a new Gaplist.");
												fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
												new ShowLabelThread(fail, frame).start();}, name);
	}
	
	/**
	 * Skips the current Track.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void skip(JLabel fail, JFrame frame) {
		wrapper.skip((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Skipped the Track successfully");else fail.setText("Failed to skip the Track");
									fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}

	/**
	 * Deletes the Track at the given index from the Gaplist.
	 * @param index	The index of the Track to be deleted.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void deleteFromGaplist(int index, JLabel fail, JFrame frame) {
		wrapper.deleteFromList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Deleted the Track from the List.");else 
												fail.setText("Couldn't delete the Track from the List.");fail.setHorizontalAlignment(JLabel.CENTER);
												fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();}, index);
	}
	
	/**
	 * Sends a Message to the Server, that the PlayButton was pressed.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void playButtonPressed(JLabel fail, JFrame frame) {
		wrapper.pauseResume((String[] s) -> {if (isPlaying)if (Boolean.parseBoolean(s[0]))fail.setText("Track resumed.");else fail.setText("Track couldn't be resumed.");else 
											if (Boolean.parseBoolean(s[0]))fail.setText("Track paused.");else fail.setText("Track couldn't be paused.");
											fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	/**
	 * Moves the Track at the given index one position down in the Gaplist.
	 * @param index	The index of the Track to be moved.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void moveTrackDown(int index, JLabel fail, JFrame frame) {
		wrapper.setGapListTrackDown((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Moved Track down.");else fail.setText("Failed to move the Track.");
													fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);
													new ShowLabelThread(fail, frame).start();}, index);
		try{Thread.sleep(100);}catch(Exception e) {}
		repaint();
	}
	
	/**
	 * Moves the Track at the given index one position up in the Gaplist.
	 * @param index	The index of the Track to be moved.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void moveTrackUp(int index, JLabel fail, JFrame frame) {
		wrapper.setGapListTrackUp((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Moved Track up.");else fail.setText("Failed to move the Track.");
													fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);
													new ShowLabelThread(fail, frame).start();}, index);
		try{Thread.sleep(100);}catch(Exception e) {}
		repaint();
	}
	
	/**
	 * Saves the current Gaplist on the Server.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void saveGaplist(JLabel fail, JFrame frame) {
		wrapper.saveGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Saved Gaplist");else fail.setText("Failed to save the Gaplist.");
											fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	/**
	 * Loads the Gaplist with the given Name.
	 * @param name	The name of the Gaplist to be loaded.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void loadGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.switchToGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Gaplist loaded.");else fail.setText("Failed to load the Gaplist.");
												fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
												new ShowLabelThread(fail, frame).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
	
	/**
	 * Repaints the second Frame.
	 */
	public synchronized void repaint() {
		if (secondFrame != null)
			secondFrame.repaint();
	}
	
	/**
	 * Updates the Name of the current Gaplist.
	 */
	public void updateGaplistName() {
		wrapper.getCurrentGapListName((String[] s) -> {if (gaplistName != null){gaplistName.setText("Gaplist - " + s[0]);
														gaplistName.setHorizontalAlignment(JLabel.CENTER);gaplistName.setVerticalAlignment(JLabel.CENTER);}});
	}

	/**
	 * Fills the ContentModel with the Tracks of the Gaplist with the given name.
	 * @param name	The name of the Gaplist to be shown.
	 * @param fail	The Label, that displays Responses.
	 */
	public void fillContentModel(String name, JLabel fail) {
		contentLabel.setText("Content - " + name + ".jb");
		contentLabel.setHorizontalAlignment(JLabel.CENTER);
		contentLabel.setVerticalAlignment(JLabel.CENTER);
		wrapper.getTitleFromGapList((String[] s) -> {contentModel.clear();for (String l : s) contentModel.addElement(l);fail.setText("Showing Elements of "+name);
													new ShowLabelThread(fail, null).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
	
	/**
	 * Removes the Gaplist with the given name.
	 * @param name	The name of the Gaplist to be deleted.
	 * @param fail	The Label, that displays Responses.
	 * @param frame	The Frame, that contains the Fail-Label.
	 */
	public void removeGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.deleteGapList((String[] s) -> {if (Boolean.parseBoolean(s[0])) fail.setText("Removed Track from the Gaplist.");else 
			fail.setText("Failed to remove the Track from the Gaplist.");fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
			new ShowLabelThread(fail, frame).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
}
