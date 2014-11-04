package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import threads.ConnectedThread;

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
			fail.setText("Connected to "+ip.getText());
			c.startStableTest(ip.getText(), Integer.parseInt(port.getText()), jFrame, fail);
			jFrame.setTitle("JukePi - "+ip.getText()+":"+port.getText());
			jFrame.getContentPane().removeAll();
			jFrame.repaint();
			jFrame.setContentPane(getJFrame().getContentPane());
			jFrame.getContentPane().add(fail);
			fail.setBounds(143, 278, 189, 14);
			ConnectedThread ct = new ConnectedThread(fail, jFrame);
			ct.start();
			jFrame.repaint();
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public JFrame getJFrame() {
		// TODO Delete when Design is completed	
/*		JFrame jFrame = new JFrame();
		jFrame.setSize(new Dimension(528, 376));
		jFrame.setTitle("JukePi");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
*/		/*Delete till here*/		
		
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
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(270, 60, 62, 20);
		jFrame.getContentPane().add(btnAdd);
		
		JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		rdbtnWishlist.setBounds(338, 59, 75, 23);
		jFrame.getContentPane().add(rdbtnWishlist);
		rdbtnWishlist.setSelected(true);
		c.addWishlistRB(rdbtnWishlist);
		
		JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		rdbtnGaplist.setBounds(415, 59, 75, 23);
		jFrame.getContentPane().add(rdbtnGaplist);
		c.addGaplistRB(rdbtnGaplist);
		
		rdbtnWishlist.addActionListener(new RadioButtonListener(rdbtnWishlist, rdbtnGaplist));
		rdbtnGaplist.addActionListener(new RadioButtonListener(rdbtnGaplist, rdbtnWishlist));
		
		JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNowPlaying.setBounds(10, 111, 68, 14);
		jFrame.getContentPane().add(lblNowPlaying);
		
		JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNextTrack.setBounds(10, 136, 68, 14);
		jFrame.getContentPane().add(lblNextTrack);
		
		JLabel lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlayingTrack.setBounds(88, 111, 244, 14);
		c.addNowPlayingLabel(lblPlayingTrack);
		jFrame.getContentPane().add(lblPlayingTrack);
		
		JLabel lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTrackNext.setBounds(88, 136, 244, 14);
		c.addNextTrackLabel(lblTrackNext);
		jFrame.getContentPane().add(lblTrackNext);
		
		JButton btnEditTracks = new JButton("Edit Tracks");
		btnEditTracks.setBounds(10, 161, 100, 23);
		jFrame.getContentPane().add(btnEditTracks);
		
		JButton btnPlayPause = new JButton("Play");
		btnPlayPause.setBounds(123, 229, 89, 45);
		jFrame.getContentPane().add(btnPlayPause);
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.setBounds(270, 229, 89, 45);
		jFrame.getContentPane().add(btnSkip);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(123, 303, 236, 23);
		jFrame.getContentPane().add(btnDisconnect);
		
		btnAdd.addActionListener(new AddButtonListener(txtYoutubelink, c, btnAdd));
		btnDisconnect.addActionListener(new DisconnectButtonListener(jFrame, c));
		btnSkip.addActionListener(new SkipButtonListener(c, fail, jFrame));
		
		jFrame.repaint();
		
		return jFrame;
	}
}