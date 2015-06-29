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

public class DebugWindow extends Window implements DebugNotificationListener{

	private JFrame frame;
	
	private ArrayList<String> messages;
	
	private JTextArea txtDebugs;
	
	private boolean recording;
	
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
		frame.setVisible(false);
	}

	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		addNewMessage("New Client Count: " + newClientCount);
	}

	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		addNewMessage("New Player Count: " + newPlayerCount);
	}

	@Override
	public void onNewOutput(String output) {
		addNewMessage(output);
	}
	
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
