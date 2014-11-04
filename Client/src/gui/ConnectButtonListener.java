package gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import connection.Collector;

public class ConnectButtonListener implements ActionListener{

	private JFrame jFrame;
	private Collector c;
	private JTextField txtYoutubelink;
	private JTextField ip;
	private JTextField port;
	private JLabel fail;
	
	public ConnectButtonListener(JFrame frame, Collector c, JTextField ipfield, JTextField portfield, JLabel fail) {
		this.jFrame = frame;
		this.c = c;
		this.ip = ipfield;
		this.port = portfield;
		this.fail = fail;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (!c.connect(ip.getText(), port.getText()))
			fail.setText("Failed to connect to the Server. Please check for correct spelling.");
		else {
			jFrame.getContentPane().removeAll();
			jFrame.repaint();
			JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");
			lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblGaplist.setBounds(10, 11, 123, 14);
			jFrame.getContentPane().add(lblGaplist);
			
			JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");
			lblWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblWishlist.setBounds(10, 36, 123, 14);
			jFrame.getContentPane().add(lblWishlist);
			
			JLabel lblNoGaplist = new JLabel("");
			lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNoGaplist.setBounds(143, 11, 68, 14);
			jFrame.getContentPane().add(lblNoGaplist);
			c.addGaplistLabel(lblNoGaplist);
			
			JLabel lblNoWishlist = new JLabel("");
			lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
			lblNoWishlist.setBounds(143, 36, 46, 14);
			jFrame.getContentPane().add(lblNoWishlist);
			c.addWishlistLabel(lblNoWishlist);
			
			txtYoutubelink = new JTextField();
			txtYoutubelink.setBounds(10, 60, 250, 20);
			jFrame.getContentPane().add(txtYoutubelink);
			txtYoutubelink.setColumns(10);
			
			JButton btnWishlistadd = new JButton("Add");
			btnWishlistadd.setBounds(270, 60, 62, 20);
			jFrame.getContentPane().add(btnWishlistadd);
			
			JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
			rdbtnWishlist.setBounds(338, 59, 75, 23);
			jFrame.getContentPane().add(rdbtnWishlist);
			c.addWishlistRB(rdbtnWishlist);
			
			JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
			rdbtnGaplist.setBounds(415, 59, 75, 23);
			jFrame.getContentPane().add(rdbtnGaplist);
			c.addGaplistRB(rdbtnGaplist);
			
			rdbtnWishlist.addActionListener(new RadioButtonListener(rdbtnWishlist, rdbtnGaplist));
			rdbtnGaplist.addActionListener(new RadioButtonListener(rdbtnGaplist, rdbtnWishlist));
			jFrame.repaint();
		}
			
	}

}
