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
 * The Window, where the User can enter the Password, to get Admin Permissions on the Server.
 * @author Haeldeus
 * @version 1.0
 */
public class PasswordWindow extends Window{

	/**
	 * The Frame, that displays this Window.
	 */
	private JFrame frame;
	
	/**
	 * The ServerConnection, that will send Messages to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The Collector, that provides additional Methods.
	 */
	private Collector collector;
	
	/**
	 * The Counter, that will count Wrong Passwords to be able to preventing Spam to the 
	 * Server by connecting as a LowClient after 3 wrong entries.
	 */
	private int counter;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Constructor for the Window.
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @param wrapper	The ServerConnection to the Server.
	 * @param collector	The Collector, that provides additional Methods for the Client.
	 * @since 1.0
	 */
	public PasswordWindow(String ip, int port, JFrame visibleFrame, ServerConnection wrapper, Collector collector) {
		frame = visibleFrame;
		frame.setTitle("JukePi - " +ip+":"+port);
		this.wrapper = wrapper;
		this.collector = collector;
		counter = 0;
	}
	
	@Override
	public void showFail(String text) {
		new util.ShowLabelThread(fail, frame, text).start();
	}

	@Override
	public void show() {
		this.constructFrame();
	}

	@Override
	public void close() {
		frame.setVisible(false);
	}

	/**
	 * Constructs the Frame.
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
					collector.adminConnect(wrapper.getIPAddress(), wrapper.getPort(), password, "UNKNOWN", false);
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

	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);	
	}
}
