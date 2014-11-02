package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;

import connection.Collector;


public class SkipButtonListener implements ActionListener{

	private Collector c;
	private JLabel pf;
	private JLabel ns;
	
	public SkipButtonListener(Collector c, JLabel playingFile, JLabel nextSong) {
		this.c = c;
		this.pf = playingFile;
		this.ns = nextSong;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		c.skip();
		pf.setText(""+c.getPlayingFile());
		ns.setText(""+c.getNextSong());
	}

}
