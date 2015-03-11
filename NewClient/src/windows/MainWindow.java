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
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.JTableHeader;

import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import connection.Collector;

/**
 * The Main {@link Window}, that contains information transmitted by the Server, this Client 
 * is connected to.
 * @author Haeldeus
 * @version 1.3
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
	private JTextField textField;
	
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
		this.gaplist = gaplist;
		lblNoGaplist.setText(""+gaplist.length);
		createGaplistTable();
	}
	
	
	
	/**
	 * Sets the Wishlist to the given List and updates the Wishlist-Table.
	 * @param wishlist	The new Wishlist.
	 * @since 1.0
	 */
	public void setWishlist(Song[] wishlist) {
		this.wishlist = wishlist;
		lblNoWishlist.setText(""+wishlist.length);
		createWishlistTable();
		setNextTrack();
	}
	
	/**
	 * Sets the Gaplists to the given List and updates the Saved-Gaplists-Table.
	 * @param gaplists	The Gaplists on the Server.
	 * @since 1.2
	 */
	public void setGaplists(String[] gaplists) {
		this.gaplists = gaplists;
		createSavedGaplistsTable();
		setNextTrack();
	}
	
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
	private void deleteTrack(int index, JScrollPane list) {
		if (index >= 0) {
			if (wrapper.deleteFromList(gaplist[index]))
				showFail("Deleted the Track from the Gaplist");
			else
				showFail("Couldn't delete the Track from the Gaplist");
			try{Thread.sleep(100);} catch (Exception e) {}
			setSelectedGaplistIndex(index);
		}
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
		wrapper.getTitleFromGapList((String[] s) -> {createContentTable(s);}, name);
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
	 * Sets the Text of the PlayingTrackLabel to the given title.
	 * @param title	The title of the song, that is now playing.
	 * @since 1.2
	 */
	public void setNowPlaying(String title) {
		lblPlayingTrack.setText(title);
	}
	
	/**
	 * Sets the Text of the NextTrackLabel to the given title.
	 * @param title	The title of the next Song.
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
	 * Creates the Table, that displays the Wishlist and the Votes for each Song in it.
	 * @since 1.0
	 */
	private void createWishlistTable() {	
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
	 * Creates the Table, that contains the Gaplist.
	 * @since 1.1
	 */
	private void createGaplistTable() {
		if (oldGaplistPane != null)
			frame.getContentPane().remove(oldGaplistPane);
		
		String[] columns = {"Gaplist:"};
		
		String[][] data = new String[gaplist.length][1];
		
		for (int i = 0; i < gaplist.length; i++)
			data[i][0] = gaplist[i].getName();
		
		JTable table = new JTable(data, columns) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String [] columnToolTips = {"The Name of the Song in the Gaplist"};

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
		JScrollPane gaplistPane = new JScrollPane(table);
		gaplistPane.setBounds(10, 328, 250, 102);
		frame.getContentPane().add(gaplistPane);
		oldGaplistPane = gaplistPane;
	}
	
	/**
	 * Creates a Table with all saved Gaplists in it.
	 * @since 1.2
	 */
	private void createSavedGaplistsTable() {
		if (oldSavedGaplistPane != null)
			frame.getContentPane().remove(oldSavedGaplistPane);
		
		String[] columns = {"Gaplists:"};
		
		String[][] data = new String[gaplists.length][1];
		
		for (int i = 0; i < gaplists.length; i++)
			data[i][0] = gaplists[i];
		
		JTable table = new JTable(data, columns) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String [] columnToolTips = {"The Name of the Gaplist"};

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
		JScrollPane gaplistsPane = new JScrollPane(table);
		gaplistsPane.setBounds(10, 528, 248, 102);
		frame.getContentPane().add(gaplistsPane);
		oldSavedGaplistPane = gaplistsPane;
	}
	
	/**
	 * Creates the Table with the Content of the to be shown Gaplist. If the Content is null, 
	 * an empty table will be build with nothing but the header in it.
	 * @param content	The Content of the Gaplist, that should be shown.
	 * @since 1.2
	 */
	private void createContentTable(String[] content) {
		if (oldContentPane != null)
			frame.getContentPane().remove(oldContentPane);
		
		String[] columns = {"Content:"};
		String[][] data = new String[0][1];
		
		if (content != null) {
			data = new String[content.length][1];
		
			for (int i = 0; i < content.length; i++)
				data[i][0] = content[i];
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			String [] columnToolTips = {"The Name of the Song in the selected Gaplist."};

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
		JScrollPane contentPane = new JScrollPane(table);
		contentPane.setBounds(320, 528, 248, 102);
		frame.getContentPane().add(contentPane);
		oldContentPane = contentPane;
	}
	
	/**
	 * Sets the SelectedIndex of gaplistList to the given index.
	 * @param index	The index of the new Selection.
	 * @since 1.1
	 */
	private void setSelectedGaplistIndex(int index) {
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
													showFail("Coudln't remove the Gaplist");
											  }, name);
	}
	
	private void createGaplist(String name) {
		if (name != null) {
			String newName = replaceSpecials(name);
			wrapper.switchToGapList((String[] s) -> {	if (s[0].equals("true"))
															showFail("Created a new Gaplist.");
														else
															showFail("Failed to create a new Gaplist.");
													}, newName);
		}
		else {
			showFail("Please enter a name first");
		}
	}
	
	/**
	 * Replaces all special Characters from the given String.
	 * @param regex The String to have all specials replaced.
	 * @return	The given String without special Characters.
	 * @since 1.2
	 */
	private String replaceSpecials(String regex) {
		regex = regex.replaceAll("ä", "ae");
		regex = regex.replaceAll("Ä", "ae");
		regex = regex.replaceAll("ü", "ue");
		regex = regex.replaceAll("Ü", "ue");
		regex = regex.replaceAll("ö", "oe");
		regex = regex.replaceAll("Ö", "oe");
		regex = regex.replaceAll("ß", "ss");
		return regex;
	}
	
	/**
	 * Votes for the Song at the given index.
	 * @param index	The index of the Song, that will be voted for.
	 * @since 1.3
	 */
	private void vote(int index) {
		wrapper.removeVote((String[] s)-> {});
		wrapper.voteSong((String[] s) -> {	if (s[0].equals("true"))
												showFail("Voted for the Song");
											else
												showFail("Couldn't vote for the Song");
											createWishlistTable();
										}, wishlist[index]);
	}
	
	/**
	 * Removes the Vote.
	 * @since 1.3
	 */
	private void remove() {
		wrapper.removeVote((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed your vote.");
												else
													showFail("Couldn't remove your vote.");
												createWishlistTable();
											});
	}
	
	/**
	 * Creates a new Frame.
	 * @return The created Frame.
	 * @since 1.0
	 * @wbp.parser.entryPoint
	 */
	private void constructFrame() {
		gaplist = wrapper.getGapList();
		wishlist = wrapper.getWishList();
		gaplists = wrapper.getAvailableGapLists();
		
		frame = new JFrame();
		frame.setSize(new Dimension(600, 700));
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
		
		
		createWishlistTable();
		createGaplistTable();
		
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
		
		createSavedGaplistsTable();
		createContentTable(null);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);
		
		JLabel lblSavedGaplists = new JLabel("Saved Gaplists");
		lblSavedGaplists.setBounds(10, 501, 250, 20);
		lblSavedGaplists.setVerticalAlignment(JLabel.CENTER);
		lblSavedGaplists.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblSavedGaplists);
		
		
		JButton btnLoad = new JButton("Load");
		btnLoad.setBounds(10, 637, 75, 23);
		btnLoad.setToolTipText("Loads the selected Gaplist.");
		frame.getContentPane().add(btnLoad);
		
		JButton btnShow = new JButton("Show");
		btnShow.setBounds(95, 637, 75, 23);
		btnShow.setToolTipText("Shows the Content of the selected Gaplist.");
		frame.getContentPane().add(btnShow);
		
		JButton btnRemove = new JButton("Remove");
		btnRemove.setBounds(180, 637, 80, 23);
		btnRemove.setToolTipText("Removes the selected Gaplist.");
		frame.getContentPane().add(btnRemove);	

		JButton btnCreate = new JButton("Create");
		btnCreate.setBounds(320, 637, 80, 23);
		btnCreate.setToolTipText("Click here to create a Gaplist with the Name in the Textfield on the right.");
		frame.getContentPane().add(btnCreate);
		
		textField = new JTextField();
		textField.setBounds(410, 637, 158, 23);
		frame.getContentPane().add(textField);
		textField.setColumns(10);

		JButton btnVote = new JButton("Vote");
		btnVote.setBounds(320, 437, 120, 23);
		btnVote.setToolTipText("Click here to vote for the selected Song.");
		frame.getContentPane().add(btnVote);
		
		JButton btnRemoveVote = new JButton("Remove Vote");
		btnRemoveVote.setBounds(450, 437, 120, 23);
		btnRemoveVote.setToolTipText("Click here to remove your Vote.");
		frame.getContentPane().add(btnRemoveVote);
		
		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a Link here", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		
		wrapper.getCurrentPlaybackStatus((String[] s) -> {	if (s[0].equals("true")) {
																btnPlayPause.setToolTipText("Click here to Pause the Track.");
																btnPlayPause.setText("Pause");
															}
															else {
																btnPlayPause.setToolTipText("Click here to resume the Track");
																btnPlayPause.setText("Play");
															}
														});
	
		btnDisconnect.addActionListener((ActionEvent ae)->{collector.disconnect();});
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {wind(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {wind(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText(), rdbtnWishlist.isSelected(), chckbxInfront.isSelected(), txtLink);});
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), oldGaplistPane);});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnLoad.addActionListener((ActionEvent ae) -> {loadGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0)));});
		btnShow.addActionListener((ActionEvent ae) -> {showGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0)));});
		btnRemove.addActionListener((ActionEvent ae) -> {removeGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0)));});
		btnCreate.addActionListener((ActionEvent ae) -> {createGaplist(textField.getText());});
		btnVote.addActionListener((ActionEvent ae) -> {vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnRemove.addActionListener((ActionEvent ae) -> {remove();});
	}
}