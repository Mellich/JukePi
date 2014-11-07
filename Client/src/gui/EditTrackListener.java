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
	}

	private JPanel fillContent() {
		
		JPanel contentPane = new JPanel();
		contentPane.setLayout(null);
		
		DefaultListModel<String> gaplist = new DefaultListModel<String>();
		c.addGaplistModel(gaplist);
		DefaultListModel<String> wishlist = new DefaultListModel<String>();
		c.addWishlistModel(wishlist);
		c.fillModels();
		
		JList<String> gaplistList = new JList<String>(gaplist);
		JList<String> wishlistList = new JList<String>(wishlist);
		
		JScrollPane gaplistPane = new JScrollPane(gaplistList);
		gaplistPane.setBounds(25, 40, 250, 250);	
		JScrollPane wishlistPane = new JScrollPane(wishlistList);
		wishlistPane.setBounds(300, 40, 250, 250);
		
		contentPane.add(gaplistPane);
		contentPane.add(wishlistPane);
		
		return contentPane;
	}

}
