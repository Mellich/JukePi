package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import exampleResponses.TestClass;

public class SkipButtonListener implements ActionListener{

	private TestClass tc;
	private JLabel pf;
	private JLabel ns;
	
	public SkipButtonListener(TestClass tc, JLabel playingFile, JLabel nextSong) {
		this.tc = tc;
		this.pf = playingFile;
		this.ns = nextSong;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		tc.sr.skip();
		pf.setText(""+tc.sr.getPlayingFile());
		ns.setText(""+tc.sr.getNextSong());
	}

}
