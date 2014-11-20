package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;

import threads.SkipThread;

import connection.Collector;

public class SkipButtonListener implements ActionListener{

	private Collector c;
	private JLabel fail;
	private JFrame frame;
	
	public SkipButtonListener(Collector c, JLabel fail, JFrame frame) {
		this.c = c;
		this.fail = fail;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		frame.repaint();
		SkipThread st = new SkipThread(c, fail, frame);
		st.start();
		frame.repaint();
	}

}
