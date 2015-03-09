package windows;

import util.TextFieldListener;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;

import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import connection.Collector;

/**
 * The Main {@link Window}, that contains information transmitted by the Server, this Client 
 * is connected to.
 * @author Haeldeus
 * @version 1.1
 */
public class MainWindow extends Window {
	
	/**
	 * The {@link Collector}, that will perform Actions with extern needed information.
	 */
	private Collector collector;
	
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
	private ServerConnection wrapper;

	/**
	 * The Gaplist, that contains all {@link Song}s in the Gaplist.
	 */
	private Song[] gaplist;
	
	/**
	 * The Wishlist, that contains all {@link Song}s in the Wishlist.
	 */
	private Song[] wishlist;
	
	/**
	 * The {@link DefaultListModel}, that contains all the Names of the Songs in the Gaplist.
	 */
	private DefaultListModel<String> gaplistModel;
	
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
	 * The ScrollPane, that contains the Old Wishlist-Table. Has to be stored to be able to 
	 * keep the table updated.
	 * @see JScrollPane
	 */
	private JScrollPane oldPane;
	
	/**
	 * The ScrollPane, that contains the List for the Model for the Gaplist.
	 * @see JScrollPane
	 */
	private JScrollPane gaplistPane;
	
	/**
	 * The List, that contains the Model for the Gaplist.
	 * @see JList
	 */
	private JList<String> gaplistList;
	
	/**
	 * The Constructor for the Main-Screen. Will set the parameters to their belonging 
	 * variables as well as instantiating a new {@link DefaultListModel} to store the Gaplist.
	 * @param collector	The {@link Collector}, that will perform Actions with extern needed 
	 * information.
	 * @param frame	The Frame, this Screen will display.
	 * @param wrapper	The {@link ServerConnection}, that will send the Messages.
	 * @param gaplist	The Gaplist as an Array of {@link Song}s.
	 * @param wishlist	The Wishlist as an Array of {@link Song}s.
	 * @since 1.0
	 */
	public MainWindow(Collector collector, JFrame frame, ServerConnection wrapper, Song[] gaplist, Song[] wishlist) {
		this.collector = collector;
		this.frame = frame;
		frame.getContentPane().removeAll();
		this.wrapper = wrapper;
		
		this.gaplist = gaplist;
		this.wishlist = wishlist;
		
		gaplistModel = new DefaultListModel<String>();
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
	 * Winds 30 seconds either forward or backward.
	 * @param forward	Determines, whether the Server should wind forward({@code true}) or 
	 * backward({@code false}).
	 * @see ServerConnection#seekForward(ResponseListener)
	 * @see ServerConnection#seekBackward(ResponseListener)
	 * @since 1.0
	 */
	private void wind(boolean forward) {
		if (forward)
			wrapper.seekForward((String[] s) -> {	if (s[0].equals("true")) 
														showFail("Successfully wound forward!");
													else
														showFail("Couldn't wind forward!");
												});
		else
			wrapper.seekBackward((String[] s) -> {	if (s[0].equals("true"))
														showFail("Successfully wound backwards!");
													else
														showFail("Couldn't wind backwards!");
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
	
	/**
	 * Sets the Gaplist to the given List and updates the Gaplist-Model
	 * @param gaplist	The new Gaplist.
	 * @since 1.0
	 */
	public void setGaplist(Song[] gaplist) {
		frame.getContentPane().remove(gaplistPane);
		
		this.gaplist = gaplist;
		gaplistModel.clear();
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
		lblNoGaplist.setText(""+gaplist.length);
		gaplistList = new JList<String>(gaplistModel);
		gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(10, 328, 248, 98);
		frame.getContentPane().add(gaplistPane);
	}
	
	/**
	 * Sets the Wishlist to the given List and updates the Wishlist-Table.
	 * @param wishlist	The new Wishlist.
	 * @since 1.0
	 */
	public void setWishlist(Song[] wishlist) {
		this.wishlist = wishlist;
		lblNoWishlist.setText(""+wishlist.length);
		createTable();
	}
	
	/**
	 * Moves the Song at the given index upwards in the Gaplist.
	 * @param index	The index of the Track to be moved.
	 * @param list	The List, that contains the Gaplist-Model.
	 * @see ServerConnection#setGapListTrackUp(ResponseListener, long)
	 * @since 1.0
	 */
	public void moveTrackUp(int index, JList<String> list) {
		if (index >=0)
			wrapper.setGapListTrackUp((String[] s)-> {	if (s[0].equals("true")) {
															showFail("Moved Track up.");
															try{Thread.sleep(100);}catch(Exception e) {}
															setSelectedIndex(index-1);
														}
														else {
															showFail("Couldn't move Track up.");
															try{Thread.sleep(100);}catch(Exception e) {}
															setSelectedIndex(index);
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
	public void moveTrackDown(int index, JList<String> list) {
		if (index >= 0)
			wrapper.setGapListTrackDown((String[] s) -> {	if (s[0].equals("true")) {
																showFail("Moved Track down.");
																try{Thread.sleep(100);}catch(Exception e) {}
																setSelectedIndex(index+1);
															}
															else {
																showFail("Couldn't move Track down");
																try{Thread.sleep(100);}catch(Exception e) {}
																setSelectedIndex(index);
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
	public void deleteTrack(int index, JList<String> list) {
		if (index >= 0) {
			if (wrapper.deleteFromList(gaplist[index]))
				showFail("Deleted the Track from the Gaplist");
			else
				showFail("Couldn't delete the Track from the Gaplist");
			list.setSelectedIndex(index);
		}
	}
	
	/**
	 * Saves the current Gaplist on the Server.
	 * @see ServerConnection#saveGapList(ResponseListener)
	 * @since 1.0
	 */
	public void saveGaplist() {
		wrapper.saveGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Saved Gaplist.");
												else
													showFail("Couldn't save the Gaplist.");
											});
	}
	
	/**
	 * Will be executed, when a Song was paused or resumed on the Server.
	 * @param isPlaying	Determines, if the Song is now playing ({@code true}) or paused 
	 * ({@code false}).
	 * @since 1.0
	 */
	public void pauseResume(boolean isPlaying) {
		if (isPlaying) {
			btnPlayPause.setText("Pause");
			btnPlayPause.setToolTipText("Click here to pause the Track.");
		}
		else {
			btnPlayPause.setText("Play");
			btnPlayPause.setToolTipText("Click here to resume the Track.");
		}
	}
	
	/**
	 * Will be executed, when an other Gaplist was loaded on the Server.
	 * @param gapListName	The Name of the new Gaplist.
	 * @since 1.0
	 */
	public void gaplistChanged(String gapListName) {
		lblGaplistName.setText("Gaplist - " + gapListName);
	}
	
	/**
	 * Sets the Text of the NextTrackLabel to the given title.
	 * @param title	The title of the next Song.
	 * @since 1.0
	 */
	public void setNextTrack(String title) {
		lblPlayingTrack.setText(title);
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
	}
	
	/**
	 * Creates the Table, that displays the Wishlist and the Votes for each Song in it.
	 * @since 1.0
	 */
	public void createTable() {	
		if(oldPane != null)
			frame.getContentPane().remove(oldPane);
		
		String[] columns = {"Song:", "Votes:"};
		
		String[][] data = new String[wishlist.length][2];
		
		for (int i = 0; i < wishlist.length; i++) {
			data[i][0] = wishlist[i].getName();
			data[i][1] = ""+wishlist[i].getVotes();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String [] columnToolTips = {"The Name of the Song", "The Votes for this Song"};

			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
				if (colIndex == 0)
					tip = ""+ getValueAt(rowIndex, colIndex);
				return tip;
			}
			
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;

					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return columnToolTips[realIndex];
					}
				};
            }
        };
        table.getColumnModel().getColumn(0).setMinWidth(210);
		JScrollPane wishlistPane = new JScrollPane(table);
		wishlistPane.setBounds(320,328,250,102);
		frame.getContentPane().add(wishlistPane);
		oldPane = wishlistPane;
	}
	
	/**
	 * Sets the SelectedIndex of gaplistList to the given index.
	 * @param index	The index of the new Selection.
	 * @since 1.1
	 */
	private void setSelectedIndex(int index) {
		gaplistList.setSelectedIndex(index);
	}
	
	/**
	 * Creates a new Frame.
	 * @return The created Frame.
	 * @since 1.0
	 * @wbp.parser.entryPoint
	 */
	public void constructFrame() {
		gaplist = wrapper.getGapList();
		wishlist = wrapper.getWishList();
		
		frame = new JFrame();
		frame.setSize(new Dimension(600, 500));
		frame.setTitle("JukePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		/*Delete till here*/		
		
		lblFail = new JLabel("");
		lblFail.setBounds(143, 278, 189, 14);
		frame.getContentPane().add(lblFail);
		
		
		JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplist.setBounds(10, 11, 123, 14);
		frame.getContentPane().add(lblGaplist);
		
		JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
		lblWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWishlist.setBounds(10, 36, 123, 14);
		frame.getContentPane().add(lblWishlist);
		
		lblNoGaplist = new JLabel(""+ gaplist.length);
		lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoGaplist.setBounds(143, 11, 68, 14);
		frame.getContentPane().add(lblNoGaplist);
		
		lblNoWishlist = new JLabel("" + wishlist.length);
		lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoWishlist.setBounds(143, 36, 46, 14);
		frame.getContentPane().add(lblNoWishlist);
		
		txtLink = new JTextField();
		txtLink.setBounds(10, 60, 362, 20);
		txtLink.setText("Insert a Link here.");
		frame.getContentPane().add(txtLink);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(10, 91, 62, 20);
		btnAdd.setToolTipText("Adds the YouTube-Link in the upper Textfield either to the Gaplist or the Wishlist, whatever is selected on the right.");
		frame.getContentPane().add(btnAdd);
		
		JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		rdbtnWishlist.setBounds(78, 90, 75, 23);
		frame.getContentPane().add(rdbtnWishlist);
		rdbtnWishlist.setSelected(true);
		
		JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		rdbtnGaplist.setBounds(155, 90, 75, 23);
		frame.getContentPane().add(rdbtnGaplist);
		
		JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNowPlaying.setBounds(10, 144, 68, 14);
		frame.getContentPane().add(lblNowPlaying);
		
		JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNextTrack.setBounds(10, 169, 68, 14);
		frame.getContentPane().add(lblNextTrack);
		
		lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlayingTrack.setBounds(88, 144, 244, 14);
		frame.getContentPane().add(lblPlayingTrack);
		wrapper.getCurrentTrackTitle((String[] s) -> {lblPlayingTrack.setText(s[0]);});		
		
		lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTrackNext.setBounds(88, 169, 244, 14);
		frame.getContentPane().add(lblTrackNext);
		
		Song[] wishlist = wrapper.getWishList();
		Song[] gaplist = wrapper.getGapList();
		
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
		
		collector.setLists(wishlist, gaplist);
		
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
		
		btnPlayPause = new JButton("Play");
		btnPlayPause.setBounds(140, 194, 120, 45);
		frame.getContentPane().add(btnPlayPause);
		
		JButton btnSeekBackwards = new JButton("<html><body>Wind<br>Backward</body></html>");
		btnSeekBackwards.setBounds(10, 194, 120, 45);
		btnSeekBackwards.setToolTipText("Click here to wind 30 seconds backward.");
		frame.getContentPane().add(btnSeekBackwards);
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.setBounds(450, 194, 120, 45);
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip);
		
		JButton btnSeekForward = new JButton("<html><body>Wind<br>Forward</body></html>");
		btnSeekForward.setBounds(320, 194, 120, 45);
		btnSeekForward.setToolTipText("Click here to wind 30 seconds forward.");
		frame.getContentPane().add(btnSeekForward);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(450, 7, 120, 23);
		btnDisconnect.setToolTipText("Click here to disconnect from the Server.");
		frame.getContentPane().add(btnDisconnect);
		
		JCheckBox chckbxInfront = new JCheckBox("Add in Front");
		chckbxInfront.setBounds(232, 90, 97, 23);
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of the list.");
		frame.getContentPane().add(chckbxInfront);
		
		
		createTable();

		gaplistList = new JList<String>(gaplistModel);
		gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(10, 328, 250, 102);
		frame.getContentPane().add(gaplistPane);
		
		lblGaplistName = new JLabel("");
		lblGaplistName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplistName.setBounds(10, 303, 250, 14);
		lblGaplistName.setVerticalAlignment(JLabel.CENTER);
		lblGaplistName.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblGaplistName);
		
		wrapper.getCurrentGapListName((String[] s) -> {lblGaplistName.setText("Gaplist - "+ s[0]);});
		
		JLabel lblWishlist2 = new JLabel("Wishlist");
		lblWishlist2.setHorizontalAlignment(JLabel.CENTER);
		lblWishlist2.setVerticalAlignment(JLabel.CENTER);
		lblWishlist2.setBounds(320, 303, 250, 14);
		frame.getContentPane().add(lblWishlist2);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(10, 437, 120, 23);
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		frame.getContentPane().add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(140, 437, 120, 23);
		btnSave.setToolTipText("Click here to save the current Gaplist on the Server.");
		frame.getContentPane().add(btnSave);
		
		JButton btnUp = new JButton("/\\");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		btnUp.setBounds(260, 341, 40, 25);
		frame.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("\\/");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		btnDown.setBounds(260, 392, 40, 25);
		frame.getContentPane().add(btnDown);
		
		JButton btnOpen = new JButton("Open...");
		btnOpen.setToolTipText("Click here to open saved Gaplists");
		btnOpen.setBounds(385, 437, 89, 23);
		frame.getContentPane().add(btnOpen);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);

		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a Link here", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		btnDisconnect.addActionListener((ActionEvent ae)->{collector.disconnect();});
		
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {wind(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {wind(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText(), rdbtnWishlist.isSelected(), chckbxInfront.isSelected(), txtLink);});
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(gaplistList.getSelectedIndex(), gaplistList);});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(gaplistList.getSelectedIndex(), gaplistList);});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(gaplistList.getSelectedIndex(), gaplistList);});
		}
}
