package windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.table.JTableHeader;

import connection.Collector;
import util.PopClickListener;
import util.TablePopClickListener;
import util.TextFieldListener;
import util.layouts.LowClientLayout;
import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * The Implementation for a Desktop-Client without Admin-Permissions.
 * @author Haeldeus
 * @version 1.1
 */
public class LowClientWindow extends Window implements DefaultNotificationListener{

	/**
	 * The Frame.
	 */
	private final JFrame frame;
	
	/**
	 * The Wishlist as an Array of Songs (no real Songs, just an Instance of 
	 * {@link client.serverconnection.Song} with random Values).
	 */
	private Song[] wishlist;
	
	/**
	 * The Label, that displays possible Responses from the Server.
	 */
	private JLabel lblFail;
	
	/**
	 * The Label, that displays the Count of Tracks in the Wishlist.
	 */
	private JLabel lblCountWishlist;

	/**
	 * The Label, that displays the Name of the current Track.
	 */
	private JLabel lblNameCurrentTrack;
	
	/**
	 * The TextField for the Link.
	 */
	private JTextField txtLink;
	
	/**
	 * The {@link Collector}, that performs Off-Actions, like connecting and disconnecting 
	 * to/from the Server.
	 */
	private final Collector collector;
	
	/**
	 * The ServerConnection, that will send the Messages to the Server.
	 */
	private final ServerConnection wrapper;

	/**
	 * The Wishlist-ScrollPane.
	 */
	private JScrollPane oldPane;

	
	/**
	 * Creates a new LowClientWindow.
	 * @param c	The Collector, this Object is called from.
	 * @param frame	The Frame, that will be used.
	 * @param wrapper	The {@link ServerConnection} to the Server, that will be used to send 
	 * 					the Messages.
	 * @param wishlist	The Wishlist as an Array of Songs.
	 * @param ip	The Ip of the Server.
	 * @param iport	The Port of the Server as an Integer.
	 * @since 1.1
	 */
	public LowClientWindow(Collector c, JFrame frame, ServerConnection wrapper, Song[] wishlist, String ip, int iport) {
		this.frame = frame;
		collector = c;
		this.wrapper = wrapper;
		wrapper.addDefaultNotificationListener(this);
		this.wishlist = wishlist;
		frame.setTitle("JukePi - "+ip+":"+iport);
	}
	
	/**
	 * Constructs the Frame.
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
		wrapper.getCurrentSong((String[] s) -> {lblNameCurrentTrack.setText(s[0]);});	
		contentPane.add(lblNameCurrentTrack, LowClientLayout.NAME_CURRENT_TRACK_LABEL);
		
		txtLink = new JTextField("Enter a Link to a Video here");
		txtLink.addMouseListener(new TextFieldListener(new String[] {"Enter a Link to a Video here", }, txtLink));
		txtLink.addMouseListener(new PopClickListener(txtLink));
		contentPane.add(txtLink, LowClientLayout.LINK_TEXT);
		
		JButton btnAdd = new JButton("Add");
		contentPane.add(btnAdd, LowClientLayout.ADD_BUTTON);
		
		JButton btnDisc = new JButton("Disconnect");
		contentPane.add(btnDisc, LowClientLayout.DISCONNECT_BUTTON);
		
		JButton btnVote = new JButton("Vote");
		contentPane.add(btnVote, LowClientLayout.VOTE_BUTTON);
		
		JButton btnRemoveVote = new JButton("Remove Vote");
		contentPane.add(btnRemoveVote, LowClientLayout.REMOVE_BUTTON);
		
		frame.setContentPane(contentPane);
		
		JScrollPane wishlistPane = createWishlistTable();
		contentPane.add(wishlistPane, LowClientLayout.WISHLIST_PANE);
		
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText());});
		btnDisc.addActionListener((ActionEvent ae) -> {wrapper.close();});
		btnVote.addActionListener((ActionEvent ae) -> {vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0)).getSelectedRow());});
		btnRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
		util.IO.println(this, "Constructed Frame");
	}
	
	/**
	 * Removes the Vote of this Client.
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
	 * Adds the given Link to the end of the WishList.
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
	 * Votes for the Song in the selected Row.
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
	 * Creates the Table, that displays the WishList and the Votes for each Song in it.
	 * @since 1.0
	 */
	@Deprecated
	private synchronized JScrollPane createWishlistTable() {
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
        
        table.addMouseListener(new TablePopClickListener(table, wishlist));
        
        table.getColumnModel().getColumn(0).setMinWidth(210);
        table.getColumnModel().getColumn(1).setMaxWidth(40);
		JScrollPane wishlistPane = new JScrollPane(table);
		frame.getContentPane().add(wishlistPane, LowClientLayout.WISHLIST_PANE);
		if (notFirst)
			wishlistPane.getViewport().setViewPosition(p);
		oldPane = wishlistPane;
		return wishlistPane;
	}

	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}

	@Override
	public void show() {
		frame.setTitle("LowClientGUI");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		constructFrame();
		frame.setSize(frame.getContentPane().getWidth()+15, frame.getContentPane().getHeight()+40);
		frame.setVisible(true);
		frame.setMinimumSize(new Dimension(404,343));
	}

	@Override
	public void close() {
		frame.setVisible(false);
	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		this.wishlist = songs;
		lblCountWishlist.setText(""+wishlist.length);
		createWishlistTable();
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		lblNameCurrentTrack.setText(title);
	}

	@Override
	public void onDisconnect() {
		collector.disconnect();
	}
}
