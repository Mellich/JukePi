package windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;





import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;


import javax.swing.JLabel;
import javax.swing.JPasswordField;

import connection.Collector;
import messages.Permission;
import client.serverconnection.ServerConnection;
import client.serverconnection.exceptions.PermissionDeniedException;

/**
 * <p>The Window, where the User can enter the Password, to get Admin Permissions on the 
 * Server.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #PasswordWindow(String, int, JFrame, ServerConnection, Collector)}:
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
 * 			<li>{@link #constructFrame()}:
 * 				Creates the Frame and places it's Components to their belonging Spots.</li>
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
 * 				The {@link Collector}, this Window was called from and which provides 
 * 				additional Methods.</li>
 * 
 * 			<li>{@link #counter}:
 * 				The Counter for wrong Password Entries. If this counter exceeds a set Value, 
 * 				the Client will connect as a LowClient.</li>
 * 
 * 			<li>{@link #fail}:
 * 				The {@link JLabel}, that displays Messages from the Server.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that dispalys this Window.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that is used to send and receive 
 * 				Messages to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class PasswordWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, that displays this Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The ServerConnection, that will send Messages to the 
	 * Server.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private Collector collector}</p>
	 * <p style="margin-left: 20px">The Collector, that provides additional Methods.</p>
	 */
	private Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>counter</b></em></p>
	 * <p style="margin-left: 20px">{@code private int counter}</p>
	 * <p style="margin-left: 20px">The Counter, that will count Wrong Passwords to be able to 
	 * preventing Spam to the Server by connecting as a LowClient after 3 wrong entries.</p>
	 */
	private int counter;
	
	/**
	 * <p style="margin-left: 10px"><em><b>fail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel fail}</p>
	 * <p style="margin-left: 20px">The Label, that will display possible Messages.</p>
	 */
	private JLabel fail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>PasswordWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public PasswordWindow(String, int, JFrame, 
	 * ServerConnection, Collector)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Window.</p>
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @param visibleFrame	The Frame, that will be used for this Window.
	 * @param wrapper	The ServerConnection to the Server.
	 * @param collector	The Collector, that provides additional Methods for the Client.
	 * @since 1.0
	 */
	public PasswordWindow(String ip, int port, JFrame visibleFrame, ServerConnection wrapper, 
			Collector collector) {
		frame = visibleFrame;
		frame.setTitle("JukePi - " +ip+":"+port);
		this.wrapper = wrapper;
		this.collector = collector;
		counter = 0;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Shows {@link #fail} with the given Text on the {@link 
	 * #frame}. If an empty String or {@code null} is given as Parameter, the Label will be 
	 * displayed, but without any Text.</p>
	 * @param text	The text, that will be displayed.
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(fail, frame, text).start();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #constructFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		this.constructFrame();
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
	}

	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame}</p>
	 * <p style="margin-left: 20px">Constructs the Frame.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		frame.setSize(new Dimension(300,100));
		frame.setVisible(true);
		frame.setResizable(false);
		
		fail = new JLabel();
		
		Container content = frame.getContentPane();
		content.setLayout(new GridLayout(3,1));
		
		Container south = new Container();
		south.setLayout(new GridLayout(1,3));
		
		Container center = new Container();
		center.setLayout(new GridLayout(1,2));
		
		JPasswordField pwField = new JPasswordField();
		JLabel lblPassword = new JLabel("Enter Password:");
		
		center.add(lblPassword);
		center.add(pwField);
		
		JButton btEnter = new JButton("Enter");
		JButton btSkip = new JButton("Skip");
		JButton btBack = new JButton("Back");
		
		pwField.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					btEnter.doClick();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {} });
		
		btEnter.addActionListener((ActionEvent ae) -> {
			String password = "";
			for (char c : pwField.getPassword())
				password = password+c;
			try {
				if (wrapper.addPermission(Permission.ADMIN, password)) {
					collector.adminConnect(wrapper.getIPAddress(), wrapper.getPort(), 
							password, "UNKNOWN", false);
					this.close();
				}
			}
			catch (PermissionDeniedException e) {
				counter++;
				if (counter <= 2) {
					showFail("Wrong Password! You have " + (3-counter) +" tries left!");
				}
				else {
					collector.lowConnect(wrapper.getIPAddress(), wrapper.getPort());
					this.close();
				}
			}
			});
		
		btSkip.addActionListener((ActionEvent ae) -> {
			collector.lowConnect(wrapper.getIPAddress(), wrapper.getPort());
			this.close();
		});
		
		btBack.addActionListener((ActionEvent ae) -> {
			this.close();
			collector.startUp();
		});
		
		south.add(btBack);
		south.add(btEnter);
		south.add(btSkip);
		
		Container north = new Container();
		north.setLayout(new GridLayout(1,1));
		north.add(fail);
		
		content.add(north);
		content.add(center);
		content.add(south);
		frame.setContentPane(content);
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
