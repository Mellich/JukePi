package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.listener.DebugNotificationListener;

/**
 * The Window, that will display the Debug-Messages
 * @author Haeldeus
 * @version 1.0
 */
public class DebugWindow extends Window implements DebugNotificationListener{

	/**
	 * The Frame, this Class will be working on.
	 */
	private JFrame frame;
	
	/**
	 * The Debug-Messages as an {@link ArrayList}.
	 */
	private ArrayList<String> messages;
	
	/**
	 * The TextArea for the Messages.
	 * @see JTextArea
	 */
	private JTextArea txtDebugs;
	
	/**
	 * Determines, if the Window should record the Messages and print them on the Frame or not.
	 */
	private boolean recording;
	
	/**
	 * The Constructor for the Window.
	 * @since 1.0
	 */
	public DebugWindow() {
		messages = new ArrayList<String>();
		txtDebugs = new JTextArea();
		recording = true;
	}
	
	@Override
	public void showFail(String text) {
		
	}

	@Override
	public void show() {
		createFrame();
	}

	@Override
	public void close() {
		if (frame != null)
			frame.setVisible(false);
	}

	@Override
	public void onClientCountChangedNotify(int newClientCount) {
	//	addNewMessage("New Client Count: " + newClientCount);
	}

	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
	//	addNewMessage("New Player Count: " + newPlayerCount);
	}

	@Override
	public void onNewOutput(String output) {
		addNewMessage(output);
	}
	
	/**
	 * Adds a new Message to {@link #messages}, if {@link #recording} is {@code true}.
	 * @param message	The Message, that might be added.
	 * @since 1.0
	 */
	private synchronized void addNewMessage(String message) {
		if (recording) {
			if (messages.size() < 200)
				messages.add(message);
			else {
				for (int i = 1; i <= messages.size()-1; i++)
					messages.set(i-1, messages.get(i));
				messages.set(messages.size()-1, message);
			}
		
			txtDebugs.setText(messages.get(0));
			for (int i = 1; i <= messages.size()-1; i++)
				txtDebugs.setText(txtDebugs.getText() + "\n"+messages.get(i));
		}
	}
	
	/**
	 * Creates the Frame for the DebugWindow.
	 * @since 1.0
	 */
	private void createFrame() {
		frame = new JFrame();
		frame.setSize(new Dimension(800, 400));
		frame.setTitle("Debugging Tool");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		Container pane = new Container();
		pane.setLayout(new BorderLayout());
		
		JScrollPane scrollPane = new JScrollPane(txtDebugs);
		
		JButton btnStop = new JButton("Stop");
		btnStop.setToolTipText("Stops recording Debug Data");
		
		pane.add(scrollPane, BorderLayout.CENTER);
		pane.add(btnStop, BorderLayout.SOUTH);
		
		frame.setContentPane(pane);
		
		btnStop.addActionListener((ActionEvent ae) -> 	{	if (recording) {
																recording = false;
																btnStop.setText("Start");
																btnStop.setToolTipText("Starts recording Debug Data");
															}
															else {
																recording = true;
																btnStop.setText("Stop");
																btnStop.setToolTipText("Stops recording Debug Data");
															}
														});
		
		frame.setVisible(true);
	}
}
