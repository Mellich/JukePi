package gui;



import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.Rectangle;
import javax.swing.JLabel;

import exampleResponses.TestClass;
import javax.swing.JTextField;
import java.awt.Font;

public class GUI {
	
	private TestClass tc = new TestClass();
	
	/**
	 * This method initializes jFrame	
	 * 	
	 * @return javax.swing.JFrame	
	 */
	private JFrame getJFrame() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(new Dimension(528, 376));
		jFrame.setTitle("JukePi");
		jFrame.setContentPane(getJContentPane());
		return jFrame;
	}

	
	/**
	 * This method initializes jContentPane	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getJContentPane() {

		JPanel jContentPane = new JPanel();
		jContentPane.setLayout(null);
		
		JLabel lblGapList = new JLabel(""+tc.sr.getGapListSize());
		lblGapList.setBounds(new Rectangle(221, 18, 28, 29));
		jContentPane.add(lblGapList, null);
		
		JLabel lblNoGL = new JLabel("Number of Tracks in the Gaplist:");
		lblNoGL.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblNoGL.setBounds(new Rectangle(15, 18, 196, 28));
		jContentPane.add(lblNoGL);
		
		JButton btPlayPause = new JButton("Play");
		btPlayPause.setBounds(new Rectangle(87, 253, 89, 74));
		btPlayPause.setToolTipText("Click here to play a music file.");
		jContentPane.add(btPlayPause);
		
		JButton btSkip = new JButton("Skip");
		btSkip.setBounds(new Rectangle(341, 253, 89, 74));
		btSkip.setToolTipText("Click here to skip the current track.");
		jContentPane.add(btSkip);
		
		JLabel lblWishList = new JLabel("Number of Tracks in the Wishlist:");
		lblWishList.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblWishList.setBounds(15, 58, 196, 14);
		jContentPane.add(lblWishList);
		
		JLabel lblNoWL = new JLabel("");
		lblNoWL.setBounds(221, 58, 46, 14);
		lblNoWL.setText(""+tc.sr.getWishListSize());
		jContentPane.add(lblNoWL);
		
		JTextField txtUrlField = new JTextField();
		txtUrlField.setText("Insert a YouTube Link here...");
		txtUrlField.setBounds(15, 83, 316, 20);
		jContentPane.add(txtUrlField);
		txtUrlField.setColumns(10);
		
		JButton btAdd = new JButton("Add");
		btAdd.setToolTipText("Click here to add the Link on the left to the WishList");
		btAdd.setBounds(341, 82, 89, 21);
		jContentPane.add(btAdd);
		
		JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNowPlaying.setBounds(15, 139, 62, 14);
		jContentPane.add(lblNowPlaying);
		
		JLabel lblPlayingFile = new JLabel(""+tc.sr.getPlayingFile());
		lblPlayingFile.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblPlayingFile.setBounds(87, 139, 415, 14);
		jContentPane.add(lblPlayingFile);
		
		JLabel lblNextSong = new JLabel("Next Song:");
		lblNextSong.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblNextSong.setBounds(15, 164, 62, 14);
		jContentPane.add(lblNextSong);
		
		JLabel lblSongNext = new JLabel(""+tc.sr.getNextSong());
		lblSongNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		lblSongNext.setBounds(87, 164, 415, 14);
		jContentPane.add(lblSongNext);
		
		/**
		 * Adding the Action Listener to each Button and TextField.
		 */
		btSkip.addActionListener(new SkipButtonListener(tc, lblPlayingFile, lblSongNext));
		btAdd.addActionListener(new AddButtonListener(txtUrlField, tc, lblNoWL));
		txtUrlField.addMouseListener(new TextFieldClickListener(txtUrlField));
		btPlayPause.addActionListener(new PPButtonListener(btPlayPause));
		
		return jContentPane;
	}


	/**
	 * This Method starts the GUI
	 * 
	 * @param args Just a stub
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {;
			@Override
			public void run() {
				GUI g = new GUI();
				g.getJFrame().setVisible(true);
			}
		});
	}
}
