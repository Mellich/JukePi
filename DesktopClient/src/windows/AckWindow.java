package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import client.serverconnection.ServerConnection;
import util.JMultilineLabel;

/**
 * <p>The Window, that will let the User save or discard the saves he made to the Gaplist.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #AckWindow(ServerConnection, MainWindow, String, Window)}:
 * 				The Constructor for this Window. Will set the given Parameter to their 
 * 				belonging Instance Variables.</li>
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
 * 				As there are no Messages to be displayed, this Method does nothing.</li>
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
 * 				Constructs the Frame and puts it's Components to their belonging Spots.</li>
 * 		
 * 			<li>{@link #sendMessage()}:
 * 				Sends the Message to the Server and closes the Windows afterwards. The 
 * 				Message varies, depending on what Action caused this Window to open.</li>
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
 *	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window and it's belonging Components.
 * 				</li>
 * 
 * 			<li>{@link #input}:
 * 				The {@code String}, that will be sent to the Server, when {@link 
 * 				#sendMessage()} is called.</li>
 * 
 * 			<li>{@link #mainWindow}:
 * 				The {@link MainWindow}, that is the parent or grand-parent of this Window.
 * 				</li>
 * 
 * 			<li>{@link #newWindow}:
 * 				The Window, that might be the parent of this Window, if it wasn't opened by 
 * 				the {@link #mainWindow}.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server. Will send the Messages to the 
 * 				Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class AckWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that inherits this Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection} to the Server, that will 
	 * send the Messages.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mainWindow}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow}, this Window was called from.</p>
	 */
	private MainWindow mainWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>input</b></em></p>
	 * <p style="margin-left: 20px">{@code private String input}</p>
	 * <p style="margin-left: 20px">The {@code String}, that will be sent to the Server, when 
	 * the Server should load a Gaplist or create a new one.</p>
	 */
	private String input;
	
	/**
	 * <p style="margin-left: 10px"><em><b>newWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private Window newWindow}</p>
	 * <p style="margin-left: 20px">The Window, that this Window is called from, that is not 
	 * the MainWindow. This is used, when the User tries to load a Gaplist or create a new 
	 * one.</p>
	 */
	private Window newWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>AckWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public AckWindow(ServerConnection, MainWindow, 
	 * String, Window)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Window.</p>
	 * @param wrapper	The {@link ServerConnection} to the Server.
	 * @param mainWindow	The {@link MainWindow}, that is the parent or grand-parent (if 
	 * 						this window was called before creating or loading a new List) of 
	 * 						this Window.
	 * @param input	The Name of the new Gaplist to be either loaded or created by the Server.
	 * @param newWindow	The Window, this Window was called from, if it wasn't the 
	 * 					{@link #mainWindow}.
	 * @since 1.0
	 */
	public AckWindow(ServerConnection wrapper, MainWindow mainWindow, String input, 
			Window newWindow) {
		this.frame = new JFrame();
		this.wrapper = wrapper;
		this.mainWindow = mainWindow;
		this.input = input;
		this.newWindow = newWindow;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Would show the given {@code String} on the Screen, but as 
	 * there are no Messages to be displayed by this Window, this Method does nothing.</p>
	 * @param text	The {@code String}, that would have been displayed
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		// Nothing to do here.
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
		constructFrame();
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

	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame()}</p>
	 * <p style="margin-left: 20px">Constructs the Frame and it's components.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		frame.setSize(new Dimension(300,100));
		frame.setTitle("Save Gaplist?");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container content = frame.getContentPane();
		
		content.setLayout(new BorderLayout());
		JMultilineLabel lblText = new JMultilineLabel(
				"Do you want to save the Changes for the Gaplist?");
		content.add(lblText, BorderLayout.CENTER);
		
		Container south = new Container();
		south.setLayout(new GridLayout(1, 3, 10, 10));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener((ActionEvent ae) -> {
			mainWindow.acknowledged();
			wrapper.saveGapList(); 
			sendMessage();
			});
		
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener((ActionEvent ae) -> {
			mainWindow.acknowledged();
			sendMessage();
			});
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnSave);
		south.add(btnDiscard);
		south.add(btnCancel);
		content.add(south, BorderLayout.SOUTH);
		frame.setContentPane(content);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>sendMessage</b></em></p>
	 * <p style="margin-left: 20px">{@code private void sendMessage()}</p>
	 * <p style="margin-left: 20px">Sends the Input to the Server and closes the Window 
	 * afterwards.</p>
	 * @since 1.0
	 */
	private void sendMessage() {
		if (input.equals(""))
			wrapper.close();
		else if (input.equals("CLOSE"))
			System.exit(0);
		else {
			wrapper.switchToGapList(input);
			newWindow.close();
		}
		this.close();
	}
}
