package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import connection.Collector;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;

public class EditTrackListener implements ActionListener{

	private JFrame frame;
	private Collector c;
	
	public EditTrackListener(JFrame frame, Collector c) {
		this.frame = frame;
		this.c = c;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */	
	@Override
	public void actionPerformed(ActionEvent arg0) {
	/*	//TODO Delete
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(528, 400));
		frame.setTitle("JukePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	*/	//Till here
		frame.setSize(new Dimension(575, 376));
		frame.setTitle("Track Edit");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setContentPane(fillContent());
		frame.setVisible(true);
		frame.setResizable(false);
	}

	private JPanel fillContent() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		
		DefaultListModel<String> gaplist = new DefaultListModel<String>();
		c.addGaplistModel(gaplist);
		DefaultListModel<String> wishlist = new DefaultListModel<String>();
		c.addWishlistModel(wishlist);
		c.addSecondFrame(frame);
		c.fillModels();
		
		JList<String> gaplistList = new JList<String>(gaplist);
		JList<String> wishlistList = new JList<String>(wishlist);
		
		JScrollPane gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(25, 40, 250, 250);	
		JScrollPane wishlistPane = new JScrollPane(wishlistList);
		wishlistPane.setBounds(300, 40, 250, 250);
		
		contentPane.add(gaplistPane);
		contentPane.add(wishlistPane);
		
		JLabel lblGaplist = new JLabel("Gaplist -");
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplist.setBounds(25, 15, 250, 14);
		c.addGaplistNameLabel(lblGaplist);
		c.updateGaplistName();
		contentPane.add(lblGaplist);
		
		JLabel lblWishlist = new JLabel("Wishlist");
		lblWishlist.setHorizontalAlignment(JLabel.CENTER);
		lblWishlist.setVerticalAlignment(JLabel.CENTER);
		lblWishlist.setBounds(300, 15, 250, 14);
		contentPane.add(lblWishlist);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(25, 301, 76, 23);
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		contentPane.add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(461, 301, 89, 23);
		btnSave.setToolTipText("Click here to save the current Gaplist on the Raspberry");
		contentPane.add(btnSave);
		
		JButton btnUp = new JButton("Up");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		btnUp.setBounds(110, 301, 76, 23);
		contentPane.add(btnUp);
		
		JButton btnDown = new JButton("Down");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		btnDown.setBounds(199, 301, 76, 23);
		contentPane.add(btnDown);
		
		JButton btnOpen = new JButton("Open...");
		btnOpen.setToolTipText("Click here to open saved Gaplists");
		btnOpen.setBounds(360, 301, 89, 23);
		contentPane.add(btnOpen);
		
		btnSave.addActionListener(new SaveButtonListener(c));
		btnDelete.addActionListener(new DeleteButtonListener(c, gaplistList));
		btnUp.addActionListener(new UpButtonListener(gaplistList, c));
		btnDown.addActionListener(new DownButtonListener(gaplistList, c));
		btnOpen.addActionListener(new OpenButtonListener(frame, this, c));
		
		return contentPane;
	}
}
