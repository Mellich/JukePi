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
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import listener.UDPListener;
import connection.Collector;

/**
 * <p>The {@link Window}, that will contain the Login-Screen.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #LoginWindow(Collector)}:
 * 				The Constructor for this Window.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #setActive(boolean)}:
 * 				Sets the state of the Window, depending on the given {@code boolean}, either 
 * 				enabled or disabled.</li>
 * 
 * 			<li>{@link #show()}:
 * 				Sets the Window visible and enabled.</li>
 * 
 * 			<li>{@link #showFail(String)}:
 * 				Displays the given {@code String} on the Frame.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #createFrame()}:
 * 				Constructs the Frame and places it's Components on their belonging Spots.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p> 		
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #collector}:
 * 				The {@link Collector}, that provides necessary Methods to connect.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays Messages from the Server.</li>
 * 
 * 			<li>{@link #txtIp}:
 * 				The {@link JTextField}, where the User enters the IP of the Server he wants to 
 * 				connect to.</li>
 * 
 * 			<li>{@link #txtPort}:
 * 				The {@link JTextField}, where the User enters the Port of the Server he wants 
 * 				to connect to.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class LoginWindow extends Window {
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private final Collector collector}</p>
	 * <p style="margin-left: 20px">The {@link Collector} that will send the Messages.</p>
	 */
	private final Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtIp</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextField txtIp}</p>
	 * <p style="margin-left: 20px">The TextField, where the User can insert the IP.</p>
	 * @see JTextField
	 */
	private JTextField txtIp;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtPort</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextField txtPort}</p>
	 * <p style="margin-left: 20px">The TextField, where the User can insert the Port.</p>
	 * @see JTextField
	 */
	private JTextField txtPort;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame of the Login-Screen.</p>
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The Label, that will display possible Messages.</p>
	 * @see JLabel
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>LoginWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public LoginWindow(Collector)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Login-Screen. Will set the given 
	 * parameters to their belonging Class-Variables and instantiates a new Fail-Label.</p>
	 * @param collector	The {@link Collector}, that will perform the Actions, which contains 
	 * information, that will be needed elsewhere, too (e.g. connecting, creating a new 
	 * Server, etc.).
	 * @since 1.0
	 */
	public LoginWindow(Collector collector) {
		this.collector = collector;
		this.frame = new JFrame();
		lblFail = new JLabel("");
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #createFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		createFrame();
		frame.setVisible(true);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets {@link #frame} invisible and disabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	@Override
	public void close() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Shows {@link #lblFail} with the given Text on the {@link 
	 * #frame}. If an empty String or {@code null} is given as Parameter, the Label will be 
	 * displayed, but without any Text.</p>
	 * @param text	The text, that will be displayed.
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(lblFail, frame, text).start();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>createFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void createFrame()}</p>
	 * <p style="margin-left: 20px">Creates the Frame.</p>
	 * @since 1.0
	 */
	private void createFrame() {
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
		
		JMultilineLabel lblWelcomescreen = new JMultilineLabel(
				"Welcome to the RaspberryPi Jukebox.\n" +
				"Please enter IP-Address and Port below and click on \"Connect\".\n" + 
				"You could also scan your network for possible Servers by clicking \"UDP "
				+ "Connect\",\n" +
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
		
		JCheckBox chkbxYoutubeDl = new JCheckBox("Update Youtube-dl?");
		frame.getContentPane().add(chkbxYoutubeDl, LoginLayout.UPDATE_YOUTUBEDL_CHECKBOX);
		
		JButton btnUDPConnect = new JButton("UDP Connect");
		btnUDPConnect.setToolTipText("Scans your network for available Servers and "
				+ "connects to them.");
		frame.getContentPane().add(btnUDPConnect, LoginLayout.UDP_BUTTON);
		
		JButton btnInternServer = new JButton("Create own server");
		btnInternServer.setToolTipText("Creates an own Server with the Port in the Port-Field "
				+ "above.");
		frame.getContentPane().add(btnInternServer, LoginLayout.INTERN_SERVER_BUTTON);
		
		txtIp.addMouseListener(new TextFieldListener(new String[] {"IP"}, txtIp));
		txtPort.addMouseListener(new TextFieldListener(new String[] {"Port"}, txtPort));
		btnConnect.addActionListener((ActionEvent ae) -> {new Thread(() -> {
			collector.connect(txtIp.getText(), txtPort.getText());}).start();
		});
		btnUDPConnect.addActionListener(new UDPListener(collector));
		btnInternServer.addActionListener((ActionEvent ae)->{
			collector.createLocalServer(txtPort.getText());
			});
	
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
		util.IO.println(this, "Constructed Frame in " + (System.currentTimeMillis()-start) + 
				"ms.");
	}

	/**
	 * <p style="margin-left: 10px"><em><b>setActive</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setActive(boolean)}</p>
	 * <p style="margin-left: 20px">Sets the State of the Window to the given State. Active, 
	 * if {@code state} is {@code true}, inactive if it is {@code false}.</p>
	 * @param state	The new State of the Window; active, if {@code true}, inactive else.
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 */
	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
}
