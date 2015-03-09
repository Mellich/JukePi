package windows;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

public class EditTracks extends Window{

	private JFrame frame;
	
	private JLabel fail;
	
	private Song[] gaplist;
	
	private Song[] wishlist;
	
	private DefaultListModel<String> wishlistModel;
	
	private DefaultListModel<String> gaplistModel;
	
	private ServerConnection wrapper;
	
	public EditTracks(Song[] wishlist, Song[] gaplist, ServerConnection wrapper) {
		this.gaplist = gaplist;
		this.wishlist = wishlist;
		
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		for (Song s : wishlist)
			wishlistModel.addElement(s.getName());
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
		
		this.wrapper = wrapper;
	}
	
	@Override
	public void show() {
		createFrame();
		frame.setVisible(true);
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(fail, frame, text).start();
	}
	
	public void setGaplist(Song[] gaplist) {
		this.gaplist = gaplist;
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
	}
	
	public void setWishlist(Song[] wishlist) {
		this.wishlist = wishlist;
		for (Song s : wishlist)
			wishlistModel.addElement(s.getName());
	}
	
	public void moveTrackUp(int index, JList<String> list) {
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
		if (wrapper.deleteFromList(gaplist[index]))
			showFail("Deleted the Track from the Gaplist");
		else
			showFail("Couldn't delete the Track from the Gaplist");
		list.setSelectedIndex(index);
	}
	
	public void saveGaplist() {
		wrapper.saveGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Saved Gaplist.");
												else
													showFail("Couldn't save the Gaplist.");
											});
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(575, 400));
		frame.setTitle("Track Edit");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		JList<String> gaplistList = new JList<String>(gaplistModel);
		JList<String> wishlistList = new JList<String>(wishlistModel);
		
		JScrollPane gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(25, 40, 250, 250);
		JScrollPane wishlistPane = new JScrollPane(wishlistList);
		wishlistPane.setBounds(300, 40, 250, 250);
		
		frame.getContentPane().add(gaplistPane);
		frame.getContentPane().add(wishlistPane);
		
		JLabel lblGaplist = new JLabel("Gaplist -");
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplist.setBounds(25, 15, 250, 14);
		frame.getContentPane().add(lblGaplist);
		
		JLabel lblWishlist = new JLabel("Wishlist");
		lblWishlist.setHorizontalAlignment(JLabel.CENTER);
		lblWishlist.setVerticalAlignment(JLabel.CENTER);
		lblWishlist.setBounds(300, 15, 250, 14);
		frame.getContentPane().add(lblWishlist);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(25, 301, 76, 23);
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		frame.getContentPane().add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(461, 301, 89, 23);
		btnSave.setToolTipText("Click here to save the current Gaplist on the Raspberry");
		frame.getContentPane().add(btnSave);
		
		JButton btnUp = new JButton("Up");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		btnUp.setBounds(110, 301, 76, 23);
		frame.getContentPane().add(btnUp);
		
		JButton btnDown = new JButton("Down");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		btnDown.setBounds(199, 301, 76, 23);
		frame.getContentPane().add(btnDown);
		
		JButton btnOpen = new JButton("Open...");
		btnOpen.setToolTipText("Click here to open saved Gaplists");
		btnOpen.setBounds(360, 301, 89, 23);
		frame.getContentPane().add(btnOpen);
		
		fail = new JLabel("");
		fail.setBounds(35, 335, 515, 14);
		frame.getContentPane().add(fail);
		
//		btnOpen.addActionListener(new OpenButtonListener(frame, this, c));
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnDelete.addActionListener((ActionEvent ae) -> {deleteTrack(gaplistList.getSelectedIndex(), gaplistList);});
		btnUp.addActionListener((ActionEvent ae) -> {moveTrackUp(gaplistList.getSelectedIndex(), gaplistList);});
		btnDown.addActionListener((ActionEvent ae) -> {moveTrackDown(gaplistList.getSelectedIndex(), gaplistList);});
		
	}
}
