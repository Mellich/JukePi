package gui;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import connection.Collector;

/**
 * The Class, that will initiate the GUI.
 * @author Haeldeus
 *
 */
public class GUI {

	/**
	 * The Collector that will send the Messages.
	 */
	Collector c;
	
	/**
	 * The TextField, where the User can insert the IP.
	 */
	private JTextField txtIp;
	
	/**
	 * The TextField, where the User can insert the Port.
	 */
	private JTextField txtPort;
	
	/**
	 * Constructs the Frame.
	 * @return	The Frame with it's content.
	 */
	public JFrame getFrame() {
		JFrame jFrame = new JFrame();
		jFrame.setSize(new Dimension(528, 400));
		jFrame.setTitle("JukePi");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setResizable(false);
		jFrame.getContentPane().setLayout(null);
		
		txtIp = new JTextField();
//TODO	txtIp.setText("IP");
		txtIp.setText("192.168.178.20");
		txtIp.setBounds(151, 110, 186, 20);
		jFrame.getContentPane().add(txtIp);
		txtIp.setColumns(10);
		
		txtPort = new JTextField();
//TODO	txtPort.setText("Port");
		txtPort.setText("22222");
		txtPort.setBounds(151, 184, 45, 20);
		jFrame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblWelcomescreen = new JLabel("<html><body>Welcome to the RaspberryPi Jukebox.<br>" +
											"Please enter IP-Address and Port below and click on Connect.</body></html>");
		lblWelcomescreen.setBounds(10, 11, 492, 80);
		jFrame.getContentPane().add(lblWelcomescreen);
		
		JLabel lblIPAddress = new JLabel("IP-Address:");
		lblIPAddress.setBounds(33, 113, 76, 14);
		jFrame.getContentPane().add(lblIPAddress);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(33, 187, 46, 14);
		jFrame.getContentPane().add(lblPort);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(151, 259, 131, 40);
		jFrame.getContentPane().add(btnConnect);
		c = new Collector();
		
		JLabel lblFail = new JLabel("");
		lblFail.setBounds(49, 234, 374, 14);
		jFrame.getContentPane().add(lblFail);
		
		txtIp.addMouseListener(new TextFieldListener(new String[] {"IP"}, txtIp));
		txtPort.addMouseListener(new TextFieldListener(new String[] {"Port"}, txtPort));
		btnConnect.addActionListener(new ConnectButtonListener(jFrame, c, txtIp, txtPort, lblFail));

		return jFrame;
	}
	
	/**
	 * Initiates the GUI.
	 * @param args	Just a stub.
	 */
	public static void main(String[] args) {
		GUI g = new GUI();
		g.getFrame().setVisible(true);
	}
}