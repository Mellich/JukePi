package windows;

import util.TextFieldListener;
import util.PopClickListener;
import util.layouts.NewClientLayout;
import util.tasks.SetContentTask;
import util.tasks.SetGaplistTask;
import util.tasks.SetSavedGaplistsTask;
import util.tasks.SetWishlistTask;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import messages.Permission;
import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.listener.PauseResumeNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import connection.Collector;

/**
 * The Main {@link Window}, that contains information transmitted by the Server, this Client 
 * is connected to.
 * @author Haeldeus
 * @version 1.8
 */
public class MainWindow extends Window implements DefaultNotificationListener, PauseResumeNotificationListener, GapListNotificationListener, DebugNotificationListener{
	
	/**
	 * The {@link Collector}, that will perform Actions with extern needed information.
	 */
	private final Collector collector;
	
	/**
	 * The TextField that contains the Link.
	 * @see JTextField
	 */
	private JTextField txtLink;
	
	/**
	 * The Label that displays possible Messages.
	 * @see JLabel
	 */
	private JLabel lblFail;
	
	/**
	 * The Frame, this Screen displays.
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * The {@link ServerConnection}, that will send the Messages.
	 */
	private final ServerConnection wrapper;

	/**
	 * The Gaplist, that contains all {@link Song}s in the Gaplist.
	 */
	private Song[] gaplist;
	
	/**
	 * The Wishlist, that contains all {@link Song}s in the Wishlist.
	 */
	private Song[] wishlist;
	
	/**
	 * The Button, that can be pushed to pause/resume a Track.
	 * @see JButton
	 */
	private JButton btnPlayPause;
	
	/**
	 * The Label, that will display the Name of the current Gaplist.
	 * @see JLabel
	 */
	private JLabel lblGaplistName;
	
	/**
	 * The Label, that will display the Name of the current Track.
	 * @see JLabel
	 */
	private JLabel lblPlayingTrack;
	
	/**
	 * The Label, that will display the Name of the next Track.
	 * @see JLabel
	 */
	private JLabel lblTrackNext;
	
	/**
	 * The Label, that will display the number of Tracks in the Gaplist.
	 * @see JLabel
	 */
	private JLabel lblNoGaplist;
	
	/**
	 * The Label, that will display the number of Tracks in the Wishlist.
	 * @see JLabel
	 */
	private JLabel lblNoWishlist;
	
	/**
	 * The ScrollPane, that contains the old Wishlist-Table. Has to be stored to be able to 
	 * keep the table updated.
	 * @see JScrollPane
	 */
	private JScrollPane oldPane;
	
	/**
	 * The ScrollPane, that contains the old Gaplist-Table. Has to be stored to be able to
	 * keep the table updated.
	 * @see JScrollPane
	 */
	private JScrollPane oldGaplistPane;

	/**
	 * The ScrollPane, that contains the old Saved-Gaplists-Table. Has to be stored to be able
	 * to keep the table updated.
	 * @see JScrollPane
	 * 
	 */
	private JScrollPane oldSavedGaplistPane;
	
	/**
	 * The Gaplists saved on the Server.
	 */
	private String[] gaplists;
	
	/**
	 * The ScrollPane, that contains the old Content-Table. Has to be stored to be able to 
	 * keep the table updated.
	 * @see JScrollPane
	 */
	private JScrollPane oldContentPane;
	
	/**
	 * The title of the Frame.
	 */
	private String title;
	
	/**
	 * The Icon, that will be displayed instead of "Pause" as a String.
	 */
	private final ImageIcon playIcon = new ImageIcon("pause.png");
	
	/**
	 * The Icon, that will be displayed instead of "Play" as a String.
	 */
	private final ImageIcon pauseIcon = new ImageIcon("pause.png");
	
	/**
	 * The StringBuilder, that will buffer the Messages to maintain functionality and 
	 * correctness of the Messages.
	 */
	private StringBuilder buffer;
	
	/**
	 * The TextArea for the Messages from the Server.
	 * @see JTextArea
	 */
	private JTextArea txtDebugs;
	
	/**
	 * The {@link JScrollPane}, that contains the DebugArea.
	 */
	private JScrollPane scrollPane;
	
//	private final String adminPassword;
//	
//	private final String playerPassword;
	
	/**
	 * The OptionsWindow, that will be opened, when the User clicked on Edit > Preferences.
	 */
	private OptionsWindow options;
	
	/**
	 * The {@link DisplayGaplistsWindow}, that will show the saved Gaplists when opened.
	 */
	private DisplayGaplistsWindow gaplistsWindow;
	
	/**
	 * The URL of the Song, that is currently played on the Server.
	 */
	private String currentURL;
	
	/**
	 * The Constructor for the Main-Screen. Will set the parameters to their belonging 
	 * variables.
	 * @param collector	The {@link Collector}, that will perform Actions with extern needed 
	 * information.
	 * @param frame	The Frame, this Screen will display.
	 * @param wrapper	The {@link ServerConnection}, that will send the Messages.
	 * @param gaplist	The Gaplist as an Array of {@link Song}s.
	 * @param wishlist	The Wishlist as an Array of {@link Song}s.
	 * @since 1.0
	 */
	public MainWindow(Collector collector, JFrame frame, ServerConnection wrapper, Song[] gaplist, Song[] wishlist, String ip, int iport, String adminPassword, 
			String playerPassword) {
		this.collector = collector;
		this.frame = frame;
		this.wrapper = wrapper;
		
		this.gaplist = gaplist;
		this.wishlist = wishlist;
		buffer = new StringBuilder("");
		
	//	this.adminPassword = adminPassword;
	//	this.playerPassword = playerPassword;
		
		options = new OptionsWindow(collector, adminPassword, playerPassword);
		
//		wrapper.addPermission(Permission.ADMIN, adminPassword);
//		wrapper.addPermission(Permission.PLAYBACK, "playback");
//		wrapper.addPermission(Permission.DEBUGGING, "debug");
		wrapper.addDefaultNotificationListener(this);
		wrapper.addGapListNotificationListener(this);
		wrapper.addPauseResumeNotificationListener(this);
		wrapper.addDebugNotificationListener(this);
//		Permission[] p = wrapper.getPermissions();
//		for (Permission temp : p){
//			System.out.print(temp+", ");
//		}
//		System.out.println();
		setIpAndPort(ip, iport);
	}
	
	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}
	
	@Override
	public void close() {
		frame.setVisible(false);
	}
	
	/**
	 * Sets the IP and Port of the Server, the Client is connected to, so the Title of the 
	 * Frame can display it.
	 * @param ip	The IP of the Server, the Client is connected to.
	 * @param port	The Port of the Server, the Client is connected to.
	 * @see JFrame#setTitle(String)
	 * @since 1.0
	 */
	public void setIpAndPort(String ip, int port) {
		frame.setTitle("JukePi - "+ip+":"+port);
		title = "JukePi - "+ip+":"+port;
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}
	
	/**
	 * Skips the current Song.
	 * @see ServerConnection#skip(ResponseListener)
	 * @since 1.0
	 */
	private void skip() {
		wrapper.skip((String[] s) -> {	if (s[0].equals("true")) 
											showFail("Skipped Track successfully!"); 
										else 
											showFail("Couldn't skip the Track!");
									});
	}
	
	/**
	 * Messages the Server, that the Play/Pause-Button was pressed.
	 * @see ServerConnection#pauseResume(ResponseListener)
	 * @since 1.0
	 */
	private void pressPause() {
		wrapper.pauseResume((String[] s) -> {	if (s[0].equals("true"))
													wrapper.getCurrentPlaybackStatus((String[] st) -> {	if (st[0].equals("false"))
																											showFail("Paused the Track successfully!");
																										else
																											showFail("Resumed the Track successfully!");
																										});
												else
													wrapper.getCurrentPlaybackStatus((String[] str) -> {	if (str[0].equals("false"))
																												showFail("Couldn't resume the Track!");
																											else
																												showFail("Couldn't pause the Track!");
																										});
											});
	}
	
	/**
	 * Seeks 30 seconds either forward or backward.
	 * @param forward	Determines, whether the Server should seek forward({@code true}) or 
	 * backward({@code false}).
	 * @see ServerConnection#seekForward(ResponseListener)
	 * @see ServerConnection#seekBackward(ResponseListener)
	 * @since 1.0
	 */
	private void seek(boolean forward) {
		if (forward)
			wrapper.seekForward((String[] s) -> {	if (s[0].equals("true")) 
														showFail("Successfully sought forward!");
													else
														showFail("Couldn't seek forward!");
												});
		else
			wrapper.seekBackward((String[] s) -> {	if (s[0].equals("true"))
														showFail("Successfully sought backwards!");
													else
														showFail("Couldn't seek backwards!");
												});
	}
	
	/**
	 * Adds the given Link to a List, either the Gap- or the Wishlist.
	 * @param link	The Link to the Song.
	 * @param toWishlist	Determines, whether the Song should be added to the Wishlist 
	 * ({@code true}) or to the Gaplist ({@code false}).
	 * @param inFront Determines, whether the Track should be added in Front of the List 
	 * ({@code true}) or at the the End of the List ({@code false}).
	 * @param textfield The TextField, that contains the Link.
	 * @see ServerConnection#addToList(ResponseListener, String, boolean, boolean)
	 * @since 1.0
	 */
	private void add(String link, boolean toWishlist , boolean inFront, JTextField textfield) {
		if (!link.isEmpty()) {
			showFail("Pending Server...");
			wrapper.addToList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Track added!");
												else 
													showFail("Couldn't add the Track.");
												textfield.setText("Insert a Link here");
												}, 
								link, toWishlist, !inFront);
		}
		else {
			showFail("No valid link!");
			textfield.setText("Insert a Link here");
		}
	}
	
//	/**
//	 * Sets the Gaplist to the given List and updates the Gaplist-Model
//	 * @param gaplist	The new Gaplist.
//	 * @since 1.0
//	 */
//	public void setGaplist(Song[] gaplist) {
//		this.gaplist = gaplist;
//		lblNoGaplist.setText(""+gaplist.length);
//		new SetGaplistTask(this.gaplist, gaplist, lblNoGaplist, wrapper, frame, oldGaplistPane, this).execute();
//	//	createGaplistTable();
//		setNextTrack();
//	}
	
	
	
//	/**
//	 * Sets the Wishlist to the given List and updates the Wishlist-Table.
//	 * @param wishlist	The new Wishlist.
//	 * @since 1.0
//	 */
//	public void setWishlist(Song[] wishlist) {
//		this.wishlist = wishlist;
//		lblNoWishlist.setText(""+wishlist.length);
//		createWishlistTable();
//		setNextTrack();
//	}
	
//	/**
//	 * Sets the Gaplists to the given List and updates the Saved-Gaplists-Table.
//	 * @param gaplists	The Gaplists on the Server.
//	 * @since 1.2
//	 */
//	public void setGaplists(String[] gaplists) {
//		this.gaplists = gaplists;
//		createSavedGaplistsTable();
//	}
	
	/**
	 * Moves the Song at the given index upwards in the Gaplist.
	 * @param index	The index of the Track to be moved.
	 * @param list	The List, that contains the Gaplist-Model.
	 * @see ServerConnection#setGapListTrackUp(ResponseListener, long)
	 * @since 1.0
	 */
	private void moveTrackUp(int index) {
		if (index >=0)
			wrapper.setGapListTrackUp((String[] s)-> {	if (s[0].equals("true")) {
															showFail("Moved Track up.");
															try{Thread.sleep(100);}catch(Exception e) {}
															setSelectedGaplistIndex(index-1);
														}
														else {
															showFail("Couldn't move Track up.");
															try{Thread.sleep(100);}catch(Exception e) {}
															setSelectedGaplistIndex(index);
														}
													}, gaplist[index].getTrackID());
	}
	
	/**
	 * Moves the Song at the given index downwards in the Gaplist.
	 * @param index	The index of the Track to be moved.
	 * @param list	The List, that contains the Gaplist-Model.
	 * @see ServerConnection#setGapListTrackDown(ResponseListener, long)
	 * @since 1.0
	 */
	private void moveTrackDown(int index) {
		if (index >= 0)
			wrapper.setGapListTrackDown((String[] s) -> {	if (s[0].equals("true")) {
																showFail("Moved Track down.");
																try{Thread.sleep(100);}catch(Exception e) {}
																setSelectedGaplistIndex(index+1);
															}
															else {
																showFail("Couldn't move Track down");
																try{Thread.sleep(100);}catch(Exception e) {}
																setSelectedGaplistIndex(index);
															}
														}, gaplist[index].getTrackID());
	}
	
	/**
	 * Deletes the Song at the given index from the Gaplist.
	 * @param index	The index of the Song to be deleted.
	 * @param list	The List, that contains the Gaplist-Model.
	 * @see ServerConnection#deleteFromList(Song)
	 * @since 1.0
	 */
	private void deleteTrack(int index) {
		if (index >= 0)
			wrapper.deleteFromList((String[] s) -> {	if (s[0].equals("true"))
															showFail("Deleted the Track from the Gaplist.");
														else
															showFail("Couldn't delete the Track from the Gaplist.");
														try {Thread.sleep(100);} catch (Exception e) {}
														setSelectedGaplistIndex(index);
													}, gaplist[index]);
	}
	
	/**
	 * Saves the current Gaplist on the Server.
	 * @see ServerConnection#saveGapList(ResponseListener)
	 * @since 1.0
	 */
	private void saveGaplist() {
		wrapper.saveGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Saved Gaplist.");
												else
													showFail("Couldn't save the Gaplist.");
											});
	}
	
	/**
	 * Loads the Gaplist with the given Name
	 * @param name	The Name of the Gaplist to be loaded.
	 * @since 1.2
	 */
	private void loadGaplist(String name) {
		wrapper.switchToGapList((String[] s) -> {	if (s[0].equals("true"))
														showFail("Loaded Gaplist.");
													else
														showFail("Couldn't load the Gaplist.");
												}, name);
	}
	
	/**
	 * Shows the Content of the Gaplist with the given Name.
	 * @param name	The Name of the Gaplist, which Content will be shown.
	 * @since 1.2
	 */
	private void showGaplist(String name) {
		wrapper.getTitleFromGapList((String[] s) -> {	Song[] songs = new Song[s.length/2];
														for (int i = 0; i < s.length; i = i+2) {
															songs[i/2] = new Song(-1, s[i], 0, false, null, s[i+1]);
														}
												//		new SetContentTask(songs, oldContentPane, frame, this).execute();
													}, name);
	//	createContentTable(wrapper.getTitleFromGapList(name));
	}
	
	/**
	 * Will be executed, when a Song was paused or resumed on the Server.
	 * @param isPlaying	Determines, if the Song is now playing ({@code true}) or paused 
	 * ({@code false}).
	 * @since 1.0
	 */
	public void pauseResume(boolean isPlaying) {
		if (isPlaying) {
			btnPlayPause.setIcon(pauseIcon);
			btnPlayPause.setText("Pause");
			btnPlayPause.setToolTipText("Click here to pause the Track.");
		}
		else {
			btnPlayPause.setIcon(playIcon);
			btnPlayPause.setText("Play");
			btnPlayPause.setToolTipText("Click here to resume the Track.");
		}
	}
	
//	/**
//	 * Will be executed, when an other Gaplist was loaded on the Server.
//	 * @param gapListName	The Name of the new Gaplist.
//	 * @since 1.0
//	 */
//	public void gaplistChanged(String gapListName) {
//		lblGaplistName.setText("Gaplist - " + gapListName);
//	}
	
	/**
	 * Sets the Text of the PlayingTrackLabel to the given title.
	 * @param title	The title of the song, that is now playing.
	 * @since 1.2
	 */
	public void setNowPlaying(String title) {
		lblPlayingTrack.setText(title);
	}
	
	/**
	 * Sets the Text of the NextTrackLabel to the given title.
	 * @since 1.0
	 */
	public void setNextTrack() {
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
	}
	
	/**
	 * Sets the SelectedIndex of gaplistList to the given index.
	 * @param index	The index of the new Selection.
	 * @since 1.1
	 */
	public void setSelectedGaplistIndex(int index) {
		if (index >= 0) {
			try {
				((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).setRowSelectionInterval(index, index);
			}
			catch (IllegalArgumentException iae) {
				((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).setRowSelectionInterval(index-1, index-1);
			}
		}
	}
	
	/**
	 * Removes the Gaplist with the given Name from the Server.
	 * @param name	The Name of the Gaplist to be removed.
	 * @since 1.2
	 */
	private void removeGaplist(String name) {
		wrapper.deleteGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed the Gaplist.");
												else
													showFail("Couldn't remove the Gaplist");
											  }, name);
	}
	
	/**
	 * Replaces all special Characters from the given String.
	 * @param regex The String to have all specials replaced.
	 * @return	The given String without special Characters.
	 * @since 1.2
	 */
//	private String replaceSpecials(String regex) {
//		regex = regex.replaceAll("ä", "ae");
//		regex = regex.replaceAll("Ä", "ae");
//		regex = regex.replaceAll("ü", "ue");
//		regex = regex.replaceAll("Ü", "ue");
//		regex = regex.replaceAll("ö", "oe");
//		regex = regex.replaceAll("Ö", "oe");
//		regex = regex.replaceAll("ß", "ss");
//		return regex;
//	}
	
	/**
	 * Votes for the Song at the given index.
	 * @param index	The index of the Song, that will be voted for.
	 * @since 1.3
	 */
	private void vote(int index) {
		if (index == -1)
			return;
		wrapper.removeVote((String[] s)-> {});
		wrapper.voteSong((String[] s) -> {	if (s[0].equals("true"))
												showFail("Voted for the Song");
											else
												showFail("Couldn't vote for the Song");
										//	createWishlistTable();
										}, wishlist[index]);
	}
	
	/**
	 * Removes the Vote.
	 * @since 1.3
	 */
	private void removeVote() {
		wrapper.removeVote((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed your vote.");
												else
													showFail("Couldn't remove your vote.");
											//	createWishlistTable();
											});
	}
	
	/**
	 * Removes all Votes from the Server.
	 * @since 1.7
	 */
	private void removeAllVotes() {
		if (wrapper.deleteAllVotes())
			showFail("Deleted all Votes");
		else
			showFail("Couldn't delete all Votes");
	}
	
	/**
	 * Creates a new Frame.
	 * @return The created Frame.
	 * @since 1.0
	 * @wbp.parser.entryPoint
	 */
	private void constructFrame() {
		long start = System.currentTimeMillis();
		util.IO.println(this, "Starting to build frame");
		gaplist = wrapper.getGapList();
		wishlist = wrapper.getWishList();
		gaplists = wrapper.getAvailableGapLists();
		
		frame = new JFrame();
		frame.setTitle(title);
		frame.setSize(new Dimension(620,700));
		NewClientLayout layout = new NewClientLayout();
		frame.getContentPane().setLayout(layout);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(617,695));
		/*Delete till here*/		
		
		lblFail = new JLabel("");
		frame.getContentPane().add(lblFail, NewClientLayout.FAIL_LABEL);
		
		
		final JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblGaplist, NewClientLayout.GAPLIST_LABEL);
		
		final JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
		lblWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblWishlist, NewClientLayout.WISHLIST_LABEL);
		
		lblNoGaplist = new JLabel(""+ gaplist.length);
		lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoGaplist, NewClientLayout.COUNT_GAPLIST_LABEL);
		
		lblNoWishlist = new JLabel("" + wishlist.length);
		lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoWishlist, NewClientLayout.COUNT_WISHLIST_LABEL);
		
		txtLink = new JTextField("Insert a Link here.");
		txtLink.addMouseListener(new PopClickListener(txtLink));
		frame.getContentPane().add(txtLink, NewClientLayout.LINK_TEXTFIELD);
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Adds the YouTube-Link in the upper Textfield either to the Gaplist or the Wishlist, whatever is selected on the right.");
		frame.getContentPane().add(btnAdd, NewClientLayout.ADD_BUTTON);
		
		final JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		frame.getContentPane().add(rdbtnWishlist, NewClientLayout.WISHLIST_RADIO);
		rdbtnWishlist.setSelected(true);
		
		final JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		frame.getContentPane().add(rdbtnGaplist, NewClientLayout.GAPLIST_RADIO);
		
		final JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNowPlaying, NewClientLayout.NOW_PLAYING_LABEL);
		
		final JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNextTrack, NewClientLayout.NEXT_TRACK_LABEL);
		
		final JLabel lblBuildVersion = new JLabel("0.9.1 - New Layout, Untested");
		lblBuildVersion.setHorizontalAlignment(JLabel.RIGHT);
		frame.getContentPane().add(lblBuildVersion, NewClientLayout.BUILD_VERSION_LABEL);
		
		lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblPlayingTrack, NewClientLayout.NAME_NOW_PLAYING_LABEL);
		wrapper.getCurrentSong((String[] s) -> {lblPlayingTrack.setText(s[1]); currentURL = s[2];});
		
		lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblTrackNext, NewClientLayout.NAME_NEXT_TRACK_LABEL);
		
		txtDebugs = new JTextArea();
		txtDebugs.setEditable(false);
		scrollPane = new JScrollPane(txtDebugs);
		JScrollBar sb = scrollPane.getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
		frame.getContentPane().add(scrollPane, NewClientLayout.DEBUG_PANE);
		
		Song[] wishlist = wrapper.getWishList();
		Song[] gaplist = wrapper.getGapList();
		
		collector.setLists(wishlist, gaplist);
		
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
		
		
		//TODO:
		ImageIcon icon = new ImageIcon("play.png");
		
		btnPlayPause = new JButton(icon);
		frame.getContentPane().add(btnPlayPause, NewClientLayout.PLAY_PAUSE_BUTTON);
		
		final JButton btnSeekBackwards = new JButton("<html><body>Seek<br>Backward</body></html>");
		btnSeekBackwards.setToolTipText("Click here to seek 30 seconds backward.");
		frame.getContentPane().add(btnSeekBackwards, NewClientLayout.SEEK_BACK_BUTTON);
		
		final JButton btnSkip = new JButton("Skip");
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip, NewClientLayout.SKIP_BUTTON);
		
		final JButton btnSeekForward = new JButton("<html><body>Seek<br>Forward</body></html>");
		btnSeekForward.setToolTipText("Click here to seek 30 seconds forward.");
		frame.getContentPane().add(btnSeekForward, NewClientLayout.SEEK_FORWARD_BUTTON);
		
		final JCheckBox chckbxInfront = new JCheckBox("In Front");
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of the list.");
		frame.getContentPane().add(chckbxInfront, NewClientLayout.IN_FRONT_CHECKBOX);
		
		
	//	createWishlistTable();
		oldPane = new JScrollPane();
		new SetWishlistTask(wishlist, wishlist, lblNoWishlist, wrapper, frame, oldPane, this).execute();
		//	createGaplistTable();
		oldGaplistPane = new JScrollPane();
		new SetGaplistTask(gaplist, gaplist, lblNoGaplist, wrapper, frame, oldGaplistPane, this).execute();
		
		lblGaplistName = new JLabel("");
		lblGaplistName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplistName.setVerticalAlignment(JLabel.CENTER);
		lblGaplistName.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblGaplistName, NewClientLayout.GAPLIST_NAME_LABEL);
		
		wrapper.getCurrentGapListName((String[] s) -> {lblGaplistName.setText("Gaplist - "+ s[0]);});
		
		final JLabel lblWishlist2 = new JLabel("Wishlist");
		lblWishlist2.setHorizontalAlignment(JLabel.CENTER);
		lblWishlist2.setVerticalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblWishlist2, NewClientLayout.WISHLIST_NAME_LABEL);
		
//TODO	final JButton btnDelete = new JButton("Delete");
//		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
//		frame.getContentPane().add(btnDelete, NewClientLayout.DELETE_BUTTON);
		
		final JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Click here to save the current Gaplist on the Server.");
		frame.getContentPane().add(btnSave, NewClientLayout.SAVE_BUTTON);
		
		final JButton btnUp = new JButton("/\\");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		frame.getContentPane().add(btnUp, NewClientLayout.TRACK_UP_BUTTON);
		
		final JButton btnDown = new JButton("\\/");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		frame.getContentPane().add(btnDown, NewClientLayout.TRACK_DOWN_BUTTON);
		
	//	createSavedGaplistsTable();
//		oldSavedGaplistPane = new JScrollPane();
//TODO	frame.getContentPane().add(oldSavedGaplistPane, NewClientLayout.SAVED_GAPLIST_SCROLL);
//		new SetSavedGaplistsTask(frame, gaplists, oldSavedGaplistPane, this).execute();
//		oldContentPane = new JScrollPane();
//TODO	frame.getContentPane().add(oldContentPane, NewClientLayout.CONTENT_SCROLL);
//		new SetContentTask(new Song[] {}, oldContentPane, frame, this).execute();
		
		final ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);
		
//TODO	final JLabel lblSavedGaplists = new JLabel("Saved Gaplists");
//		lblSavedGaplists.setVerticalAlignment(JLabel.CENTER);
//		lblSavedGaplists.setHorizontalAlignment(JLabel.CENTER);
//		frame.getContentPane().add(lblSavedGaplists, NewClientLayout.SAVED_GAPLIST_LABEL);
		
		
//TODO	final JButton btnLoad = new JButton("Load");
//		btnLoad.setToolTipText("Loads the selected Gaplist.");
//		frame.getContentPane().add(btnLoad, NewClientLayout.LOAD_BUTTON);
		
//TODO	final JButton btnShow = new JButton("Show");
//		btnShow.setToolTipText("Shows the Content of the selected Gaplist.");
//		frame.getContentPane().add(btnShow, NewClientLayout.SHOW_BUTTON);
		
//TODO	final JButton btnRemove = new JButton("Remove");
//		btnRemove.setToolTipText("Removes the selected Gaplist.");
//		frame.getContentPane().add(btnRemove, NewClientLayout.REMOVE_BUTTON);	

//TODO	final JButton btnCreate = new JButton("Create");
//		btnCreate.setToolTipText("Click here to create a Gaplist with the Name in the Textfield on the right.");
//		frame.getContentPane().add(btnCreate, NewClientLayout.CREATE_BUTTON);
		
//TODO	JTextField textName = new JTextField();
//		textName.addMouseListener(new PopClickListener(textName));
//		frame.getContentPane().add(textName, NewClientLayout.GAPLIST_TEXT);
//		textName.setColumns(10);

		final JButton btnVote = new JButton("Vote");
		btnVote.setToolTipText("Click here to vote for the selected Song.");
		frame.getContentPane().add(btnVote, NewClientLayout.VOTE_BUTTON);
		
//TODO	final JButton btnRemoveVote = new JButton("Remove Vote");
//		btnRemoveVote.setToolTipText("Click here to remove your Vote.");
//		frame.getContentPane().add(btnRemoveVote, NewClientLayout.REMOVE_VOTE_BUTTON);
		
//TODO	final JButton btnRemoveAllVotes = new JButton("Remove all Votes");
//		btnRemoveAllVotes.setToolTipText("Click here to remove all Votes.");
//		frame.getContentPane().add(btnRemoveAllVotes, NewClientLayout.REMOVE_ALL_VOTES_BUTTON);
		
		JMenuBar menuBar = new JMenuBar();
		JMenu menuServer = new JMenu("Server");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuGaplist = new JMenu("Gaplist");
		JMenu menuWishlist = new JMenu("Wishlist");
		JMenu menuTrack = new JMenu("Track");
		
		
		/***************Server Menu*********************/
		JMenuItem menuDisconnect = new JMenuItem("Disconnect");
		JMenuItem menuDebug = new JMenuItem("Debug-Mode");
		menuDisconnect.setAccelerator(KeyStroke.getKeyStroke('d'));
		menuDebug.setAccelerator(KeyStroke.getKeyStroke('b'));
		menuDisconnect.addActionListener((ActionEvent ae) -> {wrapper.close();});
		menuDebug.addActionListener((ActionEvent ae) -> {collector.showDebugWindow();});
		
		menuServer.add(menuDisconnect);
		menuServer.add(menuDebug);
		
		/*****************Edit Menu***********************/
		JMenuItem menuOptions = new JMenuItem("Preferences");
		menuOptions.addActionListener((ActionEvent ae) -> {options.show();});

		menuEdit.add(menuOptions);
		
		/****************Track Menu*************************/
		JMenuItem menuSkip = new JMenuItem("Skip");
		JMenuItem menuSeekBackwards = new JMenuItem("Seek Backwards");
		JMenuItem menuSeekForward = new JMenuItem("Seek Forward");
		JMenuItem menuPlayPause = new JMenuItem("Pause");
		JMenuItem menuCopyLink = new JMenuItem("Copy Link");
		
		menuSkip.setAccelerator(KeyStroke.getKeyStroke('s'));
		menuSeekBackwards.setAccelerator(KeyStroke.getKeyStroke('b'));
		menuSeekForward.setAccelerator(KeyStroke.getKeyStroke('f'));
		menuPlayPause.setAccelerator(KeyStroke.getKeyStroke('p'));
		menuCopyLink.setAccelerator(KeyStroke.getKeyStroke('c'));
		
		menuSkip.addActionListener((ActionEvent ae) -> {skip();});
		menuSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		menuSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		menuPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		menuCopyLink.addActionListener((ActionEvent ae) -> {new util.TextTransfer().setClipboardContents(currentURL);});
		
		menuTrack.add(menuCopyLink);
		menuTrack.add(menuSkip);
		menuTrack.add(menuSeekBackwards);
		menuTrack.add(menuSeekForward);
		menuTrack.add(menuPlayPause);
		
		/********************Gaplist Menu*******************/
		JMenuItem menuSaveGaplist = new JMenuItem("Save Gaplist");
		JMenuItem menuCreateGaplist = new JMenuItem("Create new Gaplist");
		JMenuItem menuDisplayGaplists = new JMenuItem("Display all Gaplists");
		menuSaveGaplist.setAccelerator(KeyStroke.getKeyStroke('s'));
		menuCreateGaplist.setAccelerator(KeyStroke.getKeyStroke('c'));
		menuDisplayGaplists.setAccelerator(KeyStroke.getKeyStroke('d'));
		menuSaveGaplist.addActionListener((ActionEvent ae) -> {saveGaplist();});
		menuCreateGaplist.addActionListener((ActionEvent ae) -> {new NewListWindow(wrapper, this).show();});
		menuDisplayGaplists.addActionListener((ActionEvent ae) -> {gaplistsWindow.show();});
		
		menuGaplist.add(menuSaveGaplist);
		menuGaplist.add(menuCreateGaplist);
		menuGaplist.add(menuDisplayGaplists);
		
		/**********************Wishlist Menu*******************/
		JMenuItem menuRemoveVote = new JMenuItem("Remove Vote");
		JMenuItem menuRemoveAllVotes = new JMenuItem("Remove all Votes");
		menuRemoveVote.setAccelerator(KeyStroke.getKeyStroke('r'));
		menuRemoveAllVotes.setAccelerator(KeyStroke.getKeyStroke('v'));
		menuRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
		menuRemoveAllVotes.addActionListener((ActionEvent ae) -> {removeAllVotes();});
		
		menuWishlist.add(menuRemoveVote);
		menuWishlist.add(menuRemoveAllVotes);
		
		/*****************Finishing Menu Creation*************/
		menuBar.add(menuServer);
		menuBar.add(menuEdit);
		menuBar.add(menuTrack);
		menuBar.add(menuGaplist);
		menuBar.add(menuWishlist);
		menuBar.setBackground(Color.white);
		
		frame.getContentPane().add(menuBar, NewClientLayout.MENU_BAR);
		
		gaplistsWindow = new DisplayGaplistsWindow(wrapper, this, gaplists);
		
		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a Link here", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		
		wrapper.getCurrentPlaybackStatus((String[] s) -> {	if (s[0].equals("true")) {
																btnPlayPause.setToolTipText("Click here to Pause the Track.");
															//	btnPlayPause.setIcon(pauseIcon);
																btnPlayPause.setText("Pause");
															}
															else {
																btnPlayPause.setToolTipText("Click here to resume the Track");
															//	btnPlayPause.setIcon(playIcon);
																btnPlayPause.setText("Play");
															}
														});
	
//		btnDisconnect.addActionListener((ActionEvent ae)->{onDisconnect();});
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText(), rdbtnWishlist.isSelected(), chckbxInfront.isSelected(), txtLink);});
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
	//	btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
//		btnLoad.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0)loadGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
//		btnShow.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) showGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
//		btnRemove.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) removeGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
//		btnCreate.addActionListener((ActionEvent ae) -> {createGaplist(textName.getText());});
		btnVote.addActionListener((ActionEvent ae) -> {vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0)).getSelectedRow());});
//		btnRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
//		btnRemoveAllVotes.addActionListener((ActionEvent ae) -> {removeAllVotes();});
//		btnDebugMode.addActionListener((ActionEvent ae) -> {collector.showDebugWindow();});
		long end = System.currentTimeMillis();
		util.IO.println(this, "Frame Constructed in " + (end-start) + " ms");
	}
	
	/**
	 * The Renderer for the Table Cells in the Gaplist-Pane.
	 * @author Haeldeus
	 * @version 1.0
	 */
//	public class TableRenderer extends DefaultTableCellRenderer {
//
//	    /**
//		 * The serial Version UID.
//		 */
//		private static final long serialVersionUID = 1386922222679555490L;
//
//		@Override
//	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//	    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//	    	switch (gaplist[row].getParseStatus()){
//	    		case PARSED: c.setBackground(Color.WHITE); break;
//	    		case PARSING: c.setBackground(Color.YELLOW);break;
//	    		case NOT_PARSED: c.setBackground(Color.LIGHT_GRAY); break;
//	    		default: c.setBackground(Color.RED); break;
//	    	}
//	    	if (isSelected && c.getBackground() != Color.RED) {
//	    		c.setBackground(table.getSelectionBackground());
//	    	}
//	        return c;
//	    }
//	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		showFail("Count of Gaplists changed");
		this.gaplists = gapLists;
	//	new SetSavedGaplistsTask(frame, gapLists, oldSavedGaplistPane, this).execute();
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		showFail("Gaplist changed");
		lblGaplistName.setText("Gaplist - " + gapListName);
	}

	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		showFail("Gaplist updated");
	//	setGaplist(songs);
		new SetGaplistTask(gaplist, songs, lblNoGaplist, wrapper, frame, oldGaplistPane, this).execute();
	}

	@Override
	public void onPauseResumeNotify(boolean isPlaying) {
		if (isPlaying)
			showFail("Track resumed");
		else
			showFail("Track paused");
		pauseResume(isPlaying);
	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		new SetWishlistTask(wishlist, songs, lblNoWishlist, wrapper, frame, oldPane, this).execute();
		showFail("Wishlist updated");
		//	setWishlist(songs);
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		showFail("Playing next Track");
		setNowPlaying(title);
		setNextTrack();
		this.currentURL = url;
	}

	@Override
	public void onDisconnect() {
		collector.disconnect();
	}

	/**
	 * The Method, that is called, whenever the {@link SetGaplistTask} is finished. Is called 
	 * to update the references in this class.
	 * @param gaplist	The new Gaplist as an Array of {@link Song}s.
	 * @param lblNoGaplist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Gaplist.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param oldGaplistPane	The {@link JScrollPane}, that displays the Gaplist as a table.
	 * @since 1.6
	 */
	public void doneGaplistUpdate(Song[] gaplist, JLabel lblNoGaplist, JFrame frame, JScrollPane oldGaplistPane) {
		this.gaplist = gaplist;
		frame.remove(this.lblNoGaplist);
		frame.add(lblNoGaplist, NewClientLayout.COUNT_GAPLIST_LABEL);
		this.lblNoGaplist = lblNoGaplist;
		this.frame = frame;
		frame.remove(this.oldGaplistPane);
		this.oldGaplistPane = oldGaplistPane;
		this.frame.revalidate();
	}

	/**
	 * The Method, that is called whenever the {@link SetWishlistTask} is finished. Is called 
	 * to update the references in this class.
	 * @param wishlist	The new Wishlist as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Wishlist.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param oldPane	The {@link JScrollPane}, that displays the Wishlist as a table.
	 * @since 1.6
	 */
	public void doneWishlistUpdate(Song[] wishlist, JLabel lblNoWishlist, JFrame frame, JScrollPane oldPane) {
		this.wishlist = wishlist;
		frame.remove(this.lblNoWishlist);
		frame.add(lblNoWishlist, NewClientLayout.COUNT_WISHLIST_LABEL);
		this.lblNoWishlist = lblNoWishlist;
		this.frame = frame;
		frame.remove(this.oldPane);
		this.oldPane = oldPane;
		this.frame.revalidate();
	}
	
	/**
	 * The Method, that is called whenever the {@link SetContentTask} is finished. Is called
	 * to update the references in this class.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param contentPane	The {@link JScrollPane}, that displays the Content as a table.
	 * @since 1.7
	 */
	public void doneContentUpdate(JFrame frame, JScrollPane contentPane) {
		this.frame = frame;
	//	this.frame.setContentPane(frame.getContentPane());
		frame.remove(this.oldContentPane);
		this.oldContentPane = contentPane;
	//	this.frame.repaint();
		this.frame.revalidate();
	}
	
	/**
	 * The Method, that is called whenever the {@link SetSavedGaplistsTask} is finished. Is
	 * called to update the references in this class.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param savedListsPane	The {@link JScrollPane}, that displays the saved Lists as a 
	 * table.
	 * @since 1.7
	 */
	public void doneSavedListsUpdate(JFrame frame, JScrollPane savedListsPane) {
		this.frame = frame;
		frame.remove(this.oldSavedGaplistPane);
		this.oldSavedGaplistPane = savedListsPane;
	//	this.frame.repaint();
		this.frame.revalidate();
	}

	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		// Nothing to do here
	}

	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		//Nothing to do here
	}

	@Override
	public void onNewOutput(String output) {
		buffer.append(output+"\n");
		addNewMessage();
	}
	
	/**
	 * Adds a new Message to the Debug-TextArea.
	 * @since 1.8
	 */
	private void addNewMessage() {
		if (buffer.length() > 0){
			txtDebugs.append(buffer.toString());
			buffer.setLength(0);
			if (txtDebugs.getLineCount() > 200){
				int diff = txtDebugs.getLineCount() - 200;
				try {
					txtDebugs.replaceRange("", 0, txtDebugs.getLineEndOffset(diff - 1));
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar().getMaximum());
		}
	}
	
	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
}