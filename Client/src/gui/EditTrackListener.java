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

/**
 * The ActionListener for the EditTrackButton.
 * @author Haeldeus
 *
 */
public class EditTrackListener implements ActionListener{

	/**
	 * The Frame, that will be created.
	 */
	private JFrame frame;
	
	/**
	 * The Collector, that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the ActionListener.
	 * @param frame	The Frame, that will be changed.
	 * @param c	The Collector, that will send the Messages.
	 */
	public EditTrackListener(JFrame frame, Collector c) {
		this.frame = frame;
		this.c = c;
	}
	
	/**
	 * Performs the Action.
	 * @wbp.parser.entryPoint
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		//TODO Delete
	//	JFrame frame = new JFrame();
		//Till here
		c.addEditTrackListener(this);
		frame.setSize(new Dimension(575, 400));
		frame.setTitle("Track Edit");
		frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		frame.setContentPane(fillContent());
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * Fills the Content of the Frame.
	 * @return	The ContentPane.
	 */
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
		
		JLabel lblFail = new JLabel("");
		lblFail.setBounds(25, 346, 525, 14);
		contentPane.add(lblFail);
		
		btnOpen.addActionListener(new OpenButtonListener(frame, this, c));
		btnSave.addActionListener(new SaveButtonListener(c, lblFail, frame));
		btnDelete.addActionListener(new DeleteButtonListener(c, gaplistList, lblFail, frame));
		btnUp.addActionListener(new UpButtonListener(gaplistList, c, lblFail, frame));
		btnDown.addActionListener(new DownButtonListener(gaplistList, c, lblFail, frame));
		
		return contentPane;
	}
}
