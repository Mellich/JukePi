package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import client.serverconnection.ServerConnection;
import util.JMultilineLabel;

/**
 * The Instances of this Class will let the User acknowledge, that he is going to discard some
 * changes on the current Gaplist if he proceeds to the Action, he wants to perform. The User
 * may now save the current Gaplist or discard the Changes.
 * @author Haeldeus
 * @version 1.0
 */
public class AcknowledgeWindow extends Window{
	
	/**
	 * The String, that defines the Create-Operation.
	 */
	public static final String CREATE = "create";
	
	/**
	 * The String, that defines the Load-Operation.
	 */
	public static final String LOAD = "load";
	
	/**
	 * The String, that defines the Disconnect- and ShutDown-Operations.
	 */
	public static final String DISCONNECT = "disconnect";
	
	/**
	 * The {@link MainWindow}, that called this instance.
	 */
	private MainWindow mainWindow;
	
	/**
	 * The {@link JFrame}, that inherits the Window.
	 */
	private JFrame frame;
	
	/**
	 * The new {@link Window}, that will be opened by this Window.
	 * @see NewListWindow
	 * @see DisplayGaplistsWindow
	 */
	private Window newWindow;
	
	/**
	 * The String, that defines, which operation will be executed.
	 * @see #CREATE
	 * @see #DISCONNECT
	 * @see #LOAD
	 */
	private String operation;
	
	/**
	 * The {@link ServerConnection} to the Server, that will send the Messages to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The Input, that will be sent to the Server.
	 */
	private String input;
	
	/**
	 * The Constructor for the Window.
	 * @param mainWindow	The {@link MainWindow}, that called this instance.
	 * @param newWindow	The new {@link Window}, that will be opened by this Window.
	 * @param operation	The operation, this Window will perform.
	 * @param wrapper	The {@link ServerConnection} to the Server, that will send the Messages.
	 * @param input	The input, that will be sent to the Server.
	 * @since 1.0
	 */
	public AcknowledgeWindow(MainWindow mainWindow, Window newWindow, String operation, 
			ServerConnection wrapper, String input) {
		this.mainWindow = mainWindow;
		this.frame = new JFrame();
		this.newWindow = newWindow;
		this.operation = operation;
		this.wrapper = wrapper;
	}
	
	@Override
	public void showFail(String text) {
		//Nothing to do here
	}

	@Override
	public void show() {
		this.createFrame();
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
	 * Creates the Frame and all of it's Components.
	 * @since 1.0
	 */
	private void createFrame() {
		frame.setSize(new Dimension(275,100));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		JMultilineLabel lblText = new JMultilineLabel("Do you want to save your Changes to the Gaplist first?");
		content.add(lblText, BorderLayout.CENTER);
		
		Container south = new Container();
		south.setLayout(new GridLayout(1, 3, 10, 0));
		
		JButton btnSave = new JButton("Save");
		JButton btnDiscard = new JButton("Discard");
		JButton btnCancel = new JButton("Cancel");
		
		btnSave.addActionListener(new CustomActionListener("Save", operation, mainWindow, newWindow, wrapper, this, input));
		btnDiscard.addActionListener(new CustomActionListener("Discard", operation, mainWindow, newWindow, wrapper, this, input));
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnSave);
		south.add(btnDiscard);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
	}
}

/**
 * The {@link ActionListener} for the Buttons in the {@link AcknowledgeWindow}.
 * @author Haeldeus
 * @version 1.0
 */
class CustomActionListener implements ActionListener {

	/**
	 * The Name of the Button, this Listener is added to.
	 */
	private String btnName;
	
	/**
	 * The Operation, that will be performed, when the Button was clicked.
	 */
	private String operation;
	
	/**
	 * The MainWindow, that called the AcknowledgeWindow.
	 */
	private MainWindow mainWindow;
	
	/**
	 * The new Window, that may be opened after clicking the Button.
	 */
	private Window newWindow;
	
	/**
	 * The {@link ServerConnection} to the Server, that will send the Messages.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The {@link AcknowledgeWindow}, that inherits the Button, this Listener is added to.
	 */
	private AcknowledgeWindow ackWin;
	
	/**
	 * The Input, that will be sent to the Server.
	 */
	private String input;
	
	/**
	 * The Constructor for the Listener.
	 * @param btnName	The Name of the {@link JButton}, this Listener is added to.
	 * @param operation	The Operation, that will be executed, after clicking the Button.
	 * @param mainWindow	The {@link MainWindow}, that called the parent {@link 
	 * 						AcknowledgeWindow}.
	 * @param newWindow	The new {@link Window}, that will be opened, after clicking this 
	 * 					Button.
	 * @param wrapper	The {@link ServerConnection} to the Server, that will send the 
	 * 					Messages.
	 * @param acknowledgeWindow	The {@link AcknowledgeWindow}, that inherits the {@link 
	 * 							JButton}, this Listener is added to.
	 * @param input	The Input, that will be sent to the Server.
	 * @see AcknowledgeWindow#CREATE
	 * @see AcknowledgeWindow#DISCONNECT
	 * @see AcknowledgeWindow#LOAD
	 * @see DisplayGaplistsWindow
	 * @see NewListWindow
	 * @since 1.0
	 */
	public CustomActionListener(String btnName, String operation, MainWindow mainWindow, 
			Window newWindow, ServerConnection wrapper, AcknowledgeWindow acknowledgeWindow, String input) {
		this.btnName = btnName;
		this.operation = operation;
		this.mainWindow = mainWindow;
		this.newWindow = newWindow;
		this.wrapper = wrapper;
		this.ackWin = acknowledgeWindow;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (btnName.equals("Discard")) {
			if (operation.equals(AcknowledgeWindow.DISCONNECT))
				wrapper.close();
			else if (operation.equals(AcknowledgeWindow.CREATE)) {
				wrapper.switchToGapList(input);
				newWindow.close();
			}
			else if (operation.equals(AcknowledgeWindow.LOAD)) {
				wrapper.switchToGapList(input);
				newWindow.close();
			}
		}
		else {
			if (operation.equals(AcknowledgeWindow.DISCONNECT)) {
				wrapper.saveGapList();
				wrapper.close();
			}
			else if (operation.equals(AcknowledgeWindow.CREATE)) {
				wrapper.saveGapList();
				wrapper.switchToGapList(input);
				newWindow.close();
			}
			else if (operation.equals(AcknowledgeWindow.LOAD)) {
				wrapper.saveGapList();
				wrapper.switchToGapList(input);
				newWindow.close();
			}
		}
		mainWindow.acknowledged();
		ackWin.close();
	}
	
}