package windows;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import util.layouts.DisplayGaplistsLayout;
import util.tasks.SetContentTask;
import util.tasks.SetSavedGaplistsTask;
import client.listener.GapListNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * <p>The Class, whose Objects will display the Gaplists saved on the Server.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #DisplayGaplistsWindow(ServerConnection, MainWindow, String[])}:
 * 				The Constructor for this Window. Will set the given Parameters to their 
 * 				Instance Variables.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #doneContentUpdate(JFrame, JScrollPane)}:
 * 				Is called by the {@link SetContentTask}, after it finished it's Task. Will 
 * 				update the Parameters, that were edited by the Task.</li>
 * 
 * 			<li>{@link #doneSavedListsUpdate(JFrame, JScrollPane)}:
 * 				Is called by the {@link SetSavedGaplistsTask}, after it finished it's Task. 
 * 				Will update the Parameters, that were edited by that Task.</li>
 * 
 * 			<li>{@link #onGapListChangedNotify(String)}:
 * 				Is called, when the Server notified the Client, that another Gaplist was 
 * 				loaded on the Server. Does nothing, as there is nothing to edit in this Window 
 * 				in this case.</li>
 * 
 * 			<li>{@link #onGapListCountChangedNotify(String[])}:
 * 				Is called, when the Amount of saved Gaplists was changed on the Server. This 
 * 				Method will update the {@link #gaplistsPane} and fills it with the new 
 * 				Gaplists.</li>
 * 
 * 			<li>{@link #onGapListUpdatedNotify(Song[])}:
 * 				Is called, when the current Gaplist was edited. Will rebuild the 
 * 				{@link #contentPane} by calling {@link #showGaplist(String)}, in case the 
 * 				current Gaplist is the current displayed List.</li>
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
 * 				Creates the Frame and puts it's Components to their belonging Spots.</li>
 * 
 * 			<li>{@link #loadGaplist(String)}:
 * 				Loads the Gaplist with the given Name.</li>
 * 
 * 			<li>{@link #removeGaplist(String)}:
 * 				Removes the Gaplist with the given Name from the Server.</li>
 * 
 * 			<li>{@link #showGaplist(String)}:
 * 				Shows the Content of the given Gaplist in the {@link #contentPane}.</li>
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
 * 			<li>{@link #contentPane}:
 * 				The {@link JScrollPane}, that displays the Content of the selected Gaplist.
 * 				</li>
 * 
 * 			<li>{@link #currentShown}:
 * 				The Name of the current displayed Gaplist.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #gaplists}:
 * 				The Gaplists, that are saved on the Server as an Array of {@code String}s.</li>
 * 
 * 			<li>{@link #gaplistsPane}:
 * 				The {@link JScrollPane}, that displays the Gaplists, that are saved on the 
 * 				Server.</li>
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays possible Messages from the Server.</li>
 * 
 * 			<li>{@link #mainWindow}:
 * 				The {@link MainWindow}, that is the parent Window for this Window.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server that sends/receives Messages 
 * 				to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.1
 */
public class DisplayGaplistsWindow extends Window implements GapListNotificationListener{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The {@link JFrame}, that displays this Window.</p>
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection} to the Server, that is 
	 * capable of sending and receiving Messages.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mainWindow}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow}, this Object was called from.</p>
	 */
	private MainWindow mainWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplistsPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane gaplistsPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that will display all Gaplists 
	 * saved on the Server.</p>
	 */
	private JScrollPane gaplistsPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>contentPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane contentPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that will display the Songs, the 
	 * asked Gaplist contains.</p>
	 */
	private JScrollPane contentPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The {@link JLabel}, that will display Messages from the 
	 * Server.</p>
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplists</b></em></p>
	 * <p style="margin-left: 20px">{@code private String[] gaplists}</p>
	 * <p style="margin-left: 20px">The Gaplists, saved on the Server as an Array of Strings.
	 * </p>
	 */
	private String[] gaplists;
	
	/**
	 * <p style="margin-left: 10px"><em><b>currentShown</b></em></p>
	 * <p style="margin-left: 20px">{@code private String currentShown}</p>
	 * <p style="margin-left: 20px">The Name of the current displayed Gaplist.</p>
	 */
	private String currentShown;
	
	/**
	 * <p style="margin-left: 10px"><em><b>DisplayGaplistsWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public DisplayGaplistsWindow(ServerConnection, 
	 * MainWindow, String[])}</p>
	 * <p style="margin-left: 20px">The Constructor for creating an Object of this Window.</p>
	 * @param wrapper	The {@link ServerConnection}, that will send Messages to the Server.
	 * @param mw	The {@link MainWindow}, this Object was called from.
	 * @param gaplists	The Gaplists, saved on the Server, as an Array of Strings.
	 * @since 1.0
	 */
	public DisplayGaplistsWindow(ServerConnection wrapper, MainWindow mw, String[] gaplists) {
		frame = new JFrame();
		lblFail = new JLabel();
		this.wrapper = wrapper;
		this.mainWindow = mw;
		this.gaplists = gaplists;
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
		new util.ShowLabelThread(lblFail, frame, text).start();
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
	 * <p style="margin-left: 10px"><em><b>createFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void createFrame()}</p>
	 * <p style="margin-left: 20px">Creates the Frame for the Window and adds all Components 
	 * to its ContentPane.</p>
	 * @since 1.0
	 */
	private void createFrame() {
		frame.setSize(new Dimension(550,300));
		frame.setTitle("Saved Gaplists");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().removeAll();
		
		Container content = frame.getContentPane();
		content.setLayout(new DisplayGaplistsLayout());
		
		content.add(lblFail, DisplayGaplistsLayout.FAIL_LABEL);
		
		JLabel lblSavedGaplists = new JLabel("Saved Gaplists");
		lblSavedGaplists.setHorizontalAlignment(JLabel.CENTER);
		content.add(lblSavedGaplists, DisplayGaplistsLayout.SAVED_GAPLISTS_LABEL);
		
		JLabel lblContent = new JLabel("Content:");
		lblContent.setHorizontalAlignment(JLabel.CENTER);
		content.add(lblContent, DisplayGaplistsLayout.CONTENT_LABEL);
		
		JButton btnLoad = new JButton("Load");
		content.add(btnLoad, DisplayGaplistsLayout.LOAD_BUTTON);
		
		JButton btnDelete = new JButton("Delete");
		content.add(btnDelete, DisplayGaplistsLayout.DELETE_BUTTON);
		
		JButton btnCreate = new JButton("Create New");
		content.add(btnCreate, DisplayGaplistsLayout.CREATE_NEW_BUTTON);
		
		contentPane = new JScrollPane();
		content.add(contentPane, DisplayGaplistsLayout.CONTENT_PANE);
		
		gaplistsPane = new JScrollPane();
		content.add(gaplistsPane, DisplayGaplistsLayout.GAPLISTS_PANE);

		btnDelete.addActionListener((ActionEvent ae) -> {
			if (((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0))
					.getSelectedRow() >= 0)	
				removeGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0))
						.getComponent(0)).getValueAt(((JTable) ((JViewport) 
								gaplistsPane.getComponent(0)).getComponent(0))
								.getSelectedRow(), 0))); 
			else 
				showFail("Select a Gaplist first.");
			});
		
		btnLoad.addActionListener((ActionEvent ae) -> {
			if (((JTable) ((JViewport) gaplistsPane.getComponent(0)).getComponent(0))
					.getSelectedRow() >= 0) {
				loadGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0))
						.getComponent(0)).getValueAt(((JTable) ((JViewport) 
								gaplistsPane.getComponent(0)).getComponent(0))
								.getSelectedRow(), 0))); 
				this.close();
			}
			else 
				showFail("Select a Gaplist first.");});
		
		btnCreate.addActionListener((ActionEvent ae) -> {
			new NewListWindow(wrapper, mainWindow).show();
		});

		new SetSavedGaplistsTask(frame, gaplists, gaplistsPane, this).execute();
		new SetContentTask(new Song[] {}, contentPane, frame, this).execute();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>loadGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private void loadGaplist(String)}</p>
	 * <p style="margin-left: 20px">Loads the Gaplist with the given Name.</p>
	 * @param name	The Name of the Gaplist to be loaded.
	 * @since 1.0
	 */
	private void loadGaplist(String name) {
		if (!mainWindow.getChanged())
			wrapper.switchToGapList((String[] s) -> {
				if (s[0].equals("true"))
					showFail("Loaded Gaplist.");
				else
					showFail("Couldn't load the Gaplist.");
			}, name);
		else
			new AckWindow(wrapper, mainWindow, name, this).show();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>removeGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private void removeGaplist(String)}</p>
	 * <p style="margin-left: 20px">Removes the Gaplist with the given Name from the Server.
	 * </p>
	 * @param name	The Gaplist to be deleted.
	 * @since 1.0
	 */
	private void removeGaplist(String name) {
		wrapper.deleteGapList((String[] s) -> {	if (s[0].equals("true"))
													showFail("Removed the Gaplist.");
												else
													showFail("Couldn't remove the Gaplist");
											  }, name);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>showGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private void showGaplist(String)}</p>
	 * <p style="margin-left: 20px">Shows the Gaplist with the given Name in the 
	 * {@link #contentPane}.</p>
	 * @param name	The Gaplist to be shown.
	 * @since 1.0
	 */
	private void showGaplist(String name) {
		currentShown = name;
		wrapper.getTitleFromGapList((String[] s) -> {
			Song[] songs = new Song[s.length/2];
			for (int i = 0; i < s.length; i = i+2) {
				songs[i/2] = new Song(-1, s[i], 0, false, null, s[i+1]);
			}
			new SetContentTask(songs, contentPane, frame, this).execute();
		}, name);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListChangedNotify(String)}</p>
	 * <p style="margin-left: 20px">Called, when a new Gaplist was loaded on the Server. 
	 * Nothing will be done here, as this change has no influence on this Window.</p>
	 */
	@Override
	public void onGapListChangedNotify(String gapListName) {
		//Nothing to do here
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListUpdatedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListUpdatedNotify(Song[]}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the current Gaplist was edited. 
	 * Will call {@link #showGaplist(String)}, to rebuild the {@link #contentPane}, in case 
	 * the current Gaplist is the current displayed List.</p>
	 */
	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		showGaplist(currentShown);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>doneSavedListsUpdate</b></em></p>
	 * <p style="margin-left: 20px">{@code public void doneSavedListsUpdate(JFrame, 
	 * JScrollPane)}</p>
	 * <p style="margin-left: 20px">This Method is called by the {@link SetSavedGaplistsTask}, 
	 * when the Gaplists are loaded.</p>
	 * @param frame	The {@link JFrame}, that displays this Window and was edited by the Task.
	 * @param gaplistsPane	The {@link JScrollPane}, that contains all saved Gaplists.
	 * @since 1.0
	 */
	public void doneSavedListsUpdate(JFrame frame, JScrollPane gaplistsPane) {
		this.frame = frame;
		this.frame.remove(this.gaplistsPane);
		this.gaplistsPane = gaplistsPane;
		this.frame.revalidate();
		JTable table = 
				(JTable) ((JViewport) this.gaplistsPane.getComponent(0)).getComponent(0);
		table.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent event) {
				if (event.getClickCount() == 1)
					showGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0))
							.getComponent(0)).getValueAt(((JTable) ((JViewport) 
									gaplistsPane.getComponent(0)).getComponent(0))
									.getSelectedRow(), 0)));
				else if (event.getClickCount() == 2) {
					loadGaplist((String)(((JTable) ((JViewport) gaplistsPane.getComponent(0))
							.getComponent(0)).getValueAt(((JTable) ((JViewport) 
									gaplistsPane.getComponent(0)).getComponent(0))
									.getSelectedRow(), 0))); 
					close();
				  }
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {}

			@Override
			public void mouseExited(MouseEvent arg0) {}

			@Override
			public void mousePressed(MouseEvent arg0) {}

			@Override
			public void mouseReleased(MouseEvent arg0) {} });
	}

	/**
	 * <p style="margin-left: 10px"><em><b>doneContentUpdate</b></em></p>
	 * <p style="margin-left: 20px">{@code public void doneContentUpdate(JFrame, JScrollPane)}
	 * </p>
	 * <p style="margin-left: 20px">This Method is called by the {@link SetContentTask}, when 
	 * the Content was loaded into the Pane.</p>
	 * @param frame	The {@link JFrame}, that displays this Window and was edited by the Task.
	 * @param contentPane	The {@link JScrollPane}, that contains the Content.
	 * @since 1.0
	 */
	public void doneContentUpdate(JFrame frame, JScrollPane contentPane) {
		this.frame = frame;
		this.frame.remove(this.contentPane);
		this.contentPane = contentPane;
		this.frame.revalidate();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListCountChangedNotify(String[])}
	 * </p>
	 * <p style="margin-left: 20px">Is called, whenever the amount of Gaplists was changed on 
	 * the Server. Will rebuild the {@link #gaplistsPane} to display all Gaplists.</p>
	 */
	@Override
	public void onGapListCountChangedNotify(String[] gaplists) {
		new SetSavedGaplistsTask(frame, gaplists, gaplistsPane, this).execute();
	}
}
