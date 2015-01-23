package gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JLabel;

import server.Server;
import java.awt.Font;

public class GUI {
	
	Server server;

	public JFrame getFrame() {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(300,200));
		frame.getContentPane().setLayout(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("JukePi Server");
		
		JTextField tfPort = new JTextField("Enter Port");
		tfPort.setBounds(65, 40, 75, 20);
		frame.getContentPane().add(tfPort);
		
		JButton btnCreateServer = new JButton("Create Server");
		btnCreateServer.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCreateServer.setBounds(65, 75, 150, 30);
		frame.getContentPane().add(btnCreateServer);
		
		JLabel label = new JLabel("");
		label.setBounds(30, 120, 220, 15);
		frame.getContentPane().add(label);
		
		btnCreateServer.addActionListener(new CreateServerListener(tfPort, label, server, frame));		
		return frame;
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public static void main(String[] args) {
		GUI g = new GUI();
		g.getFrame().setVisible(true);
	}
}
