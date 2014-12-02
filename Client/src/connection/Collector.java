package connection;

import gui.DisconnectButtonListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import util.ShowLabelThread;
import clientinterface.listener.NotificationListener;
import clientwrapper.YTJBClientWrapper;

public class Collector implements NotificationListener{

	private YTJBClientWrapper wrapper;
	private boolean isPlaying;
	private JButton playButton;
	private JLabel gaplistName;
	private JLabel nowPlaying;
	private JLabel gaplistLabel;
	private JLabel wishlistLabel;
	private JLabel nextTrackLabel;
	private JLabel contentLabel;
	private String[] gaplist;
	private String[] wishlist;
	private String[] gaplists;
	private JFrame secondFrame;
	private DisconnectButtonListener dcListener;
	private DefaultListModel<String> gaplistModel;
	private DefaultListModel<String> wishlistModel;
	private DefaultListModel<String> gaplistCollectionModel;
	private DefaultListModel<String> contentModel;
	
	public Collector() {
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		gaplistCollectionModel = new DefaultListModel<String>();
		contentModel = new DefaultListModel<String>();
	}
	
	public void addPlayButton(JButton playButton) {
		this.playButton = playButton;
	}
	
	public void addGaplistLabel(JLabel gaplistLabel) {
		this.gaplistLabel = gaplistLabel;	
	}
	
	public void addWishlistLabel(JLabel wishlistLabel) {
		this.wishlistLabel = wishlistLabel;
	}
	
	public void addGaplistNameLabel(JLabel gaplistName) {
		this.gaplistName = gaplistName;
	}
	
	public void addNowPlayingLabel(JLabel nowPlaying) {
		this.nowPlaying = nowPlaying;
	}
	
	public void addNextTrackLabel(JLabel nextTrackLabel) {
		this.nextTrackLabel = nextTrackLabel;
	}
	
	public void addDisconnectListener(DisconnectButtonListener dcListener) {
		this.dcListener = dcListener;
	}
	
	public void addGaplistModel(DefaultListModel<String> gaplistModel) {
		this.gaplistModel = gaplistModel;
	}
	
	public void addWishlistModel(DefaultListModel<String> wishlistModel) {
		this.wishlistModel = wishlistModel;
	}
	
	public void addGaplistCollectionModel(DefaultListModel<String> gaplistCollectionModel) {
		this.gaplistCollectionModel = gaplistCollectionModel;
	}
	
	public void addContentModel(DefaultListModel<String> contentModel) {
		this.contentModel = contentModel;
	}
	
	public void addContentLabel(JLabel contentLabel) {
		this.contentLabel = contentLabel;
	}
	
	public void addSecondFrame(JFrame frame) {
		this.secondFrame = frame;
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
		repaint();
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		wishlist = title;
		wishlistLabel.setText("" + wishlist.length);
		getNextTrack();
		fillModels();
		repaint();
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL) {
		nowPlaying.setText(title);
		fillModels();
		repaint();
	}

	
	public boolean connect(String ip, String port) {
		int iport = 0;
		try {
			iport = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return false;
		}
		wrapper = new YTJBClientWrapper(ip, iport);
		wrapper.addNotificationListener(this);
		
		if (wrapper.connect())
			return true;
		else
			return false;
	}
	
	public void disconnect() {
		dcListener.actionPerformed(null);
		wrapper.close();
	}

	public void setGaplists(String[] gaplists) {
		this.gaplists = gaplists;
	}

	public void fillGaplistModel() {
		gaplistCollectionModel.clear();
		
		try {
			for (String s : gaplists)
				gaplistCollectionModel.addElement(s);
		} catch (NullPointerException e) {
			wrapper.getAvailableGapLists((String[] s) -> {gaplists = s;for (String l : gaplists) gaplistCollectionModel.addElement(l);});
		}
	}

	public void setGaplistName(String gapListName) {
		gaplistName.setText("Gaplist: "+ gapListName);
	}

	public void fillModels() {
		gaplistModel.clear();
		wishlistModel.clear();
		
		for (String s : gaplist)
			gaplistModel.addElement(s);
		for (String s : wishlist)
			wishlistModel.addElement(s);
		repaint();
	}
	
	public void addToList(String link, boolean toWishlist, boolean inFront, JTextField tf, JLabel fail, JFrame frame) {
		tf.setText("Insert a YouTube Link here.");
		fail.setText("Pending Server.");
		fail.setVisible(true);
		wrapper.addToList((String[] s) -> {if (s[0].equals("true"))fail.setText("Track added!");else fail.setText("Couldn't add the Track.");
										fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);new ShowLabelThread(fail,frame).start();}, 
										link, toWishlist, !inFront);
	}
	
	public void updateLists() {
		wrapper.getWishList((String[] s) -> {wishlist = s;wishlistLabel.setText("" + wishlist.length);nextTrack();});
		wrapper.getGapList((String[] s) -> {gaplist = s;gaplistLabel.setText("" + gaplist.length);nextTrack();});
	}

	public void updateWishlist() {
		wrapper.getWishList((String[] s) -> {onWishListUpdatedNotify(s);});
	}

	public void updateGaplist() {
		wrapper.getGapList((String[] s) -> {onGapListUpdatedNotify(s);});
	}

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
	
	public void getFirstStatus() {
		wrapper.getCurrentPlaybackStatus((String[] s) -> {onPauseResumeNotify(Boolean.parseBoolean(s[0]));});
	}
	
	public void createGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.switchToGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Created a new Gaplist.");else fail.setText("Failed to create a new Gaplist.");
												fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
												new ShowLabelThread(fail, frame).start();}, name);
		wrapper.saveGapList((String[] s) -> {boolean answer = Boolean.parseBoolean(s[0]);if (answer)fail.setText("New Gaplist created.");else 
											fail.setText("Failed to create a new Gaplist.");fail.setHorizontalAlignment(JLabel.CENTER); 
											fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	public void skip(JLabel fail, JFrame frame) {
		wrapper.skip((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Skipped the Track successfully");else fail.setText("Failed to skip the Track");
									fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	public void deleteFromGaplist(int index, JLabel fail, JFrame frame) {
		wrapper.deleteFromList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Deleted the Track from the List.");else 
												fail.setText("Couldn't delete the Track from the List.");fail.setHorizontalAlignment(JLabel.CENTER);
												fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();}, index);
	}
	
	public void playButtonPressed(JLabel fail, JFrame frame) {
		wrapper.pauseResume((String[] s) -> {if (isPlaying)if (Boolean.parseBoolean(s[0]))fail.setText("Track resumed.");else fail.setText("Track couldn't be resumed.");else 
											if (Boolean.parseBoolean(s[0]))fail.setText("Track paused.");else fail.setText("Track couldn't be paused.");
											fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	public void moveTrackDown(int index, JLabel fail, JFrame frame) {
		wrapper.setGapListTrackDown((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Moved Track down.");else fail.setText("Failed to move the Track.");
													fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);
													new ShowLabelThread(fail, frame).start();}, index);
		try{Thread.sleep(100);}catch(Exception e) {}
		repaint();
	}
	
	public void moveTrackUp(int index, JLabel fail, JFrame frame) {
		wrapper.setGapListTrackUp((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Moved Track up.");else fail.setText("Failed to move the Track.");
													fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);
													new ShowLabelThread(fail, frame).start();}, index);
		try{Thread.sleep(100);}catch(Exception e) {}
		repaint();
	}
	
	public void saveGaplist(JLabel fail, JFrame frame) {
		wrapper.saveGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Saved Gaplist");else fail.setText("Failed to save the Gaplist.");
											fail.setHorizontalAlignment(JLabel.CENTER);fail.setVerticalAlignment(JLabel.CENTER);new ShowLabelThread(fail, frame).start();});
	}
	
	public void loadGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.switchToGapList((String[] s) -> {if (Boolean.parseBoolean(s[0]))fail.setText("Gaplist loaded.");else fail.setText("Failed to load the Gaplist.");
												fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
												new ShowLabelThread(fail, frame).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
	
	public void repaint() {
		if (secondFrame != null)
			secondFrame.repaint();
	}
	
	public void updateGaplistName() {
		wrapper.getCurrentGapListName((String[] s) -> {if (gaplistName != null){gaplistName.setText("Gaplist - " + s[0]);
														gaplistName.setHorizontalAlignment(JLabel.CENTER);gaplistName.setVerticalAlignment(JLabel.CENTER);}});
	}

	public void fillContentModel(String name, JLabel fail) {
		contentLabel.setText("Content - " + name + ".jb");
		contentLabel.setHorizontalAlignment(JLabel.CENTER);
		contentLabel.setVerticalAlignment(JLabel.CENTER);
		wrapper.getTitleFromGapList((String[] s) -> {contentModel.clear();for (String l : s) contentModel.addElement(l);fail.setText("Showing Elements of "+name);
													new ShowLabelThread(fail, null).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
	
	public void removeFromGaplist(String name, JLabel fail, JFrame frame) {
		wrapper.deleteGapList((String[] s) -> {if (Boolean.parseBoolean(s[0])) fail.setText("Removed Track from the Gaplist.");else 
			fail.setText("Failed to remove the Track from the Gaplist.");fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
			new ShowLabelThread(fail, frame).start();}, name);
		try{Thread.sleep(100);} catch (Exception e) {}
		repaint();
	}
}
