package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import connection.Collector;

/**
 * The Class, that will perform the actions, made by the Button that shows the 
 * Tracklist.
 * @author Haeldeus
 *
 */
public class ShowListener implements ActionListener{

	/**
	 * The Collector with all Information about the lists and the Methods to 
	 * communicate with the Server.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the Listener.
	 * @param c	The Collector reference, where the Tracklist is saved.
	 */
	public ShowListener(Collector c) {
		this.c = c;
	}
	
	/**
	 * The Method, that will perform the actions.
	 * 
	 * @param arg0 Just a stub.
	 * @wbp.parser.entryPoint
	 */
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		JFrame gaplist = new JFrame();
		gaplist.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
		gaplist.setSize(new Dimension(850, 500));
		gaplist.setTitle("Track Edit");
		gaplist.setContentPane(fillContent());
		gaplist.setVisible(true);
	}
	
	/**
	 * Fills the Content of JPanel.
	 * @return The JPanel with all Lists in TextAreas.
	 */
	public JPanel fillContent() {
		
		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		
		
		DefaultListModel<String> listGap = new DefaultListModel<String>();
		for (String i : c.getGapList()) {
			listGap.addElement(i);
		}
		JList<String> tpGaplist = new JList<String>(listGap);
		JScrollPane spScrollGL = new JScrollPane(tpGaplist);
		spScrollGL.setBounds(25, 40, 250, 300);	
		jContentPane.add(spScrollGL);
		
		DefaultListModel<String> listWish = new DefaultListModel<String>();
		for (String i : c.getWishList()) {
			listWish.addElement(i);
		}
		JList<String> tpWishlist = new JList<String>(listWish);
		
		JScrollPane spScrollWL = new JScrollPane(tpWishlist);
		spScrollWL.setBounds(300, 40, 250, 300);
		jContentPane.add(spScrollWL);
		
		
		DefaultListModel<String> listTrack = new DefaultListModel<String>();
		listTrack.addElement(""+c.getPlayingFile()+"\n");
		for (String i : c.getWishList())
			listTrack.addElement(i);
		for (String i : c.getGapList())
			listTrack.addElement(i);
		JList<String> tpTracklist = new JList<String>(listTrack);
		
		JScrollPane spScrollTL = new JScrollPane(tpTracklist);
		spScrollTL.setBounds(575,40,250,300);
		jContentPane.add(spScrollTL);
		
		JLabel lblGaplist = new JLabel("Gaplist");
		lblGaplist.setBounds(130,15,40,15);
		jContentPane.add(lblGaplist);
		
		JLabel lblWishlist = new JLabel("Wishlist");
		lblWishlist.setBounds(395, 15, 60, 15);
		jContentPane.add(lblWishlist);
		
		JLabel lblTracklist = new JLabel("Tracklist");
		lblTracklist.setBounds(670, 15, 60, 15);
		jContentPane.add(lblTracklist);
		
		JButton btnUp = new JButton("Up");
		btnUp.setBounds(25, 351, 77, 23);
		btnUp.addActionListener(new UpButtonListener(tpGaplist,1));
		jContentPane.add(btnUp);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setBounds(112, 351, 77, 23);
		jContentPane.add(btnDelete);
		
		JButton btnDown = new JButton("Down");
		btnDown.setBounds(198, 351, 77, 23);
		jContentPane.add(btnDown);
		
		JButton btnUp2 = new JButton("Up");
		btnUp2.setBounds(300, 351, 77, 23);
		jContentPane.add(btnUp2);
		
		JButton btnDelete2 = new JButton("Delete");
		btnDelete2.setBounds(387, 351, 77, 23);
		jContentPane.add(btnDelete2);
		
		JButton btnDown2 = new JButton("Down");
		btnDown2.setBounds(473, 351, 77, 23);
		jContentPane.add(btnDown2);
		
		JButton btnUp3 = new JButton("Up");
		btnUp3.setBounds(575, 351, 77, 23);
		jContentPane.add(btnUp3);
		
		JButton btnDelete3 = new JButton("Delete");
		btnDelete3.setBounds(662, 351, 77, 23);
		jContentPane.add(btnDelete3);
		
		JButton btnDown3 = new JButton("Down");
		btnDown3.setBounds(748, 351, 77, 23);
		jContentPane.add(btnDown3);
		
		
		/**
		 * Adding the Listeners
		 */
		btnDelete.addActionListener(new DeleteButtonListener(tpGaplist, 1, c, listGap, listWish, listTrack));
		btnDelete2.addActionListener(new DeleteButtonListener(tpWishlist, 2, c, listGap, listWish, listTrack));
		
		return jContentPane;
	}
}