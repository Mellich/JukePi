package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import connection.Collector;

/**
 * <p>The Window, where the User can enter the Passwords for Admin- and Player-Permissions.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #SetPasswordWindow(Collector, int)}:
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
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays Messages from the Server.</li>
 * 
 * 			<li>{@link #port}:
 * 				The Port, the new Server will be running on.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class SetPasswordWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private Collector collector}</p>
	 * <p style="margin-left: 20px">The {@link Collector}, that provides additional Methods.
	 * </p>
	 */
	private Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that contains this Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that displays responses from the 
	 * Server.</p>
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>port</b></em></p>
	 * <p style="margin-left: 20px">{@code private int port}</p>
	 * <p style="margin-left: 20px">The Port of the Server.</p>
	 */
	private int port;
	
	/**
	 * <p style="margin-left: 10px"><em><b>SetPasswordWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public SetPasswordWindow(Collector, int)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Window.</p>
	 * @param collector	The {@link Collector}, that provides additional Methods.
	 * @param port	The Port of the Server.
	 * @since 1.0
	 */
	public SetPasswordWindow(Collector collector, int port) {
		frame = new JFrame();
		lblFail = new JLabel();
		this.collector = collector;
		this.port = port;
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
		new util.ShowLabelThread(lblFail, frame, text);
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
	 * <p style="margin-left: 10px"><em><b>createFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void createFrame()}</p>
	 * <p style="margin-left: 20px">Creates the Frame of this Window.</p>
	 * @since 1.0
	 */
	private void createFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Set Passwords");
		frame.setSize(new Dimension(300,200));
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout(10,10));
		
		Container north = new Container();
		north.add(lblFail);
		
		Container center = new Container();
		center.setLayout(new GridLayout(2,2,10,10));
		JLabel lblAdminPW = new JLabel("Admin Password:");
		JTextField txtAdminPassword = new JTextField();
		JLabel lblPlayerPW = new JLabel("Player Password:");
		JTextField txtPlayerPassword = new JTextField();
		center.add(lblAdminPW);
		center.add(txtAdminPassword);
		center.add(lblPlayerPW);
		center.add(txtPlayerPassword);
		
		Container south = new Container();
		JButton btnSet = new JButton("Set Passwords");
		JButton btnSkip = new JButton("Use Standard");
		south.setLayout(new GridLayout(2,1,10,15));
		south.add(btnSet);
		south.add(btnSkip);
		
		content.add(north, BorderLayout.NORTH);
		content.add(center, BorderLayout.CENTER);
		content.add(south, BorderLayout.SOUTH);
		
		btnSet.addActionListener((ActionEvent ae) -> {
			if (!txtAdminPassword.getText().equals("") && 
					!txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, txtAdminPassword.getText(),
						txtPlayerPassword.getText());
			
			else if (txtAdminPassword.getText().equals("") && 
					!txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, "gaplist", 
						txtPlayerPassword.getText());
			
			else if (!txtAdminPassword.getText().equals("") && 
					txtPlayerPassword.getText().equals(""))
				collector.createLocalServerFinal(port, txtAdminPassword.getText(), "player");
			else
				collector.createLocalServerFinal(port, "gaplist", "player");
													});
		btnSkip.addActionListener((ActionEvent ae) -> {collector.createLocalServerFinal(port, 
				"gaplist", "player");});
		
		txtAdminPassword.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					btnSet.doClick();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {} });
		
		txtPlayerPassword.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					btnSet.doClick();
			}
			@Override
			public void keyReleased(KeyEvent arg0) {}
			@Override
			public void keyTyped(KeyEvent arg0) {} });
		
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
