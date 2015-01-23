package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

import server.Server;

public class ShutDownListener implements ActionListener{

	private JFrame frame;
	private Server s;
	
	public ShutDownListener(JFrame frame, Server s) {
		this.frame = frame;
		this.s = s;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		s.shutDown();
		createNewGui();
	}
	
	public void createNewGui() {
		frame.setEnabled(false);
		frame.setVisible(false);
		GUI.main(null);
	}
	
}
