package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultCaret;

import client.listener.DebugNotificationListener;
import client.serverconnection.ServerConnection;

/**
 * <p>The Window, that will display the Debug-Messages.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #DebugWindow(ServerConnection)}:
 * 				The Constructor for this Window. Will use the given {@link ServerConnection} 
 * 				to get the current amount of players and Clients connected to the Server.</li> 
 * 		
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #onClientCountChangedNotify(int)}:
 * 				This Method is called, whenever the amount of connected Clients is changed and 
 * 				the Server sent a message to the Client, that this happened. It will set the 
 * 				text of {@link #clientCount} to the new Number.</li>
 * 	
 * 			<li>{@link #onNewOutput(String)}:
 * 				Adds the given {@code String} to {@link #buffer} and adds it to the Pane, if 
 * 				{@link #recording} is {@code true}.</li>
 * 
 * 			<li>{@link #onPlayerCountChangedNotify(int)}:
 * 				This Method is called, whenever the amount of connected Players is changed and 
 * 				the Server sent a message to the Client, that this happened. It will set the 
 * 				text of {@link #playerCount} to the new Number.</li>
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
 * 			<li>{@link #addNewMessage()}:
 * 				Adds the new Message(s) to the end of {@link #txtDebugs} and, if the amount of 
 * 				displayed Messages is above 200, removes the first Messages to match 200 
 * 				Messages.</li>
 * 
 * 			<li>{@link #createFrame()}:
 * 				Creates the {@link #frame} and places it's Components on it.</li>
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
 * 			<li>{@link #buffer}:
 * 				The {@link StringBuilder}, that will buffer the Messages to maintain 
 * 				functionality and correctness of the Messages.</li>
 * 
 * 			<li>{@link #clientCount}:
 * 				The {@link JLabel}, that displays the amount of connected Clients.</li>
 *
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #playerCount}:
 * 				The {@link JLabel}, that displays the amount of connected Players.</li>
 * 
 * 			<li>{@link #recording}:
 * 				The {@code boolean} value, if the Messages will be printed on the screen or 
 * 				not.</li>
 * 
 * 			<li>{@link #scrollPane}:
 * 				The {@link JScrollPane}, that displays the {@link #txtDebugs}.</li>
 * 
 * 			<li>{@link #txtDebugs}:
 * 				The {@link JTextArea}, that shows the Debug-Messages.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class DebugWindow extends Window implements DebugNotificationListener{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, this Class will be working on.</p>
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtDebugs</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextArea txtDebugs}</p>
	 * <p style="margin-left: 20px">The TextArea for the Messages.</p>
	 * @see JTextArea
	 */
	private JTextArea txtDebugs;
	
	/**
	 * <p style="margin-left: 10px"><em><b>recording</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean recording}</p>
	 * <p style="margin-left: 20px">Determines, if the Window should record the Messages and 
	 * print them on the Frame or not.</p>
	 */
	private boolean recording;
	
	/**
	 * <p style="margin-left: 10px"><em><b>scrollPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane scrollPane}</p>
	 * <p style="margin-left: 20px">The ScrollPane, that contains the TextArea for the 
	 * Debug-Messages.</p>
	 * @see DebugWindow#txtDebugs
	 * @see JScrollPane
	 */
	private JScrollPane scrollPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>playerCount</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel playerCount}</p>
	 * <p style="margin-left: 20px">The Label, that displays the amount of current connected 
	 * players.</p>
	 * @see JLabel
	 */
	private JLabel playerCount;
	
	/**
	 * <p style="margin-left: 10px"><em><b>clientCount</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel clientCount}</p>
	 * <p style="margin-left: 20px">The Label, that displays the amount of current connected 
	 * Clients.</p>
	 * @see JLabel
	 */
	private JLabel clientCount;
	
	/**
	 * <p style="margin-left: 10px"><em><b>buffer</b></em></p>
	 * <p style="margin-left: 20px">{@code private StringBuilder buffer}</p>
	 * <p style="margin-left: 20px">The StringBuilder, that will buffer the Messages to 
	 * maintain functionality and correctness of the Messages.</p>
	 * @see StringBuilder
	 */
	private StringBuilder buffer;
	
	/**
	 * <p style="margin-left: 10px"><em><b>DebugWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public DebugWindow(ServerConnection)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Window.</p>
	 * @param serverConnection	The {@link ServerConnection} to the Server.
	 * @since 1.0
	 */
	public DebugWindow(ServerConnection serverConnection) {
		txtDebugs = new JTextArea();
		playerCount = new JLabel(""+serverConnection.getCurrentPlayerCount());
		clientCount = new JLabel(""+serverConnection.getCurrentClientCount());
		recording = true;
		((DefaultCaret) txtDebugs.getCaret()).setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		buffer = new StringBuilder("");
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
		//Nothing to do here
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
		if (frame != null) {
			frame.setVisible(false);
			frame.setEnabled(false);
		}
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onClientCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onClientCountChangedNotify(int)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the Amount of connected Clients 
	 * to the Server has changed and the Server fired the Notify. Will set the Text of 
	 * {@link #clientCount} to the new amount of connected Clients.</p>
	 */
	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		clientCount.setText(""+newClientCount);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onPlayerCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onPlayerCountChangedNotify(int)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the Amount of connected Players 
	 * to the Server has changed and the Server fired the Notify. Will set the Text of 
	 * {@link #playerCount} to the new amount of connected Players.</p>
	 */
	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		playerCount.setText(""+newPlayerCount);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onNewOutput</b></em></p>
	 * <p style="margin-left: 20px">{@code public synchronized void onNewOutput(String)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever a new Output-Line was created. 
	 * This will append that line to the {@link #buffer} and, if {@link #recording} is 
	 * {@code true}, prints it into the {@link #txtDebugs} by calling {@link #addNewMessage()}.
	 * </p>
	 */
	@Override
	public synchronized void onNewOutput(String output) {
		buffer.append(output+"\n");
		if (recording){ 
			addNewMessage();
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addNewMessage</b></em></p>
	 * <p style="margin-left: 20px">{@code private synchronized void addNewMessage()}</p>
	 * <p style="margin-left: 20px">Adds a new Message to {@link #messages}, if 
	 * {@link #recording} is {@code true}.</p>
	 * @since 1.0
	 */
	private synchronized void addNewMessage() {	
		if (buffer.length() > 0){
			txtDebugs.append(buffer.toString());
			buffer.setLength(0);
			if (txtDebugs.getLineCount() > 200){
				int diff = txtDebugs.getLineCount() - 200;
				try {
					txtDebugs.replaceRange("", 0, txtDebugs.getLineEndOffset(diff - 1));
				} catch (BadLocationException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>createFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void createFrame()}</p>
	 * <p style="margin-left: 20px">Creates the Frame for the DebugWindow.</p>
	 * @since 1.0
	 */
	private void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(800, 400));
		frame.setTitle("Debugging Tool");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container pane = new Container();
		pane.setLayout(new BorderLayout());
		
		txtDebugs.setEditable(false);
		scrollPane = new JScrollPane(txtDebugs);
		JScrollBar sb = scrollPane.getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
		
		JButton btnStop = new JButton("Stop");
		btnStop.setToolTipText("Stops recording Debug Data");
		
		pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(btnStop, BorderLayout.SOUTH);
		
		Container clientCounts = new Container();
		clientCounts.setLayout(new GridLayout(1,2));
		
		Container left = new Container();
		left.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		left.add(new JLabel("Connected clients: "));
		left.add(clientCount);
		
		Container right = new Container();
		right.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		right.add(new JLabel("Connected player: "));
		right.add(playerCount);
		
		clientCounts.add(left);
		clientCounts.add(right);
		
		pane.add(clientCounts,BorderLayout.NORTH);
		
		frame.setContentPane(pane);
		
		btnStop.addActionListener((ActionEvent ae) -> 	{
			if (recording) {
				recording = false;
				btnStop.setText("Start");
				btnStop.setToolTipText("Starts recording Debug Data");
			}
			else {
				recording = true;
				btnStop.setText("Stop");
				btnStop.setToolTipText("Stops recording Debug Data");
				addNewMessage();
			}
		});
		
		frame.setVisible(true);
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
