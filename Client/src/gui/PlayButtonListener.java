package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import threads.PlayPauseThread;

import connection.Collector;

public class PlayButtonListener implements ActionListener{

	private Collector c;
	private JLabel fail;
	private JFrame frame;
	
	public PlayButtonListener(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		PlayPauseThread ppt = new PlayPauseThread(c, fail, frame);
		ppt.start();
		frame.repaint();
	}

}
