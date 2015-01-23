package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import util.ShowLabelThread;
import connection.Collector;

import javax.swing.JCheckBox;

/**
 * The ActionListener for the ConnectButton.
 * @author Haeldeus
 *
 */
public class ConnectButtonListener implements ActionListener{

	/**
	 * The Frame, this Listener will create
	 */
	private JFrame jFrame;
	
	/**
	 * The Edit Track Window.
	 */
	private JFrame editTrackWindow;
	
	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The TextField that contains the YouTube-Link.
	 */
	private JTextField txtLink;
	
	/**
	 * The TextField that contains the IP.
	 */
	private JTextField ip;
	
	/**
	 * The TextField that contains the Port.
	 */
	private JTextField port;
	
	/**
	 * The Label that displays possible Messages.
	 */
	private JLabel fail;

	/**
	 * The Constructor for the ActionListener.
	 * @param frame	The Frame, that will be created.
	 * @param c	The Collector that will send the Messages.
	 * @param ipfield	The TextField that contains the IP.
	 * @param portfield	The TextField that contains the Port.
	 * @param fail	The Label, that displays possible Messages.
	 */
	public ConnectButtonListener(JFrame frame, Collector c, JTextField ipfield, JTextField portfield, JLabel fail) {
		this.jFrame = frame;
		this.c = c;
		this.ip = ipfield;
		this.port = portfield;
		this.fail = fail;
		this.editTrackWindow = new JFrame();
	}
	

	/**
	 * Performs the Action.
	 * @param e Just a stub.
	 */
	public void actionPerformed(ActionEvent e) {
		if (!(c.connect(ip.getText(), port.getText())))
			fail.setText("Failed to connect to the Server. Please check for correct spelling.");
		else {
			fail.setText("Connected to "+ip.getText());
			jFrame.setTitle("JukePi - "+ip.getText()+":"+port.getText());
			jFrame.getContentPane().removeAll();
			jFrame.repaint();
			jFrame.setContentPane(getJFrame().getContentPane());
			jFrame.getContentPane().add(fail);
			fail.setBounds(143, 278, 189, 14);
			new ShowLabelThread(fail, jFrame).start();
			jFrame.repaint();
		}
	}
	
	/**
	 * Creates a new Frame.
	 * @wbp.parser.entryPoint
	 * @return The created Frame.
	 */
	public JFrame getJFrame() {
		// TODO Delete when Design is completed	
		JFrame jFrame = new JFrame();
		jFrame.setSize(new Dimension(528, 400));
		jFrame.setTitle("JukePi");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.getContentPane().setLayout(null);
		/*Delete till here*/		
		
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
		c.updateLists();
		
		txtLink = new JTextField();
		txtLink.setBounds(10, 60, 362, 20);
		jFrame.getContentPane().add(txtLink);
		txtLink.setText("Insert a YouTube Link here.");
		txtLink.addMouseListener(new TextFieldListener(new String[] {"Insert a YouTube Link here.", "Couldn't add", "Track added", "No valid"}, txtLink));
		txtLink.setColumns(10);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setBounds(10, 91, 62, 20);
		btnAdd.setToolTipText("Adds the YouTube-Link in the upper Textfield either to the Gaplist or the Wishlist, whatever is selected on the right.");
		jFrame.getContentPane().add(btnAdd);
		
		JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		rdbtnWishlist.setBounds(78, 90, 75, 23);
		jFrame.getContentPane().add(rdbtnWishlist);
		rdbtnWishlist.setSelected(true);
		
		JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		rdbtnGaplist.setBounds(155, 90, 75, 23);
		jFrame.getContentPane().add(rdbtnGaplist);
		
		JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNowPlaying.setBounds(10, 144, 68, 14);
		jFrame.getContentPane().add(lblNowPlaying);
		
		JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNextTrack.setBounds(10, 169, 68, 14);
		jFrame.getContentPane().add(lblNextTrack);
		
		JLabel lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblTrackNext.setBounds(88, 169, 244, 14);
		c.addNextTrackLabel(lblTrackNext);
		jFrame.getContentPane().add(lblTrackNext);
		
		JLabel lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlayingTrack.setBounds(88, 144, 244, 14);
		c.addNowPlayingLabel(lblPlayingTrack);
		jFrame.getContentPane().add(lblPlayingTrack);
		c.getNextTrack();
		
		JButton btnEditTracks = new JButton("Edit Tracks");
		btnEditTracks.setBounds(10, 194, 100, 23);
		btnEditTracks.setToolTipText("Click here to edit the tracks in the lists.");
		jFrame.getContentPane().add(btnEditTracks);
		
		JButton btnPlayPause = new JButton("Play");
		btnPlayPause.setBounds(10, 305, 89, 45);
		jFrame.getContentPane().add(btnPlayPause);
		c.addPlayButton(btnPlayPause);
		c.getFirstStatus();
		
		JButton btnSkip = new JButton("Skip");
		btnSkip.setBounds(122, 305, 89, 45);
		btnSkip.setToolTipText("Click here to skip the current track.");
		jFrame.getContentPane().add(btnSkip);
		
		JButton btnSeekForward = new JButton("<html><body>Seek<br>Forward</body></html>");
		btnSeekForward.setBounds(234, 305, 89, 45);
		jFrame.getContentPane().add(btnSeekForward);
		
		JButton btnSeekBackwards = new JButton("<html><body>Seek<br>Backwards</body></html>");
		btnSeekBackwards.setBounds(346, 305, 89, 45);
		jFrame.getContentPane().add(btnSeekBackwards);
		
		JButton btnDisconnect = new JButton("Disconnect");
		btnDisconnect.setBounds(388, 11, 114, 23);
		btnDisconnect.setToolTipText("Click here to disconnect from the Server.");
		jFrame.getContentPane().add(btnDisconnect);
		
		JCheckBox chckbxInfront = new JCheckBox("Add in Front");
		chckbxInfront.setBounds(232, 90, 97, 23);
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of the list.");
		jFrame.getContentPane().add(chckbxInfront);
		
		DisconnectButtonListener dcListener = new DisconnectButtonListener(jFrame, c, editTrackWindow);
		c.addDisconnectListener(dcListener);
		ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);
		
//		btnAdd.addActionListener(new AddButtonListener(txtLink, rdbtnWishlist, c, chckbxInfront, fail, jFrame));
		btnAdd.addActionListener((ActionEvent ae) -> {String link = txtLink.getText();boolean inFront = chckbxInfront.isSelected();
													 if (!link.isEmpty()) c.addToList(link, rdbtnWishlist.isSelected(), inFront, txtLink, fail, jFrame);
													 else {fail.setText("No valid Link");fail.setVerticalAlignment(JLabel.CENTER);fail.setHorizontalAlignment(JLabel.CENTER);
													 new util.ShowLabelThread(fail, jFrame).start();}});
		btnDisconnect.addActionListener(dcListener);
		
	//	btnSkip.addActionListener(new SkipButtonListener(c, fail, jFrame));
		btnSkip.addActionListener((ActionEvent ae) -> {c.skip(fail, jFrame);});
		
	//	btnPlayPause.addActionListener(new PlayButtonListener(c, fail, jFrame));
		btnPlayPause.addActionListener((ActionEvent ae) -> {c.playButtonPressed(fail, jFrame);});
		
		btnEditTracks.addActionListener(new EditTrackListener(editTrackWindow, c));
		btnSeekForward.addActionListener((ActionEvent ae) -> {c.seekForward(fail, jFrame);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {c.seekBackward(fail, jFrame);});
		
		jFrame.repaint();
		
		return jFrame;
	}
}