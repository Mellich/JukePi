package windows;

import util.TablePopClickListener;
import util.TextFieldListener;
import util.PopClickListener;
import util.layouts.ClientLayout;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;

import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import connection.Collector;

/**
 * The Main {@link Window}, that contains information transmitted by the Server, this Client 
 * is connected to.
 * @author Haeldeus
 * @version 1.4
 */
public class MainWindow extends Window {
	
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
	 * The Icon, that will be displayed instead of "Pause" as a String.
	 */
	private final ImageIcon playIcon = new ImageIcon("pause.png");
	
	/**
	 * The Icon, that will be displayed instead of "Play" as a String.
	 */
	private final ImageIcon pauseIcon = new ImageIcon("pause.png");
	
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
	public MainWindow(Collector collector, JFrame frame, ServerConnection wrapper, Song[] gaplist, Song[] wishlist) {
		this.collector = collector;
		this.frame = frame;
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
	
	/**
	 * Sets the Gaplist to the given List and updates the Gaplist-Model
	 * @param gaplist	The new Gaplist.
	 * @since 1.0
	 */
	public void setGaplist(Song[] gaplist) {
		this.gaplist = gaplist;
		lblNoGaplist.setText(""+gaplist.length);
		createGaplistTable();
		setNextTrack();
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
														createContentTable(songs);
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
	private synchronized void createWishlistTable() {
		Point p = new Point(-1,-1);
		boolean notFirst = false;
		if(oldPane != null) {
			notFirst = true;
			p = oldPane.getViewport().getViewPosition();
			frame.getContentPane().remove(oldPane);
		}
		
		String[] columns = {"Song:", "Votes:"};
		
		String[][] data = new String[wishlist.length][2];
		
		for (int i = 0; i < wishlist.length; i++) {
			data[i][0] = wishlist[i].getName();
			data[i][1] = ""+wishlist[i].getVotes();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTips for the TableHeaders.
			 */
			private String [] columnToolTips = {"The Name of the Song", "The Votes for this Song"};

			/**
			 * Returns the ToolTip for the Cell at the given Position of the Cursor.
			 * @param e	The MouseEvent.
			 * @return	The ToolTip for the Cell at the Cursor's Position.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
				if (colIndex == 0)
					tip = ""+ getValueAt(rowIndex, colIndex);
				return tip;
			}
			
			/**
			 * Returns, if the Cell at the given Position is editable.
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return the new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the given Cursor's Position.
					 * @param e	The MouseEvent.
					 * @return the ToolTip for the column at the Position of the Cursor.
					 */
					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return columnToolTips[realIndex];
					}
				};
            }
        };
        
        table.addMouseListener(new TablePopClickListener(table, wishlist, wrapper, this));
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		frame.getContentPane().add(wishlistPane, ClientLayout.WISHLIST_SCROLL);
		if (notFirst)
			wishlistPane.getViewport().setViewPosition(p);
		oldPane = wishlistPane;
	}
	
	/**
	 * Creates the Table, that contains the Gaplist.
	 * @since 1.1
	 */
	private synchronized void createGaplistTable() {
		Point p = new Point(-1,-1);
		boolean notFirst = false;
		
		if (oldGaplistPane != null) {
			notFirst = true;
			p = oldGaplistPane.getViewport().getViewPosition();
			frame.getContentPane().remove(oldGaplistPane);
		}
		
		String[] columns = {"Gaplist:"};
		
		String[][] data = new String[gaplist.length][1];
		
		for (int i = 0; i < gaplist.length; i++) {
			data[i][0] = gaplist[i].getName();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTip for the column.
			 */
			private String [] columnToolTips = {"The Name of the Song in the Gaplist"};

			/**
			 * Returns the ToolTip for the Cell at the Cursor's Position.
			 * @param e	The MouseEvent.
			 * @return The ToolTip for the Cell at the Position of the Cursor.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
				
				if (colIndex == 0) {
					switch (gaplist[rowIndex].getParseStatus()){
						case PARSED: tip = "Parsed - "; break;
						case NOT_PARSED: tip = "Not Parsed - "; break;
						default: tip = "Error while parsing: Check the URL! - "; break;
					}
					tip = tip.concat(""+ getValueAt(rowIndex, colIndex));
				}
				return tip;
			}
			
			/**
			 * Returns, if the Cell at the given index is editable.
			 * @param row	The row-index of the Cell.
			 * @param column	The column-index of the Cell.
			 * @return false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return	The new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the Cursor's Position.
					 * @param e	The MouseEvent.
					 * @return	The ToolTip for the given column.
					 */
					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return columnToolTips[realIndex];
					}
				};
            }
        };
        
        table.addMouseListener(new TablePopClickListener(table, gaplist, wrapper, this));
        
        table.getColumnModel().getColumn(0).setCellRenderer(new TableRenderer());
        
		JScrollPane gaplistPane = new JScrollPane(table);
		frame.getContentPane().add(gaplistPane, ClientLayout.GAPLIST_SCROLL);
		
		if (notFirst) 
			gaplistPane.getViewport().setViewPosition(p);
		oldGaplistPane = gaplistPane;
	}
	
	/**
	 * Creates a Table with all saved Gaplists in it.
	 * @since 1.2
	 */
	private synchronized void createSavedGaplistsTable() {
		if (oldSavedGaplistPane != null)
			frame.getContentPane().remove(oldSavedGaplistPane);
		
		String[] columns = {"Gaplists:"};
		
		String[][] data = new String[gaplists.length][1];
		
		for (int i = 0; i < gaplists.length; i++)
			data[i][0] = gaplists[i];
		
		JTable table = new JTable(data, columns) {
			/**
			 * The serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The Tooltip of the column.
			 */
			private String [] columnToolTips = {"The Name of the Gaplist"};

			/**
			 * Returns the ToolTip for the Cell at the Position of the Cursor.
			 * @param e	The MouseEvent.
			 * @return	The ToolTip for the Cell at the Cursor's Position.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
				if (colIndex == 0)
					tip = ""+ getValueAt(rowIndex, colIndex);
				return tip;
			}
			
			/**
			 * Returns, if the Cell at the Row and column is editable.
			 * @param row	The row index of the Cell.
			 * @param column The column index of the Cell.
			 * @return	false as default value, since these Cells shouldn't be edited.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return The new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the Cursor's Position
					 * @param e	The MouseEvent.
					 * @return The ToolTip for the column at the given Position of the Cursor.
					 */
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
		frame.getContentPane().add(gaplistsPane, ClientLayout.SAVED_GAPLIST_SCROLL);
		oldSavedGaplistPane = gaplistsPane;
	}
	
	/**
	 * Creates the Table with the Content of the to be shown Gaplist. If the Content is null, 
	 * an empty table will be build with nothing but the header in it.
	 * @param content	The Content of the Gaplist, that should be shown.
	 * @since 1.2
	 */
	private synchronized void createContentTable(Song[] content) {
		if (oldContentPane != null)
			frame.getContentPane().remove(oldContentPane);
		
		String[] columns = {"Content:"};
		String[][] data = new String[0][1];
		
		if (content != null) {
			data = new String[content.length][1];
		
			for (int i = 0; i < content.length; i++)
				data[i][0] = content[i].getName();
		}
		
		JTable table = new JTable(data, columns) {
			/**
			 * The Serial Version ID.
			 */
			private static final long serialVersionUID = 1L;
			
			/**
			 * The ToolTip for the column.
			 */
			private String [] columnToolTips = {"The Name of the Song in the selected Gaplist."};

			/**
			 * Returns the ToolTip of the Cell at the Cursor's Position.
			 * @param e	The MouseEvent.
			 * @return The ToolTip of the Cell at the Position of the Cursor.
			 */
			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
				if (colIndex == 0)
					tip = ""+ getValueAt(rowIndex, colIndex);
				return tip;
			}
			
			/**
			 * Returns, if the Cell at the given index is editable.
			 * @param row	The row-Index.
			 * @param column	The column-Index.
			 * @return	false by default, as these Cells shouldn't be editable.
			 */
			public boolean isCellEditable(int row, int column){  
				return false;  
			}
	
			/**
			 * Creates a new TableHeader.
			 * @return The new TableHeader.
			 */
			protected JTableHeader createDefaultTableHeader() {
				return new JTableHeader(columnModel) {
					/**
					 * The Serial Version ID.
					 */
					private static final long serialVersionUID = 1L;

					/**
					 * Returns the ToolTip for the column at the Cursor's Position.
					 * @param e	The MouseEvent.
					 * @return	The ToolTip for the column at the Position of the Cursor.
					 */
					public String getToolTipText(MouseEvent e) {
						java.awt.Point p = e.getPoint();
						int index = columnModel.getColumnIndexAtX(p.x);
						int realIndex = columnModel.getColumn(index).getModelIndex();
						return columnToolTips[realIndex];
					}
				};
            }
        };
        table.addMouseListener(new TablePopClickListener(table, content));
		JScrollPane contentPane = new JScrollPane(table);
		frame.getContentPane().add(contentPane, ClientLayout.CONTENT_SCROLL);
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
													showFail("Couldn't remove the Gaplist");
											  }, name);
	}
	
	/**
	 * Creates a Gaplist with the given Name.
	 * @param name	The Name of the new Gaplist.
	 * @since 1.2
	 */
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
		regex = regex.replaceAll("�", "ae");
		regex = regex.replaceAll("�", "ae");
		regex = regex.replaceAll("�", "ue");
		regex = regex.replaceAll("�", "ue");
		regex = regex.replaceAll("�", "oe");
		regex = regex.replaceAll("�", "oe");
		regex = regex.replaceAll("�", "ss");
		return regex;
	}
	
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
											createWishlistTable();
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
		long start = System.currentTimeMillis();
		gaplist = wrapper.getGapList();
		wishlist = wrapper.getWishList();
		gaplists = wrapper.getAvailableGapLists();
		
		frame = new JFrame();
		frame.setTitle("JukePi");
		frame.setSize(new Dimension(620,700));
		frame.getContentPane().setLayout(new ClientLayout());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setMinimumSize(new Dimension(617,695));
		/*Delete till here*/		
		
		lblFail = new JLabel("");
		frame.getContentPane().add(lblFail, ClientLayout.FAIL_LABEL);
		
		
		final JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblGaplist, ClientLayout.GAPLIST_LABEL);
		
		final JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
		lblWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblWishlist, ClientLayout.WISHLIST_LABEL);
		
		lblNoGaplist = new JLabel(""+ gaplist.length);
		lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoGaplist, ClientLayout.COUNT_GAPLIST_LABEL);
		
		lblNoWishlist = new JLabel("" + wishlist.length);
		lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoWishlist, ClientLayout.COUNT_WISHLIST_LABEL);
		
		txtLink = new JTextField("Insert a Link here.");
		txtLink.addMouseListener(new PopClickListener(txtLink));
		frame.getContentPane().add(txtLink, ClientLayout.LINK_TEXT);
		
		final JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Adds the YouTube-Link in the upper Textfield either to the Gaplist or the Wishlist, whatever is selected on the right.");
		frame.getContentPane().add(btnAdd, ClientLayout.ADD_BUTTON);
		
		final JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		frame.getContentPane().add(rdbtnWishlist, ClientLayout.WISHLIST_RADIO);
		rdbtnWishlist.setSelected(true);
		
		final JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		frame.getContentPane().add(rdbtnGaplist, ClientLayout.GAPLIST_RADIO);
		
		final JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNowPlaying, ClientLayout.NOW_PLAYING_LABEL);
		
		final JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNextTrack, ClientLayout.NEXT_TRACK_LABEL);
		
		lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblPlayingTrack, ClientLayout.NAME_NOW_PLAYING_LABEL);
		wrapper.getCurrentTrackTitle((String[] s) -> {lblPlayingTrack.setText(s[0]);});		
		
		lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblTrackNext, ClientLayout.NAME_NEXT_TRACK_LABEL);
		
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
		frame.getContentPane().add(btnPlayPause, ClientLayout.PLAY_PAUSE_BUTTON);
		
		final JButton btnSeekBackwards = new JButton("<html><body>Seek<br>Backward</body></html>");
		btnSeekBackwards.setToolTipText("Click here to seek 30 seconds backward.");
		frame.getContentPane().add(btnSeekBackwards, ClientLayout.SEEK_BACK_BUTTON);
		
		final JButton btnSkip = new JButton("Skip");
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip, ClientLayout.SKIP_BUTTON);
		
		final JButton btnSeekForward = new JButton("<html><body>Seek<br>Forward</body></html>");
		btnSeekForward.setToolTipText("Click here to seek 30 seconds forward.");
		frame.getContentPane().add(btnSeekForward, ClientLayout.SEEK_FORWARD_BUTTON);
		
		final JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setToolTipText("Click here to disconnect from the Server.");
		frame.getContentPane().add(btnDisconnect, ClientLayout.DISCONNECT_BUTTON);
		
		final JCheckBox chckbxInfront = new JCheckBox("Add in Front");
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of the list.");
		frame.getContentPane().add(chckbxInfront, ClientLayout.FRONT_CHECK);
		
		
		createWishlistTable();
		createGaplistTable();
		
		lblGaplistName = new JLabel("");
		lblGaplistName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplistName.setVerticalAlignment(JLabel.CENTER);
		lblGaplistName.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblGaplistName, ClientLayout.GAPLIST_NAME_LABEL);
		
		wrapper.getCurrentGapListName((String[] s) -> {lblGaplistName.setText("Gaplist - "+ s[0]);});
		
		final JLabel lblWishlist2 = new JLabel("Wishlist");
		lblWishlist2.setHorizontalAlignment(JLabel.CENTER);
		lblWishlist2.setVerticalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblWishlist2, ClientLayout.WISHLIST_SHOW_LABEL);
		
		final JButton btnDelete = new JButton("Delete");
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		frame.getContentPane().add(btnDelete, ClientLayout.DELETE_BUTTON);
		
		final JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Click here to save the current Gaplist on the Server.");
		frame.getContentPane().add(btnSave, ClientLayout.SAVE_BUTTON);
		
		final JButton btnUp = new JButton("/\\");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		frame.getContentPane().add(btnUp, ClientLayout.TRACK_UP_BUTTON);
		
		final JButton btnDown = new JButton("\\/");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		frame.getContentPane().add(btnDown, ClientLayout.TRACK_DOWN_BUTTON);
		
		createSavedGaplistsTable();
		createContentTable(null);
		
		final ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);
		
		final JLabel lblSavedGaplists = new JLabel("Saved Gaplists");
		lblSavedGaplists.setVerticalAlignment(JLabel.CENTER);
		lblSavedGaplists.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblSavedGaplists, ClientLayout.SAVED_GAPLIST_LABEL);
		
		
		final JButton btnLoad = new JButton("Load");
		btnLoad.setToolTipText("Loads the selected Gaplist.");
		frame.getContentPane().add(btnLoad, ClientLayout.LOAD_BUTTON);
		
		final JButton btnShow = new JButton("Show");
		btnShow.setToolTipText("Shows the Content of the selected Gaplist.");
		frame.getContentPane().add(btnShow, ClientLayout.SHOW_BUTTON);
		
		final JButton btnRemove = new JButton("Remove");
		btnRemove.setToolTipText("Removes the selected Gaplist.");
		frame.getContentPane().add(btnRemove, ClientLayout.REMOVE_BUTTON);	

		final JButton btnCreate = new JButton("Create");
		btnCreate.setToolTipText("Click here to create a Gaplist with the Name in the Textfield on the right.");
		frame.getContentPane().add(btnCreate, ClientLayout.CREATE_BUTTON);
		
		JTextField textName = new JTextField();
		textName.addMouseListener(new PopClickListener(textName));
		frame.getContentPane().add(textName, ClientLayout.GAPLIST_TEXT);
		textName.setColumns(10);

		final JButton btnVote = new JButton("Vote");
		btnVote.setToolTipText("Click here to vote for the selected Song.");
		frame.getContentPane().add(btnVote, ClientLayout.VOTE_BUTTON);
		
		final JButton btnRemoveVote = new JButton("Remove Vote");
		btnRemoveVote.setToolTipText("Click here to remove your Vote.");
		frame.getContentPane().add(btnRemoveVote, ClientLayout.REMOVE_VOTE_BUTTON);
		
		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a Link here", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		
		final JButton btnDebugMode = new JButton("Debug Mode");
		btnDebugMode.setToolTipText("Click here to open the Debug Window.");
		frame.getContentPane().add(btnDebugMode, ClientLayout.DEBUG_BUTTON);
		
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
	
		btnDisconnect.addActionListener((ActionEvent ae)->{collector.disconnect();});
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText(), rdbtnWishlist.isSelected(), chckbxInfront.isSelected(), txtLink);});
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnLoad.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0)loadGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
		btnShow.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) showGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
		btnRemove.addActionListener((ActionEvent ae) -> {if (((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow() >= 0) removeGaplist((String)(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getValueAt(((JTable) ((JViewport) oldSavedGaplistPane.getComponent(0)).getComponent(0)).getSelectedRow(), 0))); else showFail("Select a Gaplist first.");});
		btnCreate.addActionListener((ActionEvent ae) -> {createGaplist(textName.getText());});
		btnVote.addActionListener((ActionEvent ae) -> {vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
		btnDebugMode.addActionListener((ActionEvent ae) -> {collector.showDebugWindow();});
		long end = System.currentTimeMillis();
		System.out.println(end-start);
	}
	
	/**
	 * The Renderer for the Table Cells in the Gaplist-Pane.
	 * @author Haeldeus
	 * @version 1.0
	 */
	private class TableRenderer extends DefaultTableCellRenderer {

	    /**
		 * The serial Version UID.
		 */
		private static final long serialVersionUID = 1386922222679555490L;

		@Override
	    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	    	final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
	    	switch (gaplist[row].getParseStatus()){
	    		case PARSED: c.setBackground(Color.WHITE); break;
	    		case PARSING: c.setBackground(Color.YELLOW);break;
	    		case NOT_PARSED: c.setBackground(Color.LIGHT_GRAY); break;
	    		default: c.setBackground(Color.RED); break;
	    	}
	    	if (isSelected && c.getBackground() != Color.RED) {
	    		c.setBackground(table.getSelectionBackground());
	    	}
	        return c;
	    }
	}
}