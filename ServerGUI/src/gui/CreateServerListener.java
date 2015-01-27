package gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import server.Server;
import server.ServerFactory;

public class CreateServerListener implements ActionListener{

	private JTextField tfPort;
	private JLabel fail;
	private Server s;
	private JFrame frame;
	
	public CreateServerListener(JTextField tfPort, JLabel fail, Server s, JFrame frame) {
		this.tfPort = tfPort;
		this.fail = fail;
		this.s = s;
		this.frame = frame;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		int iport = -1;
		try{
			iport = Integer.parseInt(tfPort.getText());
			fail.setVisible(false);
			s = ServerFactory.createServer(iport);
			new StartUpThread(s).start();
			createNewGui();
		}
		catch (NumberFormatException nfe) {
			fail.setText("Please insert a real number for the Port");
			fail.setVisible(true);
		}
	}
	
	/**
	 * @wbp.parser.entryPoint
	 */
	private void createNewGui() {
		/*Delete*/
	/*	JFrame frame = new JFrame();
		frame.setSize(new Dimension(300,200));
		frame.getContentPane().setLayout(null);
		frame.setTitle("JukePi Server");
		/*Delete*/
		
		frame.getContentPane().removeAll();
		frame.repaint();
		
		JButton btnShutDown = new JButton("Shut Down");
		btnShutDown.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnShutDown.setBounds(50, 50, 100, 50);
		btnShutDown.addActionListener(new ShutDownListener(frame, s));
		frame.getContentPane().add(btnShutDown);
	}
	
}
