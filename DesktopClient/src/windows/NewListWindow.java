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







import client.serverconnection.ServerConnection;

/**
 * <p>The Window, that will be opened, when a new Gaplist should be added to the Server.</p>
 * <h3>Provided Methods:</h3> 
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #NewListWindow(ServerConnection, MainWindow)}:
 * 				The Constructor for the Window. Creates a new {@link JFrame} and sets the 
 * 				Fields to their given Parameter Values.</li>
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
 * 				Fills the {@link #frame} with the needed Components and adds Listener to each 
 * 				Button.</li>
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
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that inherits this Window.</li>
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays responses from the Server and other 
 * 				important Information.</li>
 * 
 * 			<li>{@link #mainWindow}:
 * 				The {@link MainWindow}, that is the parent Window of this NewListWindow.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that will send and receive Message 
 * 				to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class NewListWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays this Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send Messages to 
	 * the Server.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that will display responses from the 
	 * Server.</p>
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mainWindow}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow}, that called this Window.</p>
	 */
	private MainWindow mainWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>NewListWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public NewListWindow(ServerConnection, MainWindow)}
	 * </p>
	 * <p style="margin-left: 20px">The Constructor for this Window.</p>
	 * @param wrapper	The {@link ServerConnection}, that will send Messages to the Server.
	 * @param mw	The {@link MainWindow}, that called this Window.
	 * @since 1.0
	 */
	public NewListWindow(ServerConnection wrapper, MainWindow mw) {
		frame = new JFrame();
		this.wrapper = wrapper;
		lblFail = new JLabel("Enter a Name for a new Gaplist");
		this.mainWindow = mw;
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
		new Thread(() -> {
			lblFail.setText(text);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			if (lblFail.getText().equals(text))
				lblFail.setText("Enter a Name for a new Gaplist");
			}).start();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #createFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}. Also, sets the {@link #mainWindow} inactive, by calling 
	 * {@link MainWindow#setActive(boolean)} with {@code false} as Parameter.</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		mainWindow.setActive(false);
		createFrame();
		frame.setVisible(true);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets {@link #frame} invisible and disabled. Also sets the 
	 * MainWindow active, by calling {@link MainWindow#setActive(boolean)} with {@code true} 
	 * as Parameter.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	@Override
	public void close() {
		frame.setVisible(false);
		mainWindow.setActive(true);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>createFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void createFrame()}</p>
	 * <p style="margin-left: 20px">Creates the Frame, that contains this Window.</p>
	 * @since 1.0
	 */
	private void createFrame() {
		frame.setSize(new Dimension(300,100));
		frame.setTitle("Creating a new Gaplist...");
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	mainWindow.setActive(true);
		        frame.setVisible(false);
		    }
		});
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		content.add(lblFail, BorderLayout.NORTH);
		
		JTextField txtName = new JTextField();
		content.add(txtName, BorderLayout.CENTER);
		
		Container south = new Container();
		south.setLayout(new GridLayout(1, 2, 20 , 10));
		
		JButton btnCreate = new JButton("Create");
		JButton btnCancel = new JButton("Cancel");
		
		txtName.addKeyListener(new KeyListener() {
			@Override
			public void keyPressed(KeyEvent arg0) {
				if (arg0.getExtendedKeyCode() == 10)
					btnCreate.doClick();
			}

			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyTyped(KeyEvent e) {}});
		
		btnCreate.addActionListener((ActionEvent ae) -> {
			if (!txtName.getText().equals("")) {
				if (!mainWindow.getChanged())
					wrapper.switchToGapList((String[] s) -> {
						if (s[0].equals("true")) {
							showFail("Created a new Gaplist.");
							this.close();
						}
						else
							showFail("Failed to create a new Gaplist.");
					}, txtName.getText());
				else
					new AckWindow(wrapper, mainWindow, txtName.getText(), this).show();
			}
			else {
				showFail("Please enter a name first");
			}
		});
		
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnCreate);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
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
