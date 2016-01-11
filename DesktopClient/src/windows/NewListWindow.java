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
 * The Window, that will be opened, when a new Gaplist should be added to the Server.
 * @author Haeldeus
 * @version 1.0
 */
public class NewListWindow extends Window{

	/**
	 * The {@link JFrame}, that displays this Window.
	 */
	private JFrame frame;
	
	/**
	 * The {@link ServerConnection}, that will send Messages to the Server.
	 */
	private ServerConnection wrapper;
	
	/**
	 * The {@link JLabel}, that will display responses from the Server.
	 */
	private JLabel lblFail;
	
	/**
	 * The {@link MainWindow}, that called this Window.
	 */
	private MainWindow mw;
	
	/**
	 * Determines, if the Gaplist was changed.
	 */
	private boolean changed;
	
	/**
	 * The Constructor for this Window.
	 * @param wrapper	The {@link ServerConnection}, that will send Messages to the Server.
	 * @param mw	The {@link MainWindow}, that called this Window.
	 * @since 1.0
	 */
	public NewListWindow(ServerConnection wrapper, MainWindow mw, boolean changed) {
		frame = new JFrame();
		this.wrapper = wrapper;
		lblFail = new JLabel("Enter a Name for a new Gaplist");
		this.mw = mw;
		this.changed = changed;
	}
	
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

	@Override
	public void show() {
		mw.setActive(false);
		createFrame();
		frame.setVisible(true);
	}

	@Override
	public void close() {
		frame.setVisible(false);
		mw.setActive(true);
	}

	/**
	 * Creates the Frame, that contains this Window.
	 * @since 1.0
	 */
	private void createFrame() {
		frame.setSize(new Dimension(300,100));
		frame.setTitle("Creating a new Gaplist...");
		frame.addWindowListener(new java.awt.event.WindowAdapter() {
		    @Override
		    public void windowClosing(java.awt.event.WindowEvent windowEvent) {
		    	mw.setActive(true);
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
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}});
		
		btnCreate.addActionListener((ActionEvent ae) -> {
			if (!changed) {
				if (!txtName.getText().equals("")) {
					wrapper.switchToGapList((String[] s) -> {	if (s[0].equals("true")) {
																	showFail("Created a new Gaplist.");
																	this.close();
																}
																else
																	showFail("Failed to create a new Gaplist.");
															}, txtName.getText());
				}
				else {
					showFail("Please enter a name first");
				}
			}
			else {
				if (!txtName.getText().equals("")) {
					new AcknowledgeWindow(mw, this, AcknowledgeWindow.CREATE, wrapper, txtName.getText()).show();
				}
			}
		});
		
		btnCancel.addActionListener((ActionEvent ae) -> {this.close();});
		
		south.add(btnCreate);
		south.add(btnCancel);
		
		content.add(south, BorderLayout.SOUTH);
		
		frame.setContentPane(content);
	}

	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
	
	/**
	 * Sets the Changed-Status to the given boolean value.
	 * @param changed	The new Status of changed.
	 * @since 1.0
	 */
	public void setChanged(boolean changed) {
		this.changed = changed;
	}
	
}
