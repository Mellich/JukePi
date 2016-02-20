package windows;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;

import connection.Collector;
import util.PopClickListener;
import util.TextFieldListener;
import util.layouts.LowClientLayout;
import util.tasks.SetLowWishlistTask;
import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * <p>The Implementation for a Desktop-Client without Admin-Permissions.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #LowClientWindow(Collector, JFrame, ServerConnection, String, int)}:
 * 				The Constructor for this Window.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #doneWishlistUpdate(Song[], JLabel, JFrame, JScrollPane)}:
 * 				Will be called by the {@link SetLowWishlistTask}, after it updated the 
 * 				{@link #oldPane}. Will update the edited Fields to the given Values.</li>
 * 
 * 			<li>{@link #onDisconnect()}:
 * 				Will be called, when the Client was disconnected from the Server without the 
 * 				Users Permission (e.g. Loss of Connection). Will call 
 * 				{@link Collector#disconnect()}.</li>
 * 
 * 			<li>{@link #onNextTrackNotify(String, String, boolean)}:
 * 				Will be called, when a new Song is played on the Server. Will update the 
 * 				{@link #lblNameCurrentTrack}.</li>
 * 
 * 			<li>{@link #onWishListUpdatedNotify(Song[])}:
 * 				Will be called, when the Wishlist was updated. Will execute a new 
 * 				{@link SetLowWishlistTask} to update the {@link #oldPane}.</li>
 * 
 * 			<li>{@link #setActive(boolean)}:
 * 				Sets the state of the Window, depending on the given {@code boolean}, either 
 * 				enabled or disabled.</li>
 * 
 * 			<li>{@link #show()}:
 * 				Sets the Window visible and enabled.</li>
 * 
 * 			<li>{@link #showFail(String)}:
 * 				Displays the given {@code String} on the Frame.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #add(String)}:
 * 				Adds the given String to the Wishlist. If it is a Link to a Song, it will be 
 * 				parsed and added to the Wishlist.</li>
 * 
 * 			<li>{@link #constructFrame()}:
 * 				Creates the Frame and places it'S Components on their belonging Spots.</li>
 * 
 * 			<li>{@link #removeVote()}:
 * 				Removes the Vote of the Client.</li>
 * 
 * 			<li>{@link #vote(int)}:
 * 				Votes for the Song with the given Index.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p> 
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p> 		
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #collector}:
 * 				The {@link Collector}, that opened this Window and provides additional 
 * 				Methods.</li>
 * 
 * 			<li>{@link #currentURL}:
 * 				The URL of the Song, that is currently playing.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #lblCountWishlist}:
 * 				The {@link JLabel}, that displays the amount of Tracks in the Wishlist.</li> 
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays Messages from the Server.</li>
 * 
 * 			<li>{@link #lblNameCurrentTrack}:
 * 				The {@link JLabel}, that displays the Name of the current Track.</li>
 * 
 * 			<li>{@link #oldPane}:
 * 				The {@link JScrollPane}, that displays the Wishlist as a Table.</li>
 * 
 * 			<li>{@link #txtLink}:
 * 				The {@link JTextField}, where the User can enter a Link to a Video.</li>
 * 
 * 			<li>{@link #wishlist}:
 * 				The Wishlist as an Array of {@link Song}s.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that is used to send/receive 
 * 				Message to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.2
 */
public class LowClientWindow extends Window implements DefaultNotificationListener{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays this Window</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] wishlist}</p>
	 * <p style="margin-left: 20px">The Wishlist as an Array of {@link Song}s.</p>
	 */
	private Song[] wishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that displays Messages from the 
	 * Server.</p>
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblCountWishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblCountWishlist}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that displays the Count of Tracks in 
	 * the Wishlist.</p>
	 */
	private JLabel lblCountWishlist;

	/**
	 * <p style="margin-left: 10px"><em><b>lblNameCurrentTrack</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNameCurrentTrack}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that displays the Name of the current 
	 * Track.</p>
	 */
	private JLabel lblNameCurrentTrack;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtLink</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextField txtLink}</p>
	 * <p style="margin-left: 20px">The TextField for the Link.</p>
	 * @see JTextField
	 */
	private JTextField txtLink;
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private final Collector collector}</p>
	 * <p style="margin-left: 20px">The {@link Collector}, that performs additional Actions, 
	 * like connecting and disconnecting to/from the Server.</p>
	 */
	private final Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private final ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send the Messages 
	 * to the Server.</p>
	 */
	private final ServerConnection wrapper;

	/**
	 * <p style="margin-left: 10px"><em><b>oldPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that displays the Wishlist as a 
	 * Table.</p>
	 */
	private JScrollPane oldPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>currentURL</b></em></p>
	 * <p style="margin-left: 20px">{@code private String currentURL}</p>
	 * <p style="margin-left: 20px">The URL of the Song, that is currently playing.</p>
	 */
	private String currentURL;

	
	/**
	 * <p style="margin-left: 10px"><em><b>LowClientWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public LowClientWindow(Collector, JFrame, 
	 * ServerConnection, String, int)}</p>
	 * <p style="margin-left: 20px">Creates a new LowClientWindow.</p>
	 * @param c	The Collector, this Object is called from.
	 * @param frame	The Frame, that will be used.
	 * @param wrapper	The {@link ServerConnection} to the Server, that will be used to send 
	 * 					the Messages.
	 * @param ip	The Ip of the Server.
	 * @param iport	The Port of the Server as an Integer.
	 * @since 1.1
	 */
	public LowClientWindow(Collector c, JFrame frame, ServerConnection wrapper, String ip, 
			int iport) {
		this.frame = frame;
		collector = c;
		this.wrapper = wrapper;
		wrapper.addDefaultNotificationListener(this);
		frame.setTitle("JukePi - "+ip+":"+iport);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame()}</p>
	 * <p style="margin-left: 20px">Constructs the Frame.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		Container contentPane = new Container();
		wishlist = wrapper.getWishList();
		contentPane.setLayout(new LowClientLayout());
		contentPane.setSize(new Dimension(400,400));
		
		JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
		contentPane.add(lblGaplist, LowClientLayout.GAPLIST_LABEL);
		
		JLabel lblCountGaplist = new JLabel();
		lblCountGaplist.setText("" + wrapper.getGapList().length);
		contentPane.add(lblCountGaplist, LowClientLayout.COUNT_GAPLIST_LABEL);
		
		JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
		contentPane.add(lblWishlist, LowClientLayout.WISHLIST_LABEL);
		
		lblCountWishlist = new JLabel();
		lblCountWishlist.setText("" + wrapper.getWishList().length);
		contentPane.add(lblCountWishlist, LowClientLayout.COUNT_WISHLIST_LABEL);
		
		JLabel lblWishlistName = new JLabel("Wishlist:");
		lblWishlistName.setHorizontalAlignment(JLabel.CENTER);
		contentPane.add(lblWishlistName, LowClientLayout.WISHLIST_SHOW_LABEL);
		
		lblFail = new JLabel();
		contentPane.add(lblFail, LowClientLayout.FAIL_LABEL);
		
		JLabel lblCurrentTrack = new JLabel("Current Track:");
		contentPane.add(lblCurrentTrack, LowClientLayout.CURRENT_TRACK_LABEL);
		
		lblNameCurrentTrack = new JLabel();
		Song current = wrapper.getCurrentSong();
		lblNameCurrentTrack.setText(current.getName());
		currentURL = current.getURL();
		contentPane.add(lblNameCurrentTrack, LowClientLayout.CURRENT_TRACKNAME_LABEL);
		
		txtLink = new JTextField("Enter a Link to a Video here");
		txtLink.addMouseListener(new TextFieldListener(new String[] {
				"Enter a Link to a Video here"}, txtLink));
		txtLink.addMouseListener(new PopClickListener(txtLink));
		contentPane.add(txtLink, LowClientLayout.LINK_TEXT);
		
		JButton btnRemoveVote = new JButton("Remove Vote");
		contentPane.add(btnRemoveVote, LowClientLayout.REMOVE_BUTTON);
		
		JButton btnAdd = new JButton("Add");
		contentPane.add(btnAdd, LowClientLayout.ADD_BUTTON);
		
		JButton btnDisc = new JButton("Disconnect");
		contentPane.add(btnDisc, LowClientLayout.DISCONNECT_BUTTON);
		
		JButton btnVote = new JButton("Vote");
		contentPane.add(btnVote, LowClientLayout.VOTE_BUTTON);
		
		/****************Menu Creation********************/
		JMenuBar menuBar = new JMenuBar();
		JMenu menuConnection = new JMenu("Connection");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuTrack = new JMenu("Track");
		JMenu menuWishlist = new JMenu("Wishlist");
		JMenu menuAbout = new JMenu("About");
		
		/***************Connection-Menu********************/
		JMenuItem menuDisconnect = new JMenuItem("Disconnect");
		menuDisconnect.setAccelerator(KeyStroke.getKeyStroke('d'));
		menuDisconnect.addActionListener((ActionEvent ae) -> {wrapper.close();});
		menuConnection.add(menuDisconnect);
		
		/****************Edit Menu************************/
		JMenuItem menuPreferences = new JMenuItem("Preferences");
		menuPreferences.setAccelerator(KeyStroke.getKeyStroke('p'));
		menuEdit.add(menuPreferences);
		
		/****************Track Menu**********************/
		JMenuItem menuCopy = new JMenuItem("Copy Link");
		menuCopy.setAccelerator(KeyStroke.getKeyStroke('c'));
		menuCopy.addActionListener((ActionEvent ae) -> {new util.TextTransfer().setClipboardContents(currentURL);});
		menuTrack.add(menuCopy);
		
		/***************Wishlist Menu********************/
		JMenuItem menuRemove = new JMenuItem("Remove Vote");
		menuRemove.setAccelerator(KeyStroke.getKeyStroke('r'));
		menuRemove.addActionListener((ActionEvent ae) -> {wrapper.removeVote();});
		menuWishlist.add(menuRemove);
		
		/**************About Menu***********************/
		JMenuItem menuVersion = new JMenuItem("Version");
		menuVersion.setAccelerator(KeyStroke.getKeyStroke('v'));
		menuVersion.addActionListener((ActionEvent ae) -> {new VersionWindow().show();});
		menuAbout.add(menuVersion);
		
		/*************Finishing Menu Creation**********/
		menuBar.add(menuConnection);
		menuBar.add(menuEdit);
		menuBar.add(menuTrack);
		menuBar.add(menuWishlist);
		menuBar.add(menuAbout);
		menuBar.setBackground(Color.white);
		
		contentPane.add(menuBar, LowClientLayout.MENU_BAR);
		
		frame.setContentPane(contentPane);
		
		oldPane = new JScrollPane();
		new SetLowWishlistTask(wishlist, lblCountWishlist, frame, oldPane, this).execute();
		
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText());});
		btnDisc.addActionListener((ActionEvent ae) -> {wrapper.close();});
		btnVote.addActionListener((ActionEvent ae) -> {
			vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0))
					.getSelectedRow());
			});
		btnRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
		util.IO.println(this, "Constructed Frame");
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>removeVote</b></em></p>
	 * <p style="margin-left: 20px">{@code private void removeVote()}</p>
	 * <p style="margin-left: 20px">Removes the Vote of this Client.</p>
	 * @since 1.1
	 */
	private void removeVote() {
		wrapper.removeVote((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed the Vote.");
												else
													showFail("Couldn't remove the Vote");
											});
	}

	/**
	 * <p style="margin-left: 10px"><em><b>add</b></em></p>
	 * <p style="margin-left: 20px">{@code private void add(String)}</p>
	 * <p style="margin-left: 20px">Adds the given Link to the end of the WishList.</p>
	 * @param url	The URL of the Video.
	 * @since 1.1
	 */
	private void add(String url) {
		wrapper.addToList((String[] s) -> {
			if (s[0].equals("true"))
				showFail("Added the Song to the List");
			else
				showFail("Couldn't add the Song to the List");
			txtLink.setText("Enter a Link to a Video here");
		}, url, true, true);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>vote</b></em></p>
	 * <p style="margin-left: 20px">{@code private void vote(int)}</p>
	 * <p style="margin-left: 20px">Votes for the Song in the selected Row.</p>
	 * @param selectedRow	The selected Row.
	 * @since 1.1
	 */
	private void vote(int selectedRow) {
		wrapper.voteSong((String[] s) -> {	if (s[0].equals("true"))
												showFail("Voted for the Song.");
											else
												showFail("Couldn't Vote for the Song.");
											}, wishlist[selectedRow]);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Shows {@link #lblFail} with the given Text on the {@link 
	 * #frame}. If an empty String or {@code null} is given as Parameter, the Label will be 
	 * displayed, but without any Text.</p>
	 * @param text	The text, that will be displayed.
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #constructFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		frame.setTitle("LowClientGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		constructFrame();
		frame.setSize(frame.getContentPane().getWidth()+15, 
				frame.getContentPane().getHeight()+40);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(404,343));
	}

	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets {@link #frame} invisible and disabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	@Override
	public void close() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onWishListUpdatedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onWishListUpdatedNotify(Song[])}</p>
	 * <p style="margin-left: 20px">Is called, whenever the Wishlist was updated. Will update 
	 * the {@link #wishlist} and {@link #lblCountWishlist} and executes a new 
	 * {@link SetLowWishlistTask} to update {@link #oldPane}.</p>
	 * @since 1.1
	 */
	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		this.wishlist = songs;
		lblCountWishlist.setText(""+wishlist.length);
		new SetLowWishlistTask(songs, lblCountWishlist, frame, oldPane, this).execute();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onNextTrackNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onNextTrackNotify(String, String, 
	 * boolean)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever a new Track is played on the 
	 * Server. Will update {@link #lblNameCurrentTrack} to the given {@code title} and updates 
	 * {@link #currentURL}.</p>
	 * @since 1.1
	 */
	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		lblNameCurrentTrack.setText(title);
		currentURL = wrapper.getCurrentSong().getURL();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onDisconnect</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onDisconnect()}</p>
	 * <p style="margin-left: 20px">Will be called, when the Client was disconnected from the 
	 * Server, without the Permission of the User (e.g. Loss of Connection). Will call 
	 * {@link Collector#disconnect()}.</p>
	 * @since 1.1
	 */
	@Override
	public void onDisconnect() {
		collector.disconnect();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>setActive</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setActive(boolean)}</p>
	 * <p style="margin-left: 20px">Sets the State of the Window to the given State. Active, 
	 * if {@code state} is {@code true}, inactive if it is {@code false}.</p>
	 * @param state	The new State of the Window; active, if {@code true}, inactive else.
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 */
	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>doneWishlistUpdate</b></em></p>
	 * <p style="margin-left: 20px">{@code public void doneWishlistUpdate(Song[], JLabel, 
	 * JFrame, JScrollPane}</p>
	 * <p style="margin-left: 20px">Will be called, when the {@link SetLowWishlistTask} has 
	 * finished updating the {@link #oldPane}. Updates the edited Variables to revalidate the 
	 * Frame.</p>
	 * @param wishlist	The Wishlist as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * 						Wishlist.
	 * @param frame	The {@link JFrame}, that displays this Window.
	 * @param oldPane	The {@link JScrollPane}, that displays the Wishlist as a Table.
	 * @since 1.2
	 */
	public void doneWishlistUpdate(Song[] wishlist, JLabel lblNoWishlist, JFrame frame, 
			JScrollPane oldPane) {
		this.wishlist = wishlist;
		frame.remove(this.lblCountWishlist);
		frame.getContentPane().add(lblNoWishlist, LowClientLayout.COUNT_WISHLIST_LABEL);
		this.lblCountWishlist = lblNoWishlist;
		this.frame = frame;
		frame.remove(this.oldPane);
		this.oldPane = oldPane;
		this.frame.revalidate();
	}
}
