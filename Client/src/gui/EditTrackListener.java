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

public class EditTrackListener implements ActionListener{

	private JFrame frame;
	private Collector c;
	
	public EditTrackListener(Collector c) {
		this.c = c;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame = new JFrame();
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
		
		JLabel lblGaplist = new JLabel("Gaplist");
		lblGaplist.setBounds(124, 15, 67, 14);
		contentPane.add(lblGaplist);
		
		JLabel lblWishlist = new JLabel("Wishlist");
		lblWishlist.setBounds(401, 15, 46, 14);
		contentPane.add(lblWishlist);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(102, 303, 89, 23);
		btnDelete.setToolTipText("Click here to delete the selected track from the Gaplist.");
		contentPane.add(btnDelete);
		
		JButton btnSave = new JButton("Save");
		btnSave.setBounds(381, 301, 89, 23);
		contentPane.add(btnSave);
		
		btnDelete.addActionListener(new DeleteButtonListener(c, gaplistList));
		btnSave.addActionListener(new SaveButtonListener(c));
		
		return contentPane;
	}
}
