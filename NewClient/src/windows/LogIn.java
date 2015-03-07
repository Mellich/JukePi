package windows;

import util.TextFieldListener;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.UDPListener;
import connection.Collector;

public class LogIn extends Window {
	/**
	 * The Collector that will send the Messages.
	 */
	private Collector c;
	
	/**
	 * The TextField, where the User can insert the IP.
	 */
	private JTextField txtIp;
	
	/**
	 * The TextField, where the User can insert the Port.
	 */
	private JTextField txtPort;
	
	private JFrame frame;
	
	private JLabel lblFail;
	
	public LogIn(Collector c, JFrame frame) {
		this.c = c;
		this.frame = frame;
		lblFail = new JLabel("");
	}
	
	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}
	
	@Override
	public void close() {
		frame.setVisible(false);
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}
	
	/**
	 * Constructs the Frame.
	 * @wbp.parser.entryPoint
	 */
	private void constructFrame() {
		frame.setSize(new Dimension(500, 400));
		frame.setTitle("JukePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);
		
		txtIp = new JTextField();
//TODO	txtIp.setText("IP");
		txtIp.setText("192.168.178.20");
		txtIp.setBounds(151, 110, 186, 20);
		frame.getContentPane().add(txtIp);
		txtIp.setColumns(10);
		
		txtPort = new JTextField();
//TODO	txtPort.setText("Port");
		txtPort.setText("22222");
		txtPort.setBounds(151, 184, 45, 20);
		frame.getContentPane().add(txtPort);
		txtPort.setColumns(10);
		
		JLabel lblWelcomescreen = new JLabel("<html><body>Welcome to the RaspberryPi Jukebox.<br>" +
											"Please enter IP-Address and Port below and click on Connect.</body></html>");
		lblWelcomescreen.setBounds(10, 11, 492, 80);
		frame.getContentPane().add(lblWelcomescreen);
		
		JLabel lblIPAddress = new JLabel("IP-Address:");
		lblIPAddress.setBounds(33, 113, 76, 14);
		frame.getContentPane().add(lblIPAddress);
		
		JLabel lblPort = new JLabel("Port:");
		lblPort.setBounds(33, 187, 46, 14);
		frame.getContentPane().add(lblPort);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBounds(10, 278, 131, 40);
		frame.getContentPane().add(btnConnect);
		
		lblFail = new JLabel("");
		lblFail.setBounds(49, 234, 374, 14);
		frame.getContentPane().add(lblFail);
		
		JButton btnUDPConnect = new JButton("UDP Connect");
		btnUDPConnect.setBounds(158, 278, 131, 40);
		frame.getContentPane().add(btnUDPConnect);
		
		JButton btnInternServer = new JButton("Create own server");
		btnInternServer.setBounds(313, 278, 171, 40);
		frame.getContentPane().add(btnInternServer);
		
		txtIp.addMouseListener(new TextFieldListener(new String[] {"IP"}, txtIp));
		txtPort.addMouseListener(new TextFieldListener(new String[] {"Port"}, txtPort));
		btnConnect.addActionListener((ActionEvent ae) -> {c.connect(txtIp.getText(), txtPort.getText());});
		btnUDPConnect.addActionListener(new UDPListener(c));
		btnInternServer.addActionListener((ActionEvent ae)->{c.createLocalServer(22222);});

		txtIp.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					c.connect(txtIp.getText(), txtPort.getText());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
		
		txtPort.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					c.connect(txtIp.getText(), txtPort.getText());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
	//	return frame;
	}
}
