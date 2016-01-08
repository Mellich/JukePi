package windows;

import util.JMultilineLabel;
import util.PopClickListener;
import util.TextFieldListener;
import util.layouts.LoginLayout;

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
	private JFrame frame;
	
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
		long start = System.currentTimeMillis();
		frame.setSize(new Dimension(500, 400));
		frame.setMinimumSize(new Dimension(500,400));
		frame.setTitle("JukePi");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new LoginLayout());
		
		util.IO.println(this, "Frame Options set");
		
		txtIp = new JTextField();
//TODO	txtIp.setText("IP");
		txtIp.setText("192.168.178.20");
		txtIp.addMouseListener(new PopClickListener(txtIp));
		frame.getContentPane().add(txtIp, LoginLayout.IP_TEXT);
		txtIp.setColumns(10);
		
		util.IO.println(this, "txtIP set");
		
		txtPort = new JTextField();
//TODO	txtPort.setText("Port");
		txtPort.setText("22222");
		txtPort.addMouseListener(new PopClickListener(txtPort));
		frame.getContentPane().add(txtPort, LoginLayout.PORT_TEXT);
		txtPort.setColumns(10);
		
		JMultilineLabel lblWelcomescreen = new JMultilineLabel("Welcome to the RaspberryPi Jukebox.\n" +
											"Please enter IP-Address and Port below and click on \"Connect\".\n" + 
											"You could also scan your network for possible Servers by clicking \"UDP Connect\",\n" +
											"or you create your own Server by clicking on \"Create own Server\".");
		frame.getContentPane().add(lblWelcomescreen, LoginLayout.WELCOME_LABEL);
		
		JLabel lblIPAddress = new JLabel("IP-Address:");
		frame.getContentPane().add(lblIPAddress, LoginLayout.IP_LABEL);
		
		JLabel lblPort = new JLabel("Port:");
		frame.getContentPane().add(lblPort, LoginLayout.PORT_LABEL);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setToolTipText("Tries to connect to a Server with the given IP and Port.");
		frame.getContentPane().add(btnConnect, LoginLayout.CONNECT_BUTTON);
		
		lblFail = new JLabel("");
		frame.getContentPane().add(lblFail, LoginLayout.FAIL_LABEL);
		
		JButton btnUDPConnect = new JButton("UDP Connect");
		btnUDPConnect.setToolTipText("Scans your network for available Servers and connects to them.");
		frame.getContentPane().add(btnUDPConnect, LoginLayout.UDP_BUTTON);
		
		JButton btnInternServer = new JButton("Create own server");
		btnInternServer.setToolTipText("Creates an own Server with the Port in the Port-Field above.");
		frame.getContentPane().add(btnInternServer, LoginLayout.INTERN_SERVER_BUTTON);
		
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
		util.IO.println(this, "Constructed Frame in " + (System.currentTimeMillis()-start) + "ms.");
	}

	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
}

