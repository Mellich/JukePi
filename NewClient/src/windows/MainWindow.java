package windows;

import util.TextFieldListener;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

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
	
	public MainWindow(Collector c, JFrame frame, ServerConnection wrapper) {
		this.c = c;
		this.frame = frame;
		frame.getContentPane().removeAll();
		this.wrapper = wrapper;
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
	
	/**
	 * Creates a new Frame.
	 * @wbp.parser.entryPoint
	 * @return The created Frame.
	 */
	public void constructFrame() {
	//	frame = new JFrame();
		frame.setSize(new Dimension(500, 400));
		frame.setTitle("JukePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
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
		
		JLabel lblNoGaplist = new JLabel("");
		lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoGaplist.setBounds(143, 11, 68, 14);
		frame.getContentPane().add(lblNoGaplist);
	//	wrapper.getGapList((String[] s)-> {lblNoGaplist.setText(""+s.length);});
		
		JLabel lblNoWishlist = new JLabel("");
		lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoWishlist.setBounds(143, 36, 46, 14);
		frame.getContentPane().add(lblNoWishlist);
	//	wrapper.getWishList((String[] s)-> {lblNoWishlist.setText(""+s.length);});
		
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
		
		JLabel lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlayingTrack.setBounds(88, 144, 244, 14);
		frame.getContentPane().add(lblPlayingTrack);
		wrapper.getCurrentTrackTitle((String[] s) -> {lblPlayingTrack.setText(s[0]);});		
		
		JLabel lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTrackNext.setBounds(88, 169, 244, 14);
		frame.getContentPane().add(lblTrackNext);
		
		Song[] wishlist = wrapper.getWishList();
		Song[] gaplist = wrapper.getGapList();
		
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
		
		JButton btnEditTracks = new JButton("Edit Tracks");
		btnEditTracks.setBounds(10, 194, 100, 23);
		btnEditTracks.setToolTipText("Click here to edit the tracks in the lists.");
		frame.getContentPane().add(btnEditTracks);
		
		JButton btnPlayPause = new JButton("Play");
		btnPlayPause.setBounds(122, 305, 89, 45);
		frame.getContentPane().add(btnPlayPause);
		
		JButton btnSeekBackwards = new JButton("<html><body>Seek<br>Backwards</body></html>");
		btnSeekBackwards.setBounds(10, 305, 89, 45);
		btnSeekBackwards.setToolTipText("Click here to seek 30 seconds backwards.");
		frame.getContentPane().add(btnSeekBackwards);
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.setBounds(346, 305, 89, 45);
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip);
		
		JButton btnSeekForward = new JButton("<html><body>Seek<br>Forward</body></html>");
		btnSeekForward.setBounds(234, 305, 89, 45);
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
	}
}
