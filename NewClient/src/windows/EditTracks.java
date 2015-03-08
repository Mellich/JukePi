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

import client.serverconnection.Song;

public class EditTracks extends Window{

	private JFrame frame;
	
	private JLabel lblFail;
	
	private DefaultListModel<String> wishlistModel;
	
	private DefaultListModel<String> gaplistModel;
	
	public EditTracks(Song[] wishlist, Song[] gaplist) {
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		for (Song s : wishlist)
			wishlistModel.addElement(s.getName());
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
	}
	
	@Override
	public void show() {
		createFrame();
		frame.setVisible(true);
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}
	
	public void setGaplist(Song[] gaplist) {
		for (Song s : gaplist)
			gaplistModel.addElement(s.getName());
	}
	
	private void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(575, 400));
		frame.setTitle("Track Edit");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
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
		
		lblFail = new JLabel("");
		lblFail.setBounds(25, 346, 525, 14);
		frame.getContentPane().add(lblFail);
		
//		btnOpen.addActionListener(new OpenButtonListener(frame, this, c));
		
	//	btnSave.addActionListener(new SaveButtonListener(c, lblFail, frame));
//		btnSave.addActionListener((ActionEvent ae) -> {c.saveGaplist(lblFail, frame);});
		
	//	btnDelete.addActionListener(new DeleteButtonListener(c, gaplistList, lblFail, frame));
//		btnDelete.addActionListener((ActionEvent ae) -> {c.deleteFromGaplist(gaplistList.getSelectedIndex(), lblFail, frame);});
		
	//	btnUp.addActionListener(new UpButtonListener(gaplistList, c, lblFail, frame));
//		btnUp.addActionListener((ActionEvent ae) -> {int index = gaplistList.getSelectedIndex();c.moveTrackUp(index, lblFail, frame);
//													try{Thread.sleep(100);}catch(Exception e) {}gaplistList.setSelectedIndex(index-1);});
		
	//	btnDown.addActionListener(new DownButtonListener(gaplistList, c, lblFail, frame));
//		btnDown.addActionListener((ActionEvent ae) -> {int index = gaplistList.getSelectedIndex();c.moveTrackDown(index, lblFail, frame);
//													  try{Thread.sleep(100);}catch(Exception e) {}gaplistList.setSelectedIndex(index+1);});
		
	}
}
