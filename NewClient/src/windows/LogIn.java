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

/**
 * The {@link Window}, that will contain the Login-Screen.
 * @author Haeldeus
 * @version 1.0
 */
public class LogIn extends Window {
	/**
	 * The {@link Collector} that will send the Messages.
	 */
	private final Collector collector;
	
	/**
	 * The TextField, where the User can insert the IP.
	 * @see JTextField
	 */
	private JTextField txtIp;
	
	/**
	 * The TextField, where the User can insert the Port.
	 * @see JTextField
	 */
	private JTextField txtPort;
	
	/**
	 * The Frame of the Login-Screen.
	 * @see JFrame
	 */
	private final JFrame frame;
	
	/**
	 * The Label, that will display possible Messages.
	 * @see JLabel
	 */
	private JLabel lblFail;
	
	/**
	 * The Constructor for the Login-Screen. Will set the given parameters to their belonging 
	 * Class-Variables and instantiates a new Fail-Label.
	 * @param collector	The {@link Collector}, that will perform the Actions, which contains 
	 * information, that will be needed elsewhere, too (e.g. connecting, creating a new 
	 * Server, etc.).
	 * @param frame The Frame, this Screen will be displaying.
	 * @since 1.0
	 */
	public LogIn(Collector collector, JFrame frame) {
		this.collector = collector;
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
	 * @since 1.0
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
											"Please enter IP-Address and Port below and click on \"Connect\".<br>" + 
											"You could also scan your network for possible Servers by clicking \"UDP Connect\", <br>" +
											"or you create your own Server by clicking on \"Create own Server\".</body></html>");
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
		btnConnect.setToolTipText("Tries to connect to a Server with the given IP and Port.");
		frame.getContentPane().add(btnConnect);
		
		lblFail = new JLabel("");
		lblFail.setBounds(49, 234, 374, 14);
		frame.getContentPane().add(lblFail);
		
		JButton btnUDPConnect = new JButton("UDP Connect");
		btnUDPConnect.setBounds(158, 278, 131, 40);
		btnUDPConnect.setToolTipText("Scans your network for available Servers and connects to them.");
		frame.getContentPane().add(btnUDPConnect);
		
		JButton btnInternServer = new JButton("Create own server");
		btnInternServer.setBounds(313, 278, 171, 40);
		btnInternServer.setToolTipText("Creates an own Server with the Port in the Port-Field above.");
		frame.getContentPane().add(btnInternServer);
		
		txtIp.addMouseListener(new TextFieldListener(new String[] {"IP"}, txtIp));
		txtPort.addMouseListener(new TextFieldListener(new String[] {"Port"}, txtPort));
		btnConnect.addActionListener((ActionEvent ae) -> {new Thread(() -> {collector.connect(txtIp.getText(), txtPort.getText());}).start();});
		btnUDPConnect.addActionListener(new UDPListener(collector));
		btnInternServer.addActionListener((ActionEvent ae)->{collector.createLocalServer(txtPort.getText());});

		txtIp.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					collector.connect(txtIp.getText(), txtPort.getText());
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
					collector.connect(txtIp.getText(), txtPort.getText());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
			}
		});
	}
}
