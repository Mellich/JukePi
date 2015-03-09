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

public class MainWindow extends Window {
	
	private Collector c;
	
	/**
	 * The TextField that contains the YouTube-Link.
	 */
	private JTextField txtLink;
	
	/**
	 * The Label that displays possible Messages.
	 */
	private JLabel lblFail;
	
	/**
	 * The IP of the Server, the Client is connected to.
	 */
//	private String connectedIp;
	
	/**
	 * The Port of the Server, the Client is connected to.
	 */
//	private int connectedPort = -1;
	
	private JFrame frame;
	
	private ServerConnection wrapper;

	private Song[] gaplist;
	
	private Song[] wishlist;
	
	private DefaultListModel<String> wishlistModel;
	
	private DefaultListModel<String> gaplistModel;
	
	private DefaultListModel<String> votelistModel;
	
	private JButton btnPlayPause;
	
	private JLabel lblGaplistName;
	
	private JLabel lblPlayingTrack;
	
	private JLabel lblTrackNext;
	
	private JLabel lblNoGaplist;
	
	private JLabel lblNoWishlist;
	
	private JScrollPane oldPane;
	
	public MainWindow(Collector c, JFrame frame, ServerConnection wrapper, Song[] gaplist, Song[] wishlist) {
		this.c = c;
		this.frame = frame;
		frame.getContentPane().removeAll();
		this.wrapper = wrapper;
		
		this.gaplist = gaplist;
		this.wishlist = wishlist;
		
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		votelistModel = new DefaultListModel<String>();
		if (wishlist != null)
			for (Song s : wishlist) {
				wishlistModel.addElement(s.getName());
				votelistModel.addElement("" + s.getVotes());
			}
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
	
	public void setIpAndPort(String ip, int port) {
		frame.setTitle("JukePi - "+ip+":"+port);
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}
	
	private void skip() {
		wrapper.skip((String[] s) -> {	if (s[0].equals("true")) 
											showFail("Skipped Track successfully!"); 
										else 
											showFail("Couldn't skip the Track!");
									});
	}
	
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
	
	private void seek(boolean forward) {
		if (forward)
			wrapper.seekForward((String[] s) -> {	if (s[0].equals("true")) 
														showFail("Successfully seeked forward!");
													else
														showFail("Couldn't seek forward!");
												});
		else
			wrapper.seekBackward((String[] s) -> {	if (s[0].equals("true"))
														showFail("Successfully seeked backwards!");
													else
														showFail("Couldn't seek backwards!");
												});
	}
	
	private void add(String link, boolean toWishlist , boolean inFront, JTextField textfield) {
		if (!link.isEmpty()) {
			showFail("Pending Server...");
			wrapper.addToList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Track added!");
												else 
													showFail("Couldn't add the Track.");
												}, 
								link, toWishlist, !inFront);
		}
		else
			showFail("No valid link!");
	}
	
	public void setGaplist(Song[] gaplist) {
		this.gaplist = gaplist;
		gaplistModel.clear();
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
		lblNoGaplist.setText(""+gaplist.length);
	}
	
	public void setWishlist(Song[] wishlist) {
		this.wishlist = wishlist;
		lblNoWishlist.setText(""+wishlist.length);
		createTable();
	}
	
	public void moveTrackUp(int index, JList<String> list) {
		if (index >=0)
			wrapper.setGapListTrackUp((String[] s)-> {	if (s[0].equals("true")) {
															showFail("Moved Track up.");
															list.setSelectedIndex(index-1);
														}
														else {
															showFail("Couldn't move Track up.");
															list.setSelectedIndex(index);
														}
													}, gaplist[index].getTrackID());
	}
	
	public void moveTrackDown(int index, JList<String> list) {
		if (index >= 0)
			wrapper.setGapListTrackDown((String[] s) -> {	if (s[0].equals("true")) {
																showFail("Moved Track down.");
																list.setSelectedIndex(index+1);
															}
															else {
																showFail("Couldn't move Track down");
																list.setSelectedIndex(index);
															}
														}, gaplist[index].getTrackID());
	}
	
	public void deleteTrack(int index, JList<String> list) {
		if (index >= 0) {
			if (wrapper.deleteFromList(gaplist[index]))
				showFail("Deleted the Track from the Gaplist");
			else
				showFail("Couldn't delete the Track from the Gaplist");
			list.setSelectedIndex(index);
		}
	}
	
	public void saveGaplist() {
		wrapper.saveGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Saved Gaplist.");
												else
													showFail("Couldn't save the Gaplist.");
											});
	}
	
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
	
	public void gaplistChanged(String gapListName) {
		lblGaplistName.setText("Gaplist - " + gapListName);
	}
	
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
			String [] columnToolTips = {"The Song", "The Votes"};

			public String getToolTipText(MouseEvent e) {
				String tip = null;
				java.awt.Point p = e.getPoint();
				int rowIndex = rowAtPoint(p);
				int colIndex = columnAtPoint(p);
        
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
		wishlistPane.setBounds(300,328,250,98);
		frame.getContentPane().add(wishlistPane);
		oldPane = wishlistPane;
	}
	
	/**
	 * Creates a new Frame.
	 * @wbp.parser.entryPoint
	 * @return The created Frame.
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
		txtLink.setText("Insert a YouTube Link here.");
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
		
		for (Song s : wishlist) {
			wishlistModel.addElement(s.getName());
			votelistModel.addElement("" + s.getVotes());
		}
		
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
		
		c.setLists(wishlist, gaplist);
		
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
		
		btnPlayPause = new JButton("Play");
		btnPlayPause.setBounds(109, 194, 89, 45);
		frame.getContentPane().add(btnPlayPause);
		
		JButton btnSeekBackwards = new JButton("<html><body>Seek<br>Backwards</body></html>");
		btnSeekBackwards.setBounds(10, 194, 89, 45);
		btnSeekBackwards.setToolTipText("Click here to seek 30 seconds backwards.");
		frame.getContentPane().add(btnSeekBackwards);
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.setBounds(307, 194, 89, 45);
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip);
		
		JButton btnSeekForward = new JButton("<html><body>Seek<br>Forward</body></html>");
		btnSeekForward.setBounds(208, 194, 89, 45);
		btnSeekForward.setToolTipText("Click here to seek 30 seconds forward.");
		frame.getContentPane().add(btnSeekForward);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(360, 7, 114, 23);
		btnDisconnect.setToolTipText("Click here to disconnect from the Server.");
		frame.getContentPane().add(btnDisconnect);
		
		JCheckBox chckbxInfront = new JCheckBox("Add in Front");
		chckbxInfront.setBounds(232, 90, 97, 23);
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of the list.");
		frame.getContentPane().add(chckbxInfront);
		
		
		//TODO Old Edit Track window from here
		
		createTable();

		JList<String> gaplistList = new JList<String>(gaplistModel);
		JScrollPane gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(10, 328, 248, 98);
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
		lblWishlist2.setBounds(300, 303, 125, 14);
		frame.getContentPane().add(lblWishlist2);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(10, 437, 70, 23);
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		frame.getContentPane().add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(232, 437, 89, 23);
		btnSave.setToolTipText("Click here to save the current Gaplist on the Server.");
		frame.getContentPane().add(btnSave);
		
		JButton btnUp = new JButton("Up");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		btnUp.setBounds(84, 437, 49, 23);
		frame.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("Down");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		btnDown.setBounds(143, 437, 76, 23);
		frame.getContentPane().add(btnDown);
		
		JButton btnOpen = new JButton("Open...");
		btnOpen.setToolTipText("Click here to open saved Gaplists");
		btnOpen.setBounds(385, 437, 89, 23);
		frame.getContentPane().add(btnOpen);
		
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);

		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a YouTube Link here.", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		btnDisconnect.addActionListener((ActionEvent ae)->{c.disconnect();});
		
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {add(txtLink.getText(), rdbtnWishlist.isSelected(), chckbxInfront.isSelected(), txtLink);});
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(gaplistList.getSelectedIndex(), gaplistList);});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(gaplistList.getSelectedIndex(), gaplistList);});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(gaplistList.getSelectedIndex(), gaplistList);});
		}
}
