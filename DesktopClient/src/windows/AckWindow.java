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
 * The Window, that will let the User save or discard the saves he made to the Gaplist.
 * @author Haeldeus
 * @version 1.0
 */
public class AckWindow extends Window{

	/**
	 * The {@link JFrame}, that inherits this Window.
	 */
	private JFrame frame;
	
	/**
	 * The {@link ServerConnection} to the Server, that will send the Messages.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The {@link MainWindow}, this Window was called from.
	 */
	private MainWindow mainWindow;
	
	/**
	 * The Input, that will be sent to the Server, when the Server should load a Gaplist or 
	 * create a new one.
	 */
	private String input;
	
	/**
	 * The Window, that this Window is called from, that is not the MainWindow. This is used,
	 *  when the User tries to load a Gaplist or create a new one.
	 */
	private Window newWindow;
	
	/**
	 * The Constructor for the Window.
	 * @param wrapper	The {@link ServerConnection} to the Server.
	 * @param mainWindow	The {@link MainWindow}, that is the parent or grand-parent (if 
	 * 						this window was called before creating or loading a new List) of 
	 * 						this Window.
	 * @param input	The Name of the new Gaplist to be either loaded or created by the Server.
	 * @param newWindow	The Window, this Window was called from, if it wasn't the 
	 * 					{@link #mainWindow}.
	 * @since 1.0
	 */
	public AckWindow(ServerConnection wrapper, MainWindow mainWindow, String input, Window newWindow) {
		this.frame = new JFrame();
		this.wrapper = wrapper;
		this.mainWindow = mainWindow;
		this.input = input;
		this.newWindow = newWindow;
	}
	
	@Override
	public void showFail(String text) {
		// Nothing to do here.
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
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}

	/**
	 * Constructs the Frame and it's components.
	 * @since 1.0
	 */
	private void constructFrame() {
		frame.setSize(new Dimension(300,100));
		frame.setTitle("Save Gaplist?");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container content = frame.getContentPane();
		
		content.setLayout(new BorderLayout());
		JMultilineLabel lblText = new JMultilineLabel("Do you want to save the Changes for the Gaplist?");
		content.add(lblText, BorderLayout.CENTER);
		
		Container south = new Container();
		south.setLayout(new GridLayout(1, 3, 10, 10));
		
		JButton btnSave = new JButton("Save");
		btnSave.addActionListener((ActionEvent ae) -> {mainWindow.acknowledged();wrapper.saveGapList(); sendMessage();});
		JButton btnDiscard = new JButton("Discard");
		btnDiscard.addActionListener((ActionEvent ae) -> {mainWindow.acknowledged();sendMessage();});
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnSave);
		south.add(btnDiscard);
		south.add(btnCancel);
		content.add(south, BorderLayout.SOUTH);
		frame.setContentPane(content);
	}
	
	/**
	 * Sends the Input to the Server and closes the Window afterwards.
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

