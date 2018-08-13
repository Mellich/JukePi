package windows;

import util.MyAdapter;
import util.TextFieldListener;
import util.PopClickListener;
import util.layouts.NewClientLayout;
import util.tasks.SetGaplistTask;
import util.tasks.SetWishlistTask;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButton;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.KeyStroke;
import javax.swing.text.BadLocationException;

import client.listener.DebugNotificationListener;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.listener.PauseResumeNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import connection.Collector;

/**
 * <p>The Main {@link Window}, that contains information transmitted by the Server, this 
 * Client is connected to.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #MainWindow(Collector, ServerConnection, String, int, String, 
 * 					String, boolean)}:
 * 				The Constructor for this Window. Will set the Parameter to their belonging 
 * 				Instance Variables.</li>
 * 			
 * 			<li>{@link #acknowledged()}:
 * 				Will be called by the {@link AckWindow}, when the User clicked "Save" or 
 * 				"Discard". Will set {@link #changed} to {@code false}, since the User has 
 * 				been acknowledged, that there were changes to the Gaplist and he either 
 * 				saved them or discarded them. Also, most of the times, a new Gaplist will be 
 * 				loaded, so this value needs to be reseted.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #doneGaplistUpdate(Song[], JLabel, JFrame, JScrollPane)}:
 * 				Will be called by the {@link SetGaplistTask} when the {@link 
 * 				#oldGaplistPane} was updated by it. Is used to validate the Window and 
 * 				update the given parameters.</li>
 * 
 * 			<li>{@link #doneWishlistUpdate(Song[], JLabel, JFrame, JScrollPane)}:
 * 				Will be called by the {@link SetWishlistTask} when the {@link #oldPane} was 
 * 				updated by it. Is used to validate the Window and update the given 
 * 				parameters.</li>
 * 
 * 			<li>{@link #getChanged()}:
 * 				Returns the Value of {@link #changed}. Is used by other Windows to check, if 
 * 				the Gaplist was edited before performing actions, that would discard this 
 * 				changes automatically.</li>
 * 
 * 			<li>{@link #onClientCountChangedNotify(int)}:
 * 				This Method will be called, when the amount of connected Clients to the 
 * 				Server has changed. Since there is nothing to do in that case, this Method 
 * 				does nothing.</li>
 * 
 * 			<li>{@link #onDisconnect()}:
 * 				Will be called, whenever the Client was disconnected from the Server without 
 * 				the User's Permission (e.g. Loss of Connection). Will call 
 * 				{@link Collector#disconnect()}.</li> 
 * 
 * 			<li>{@link #onGapListChangedNotify(String)}:
 * 				Will be called, when a new Gaplist was loaded on the Server. This will 
 * 				update {@link #lblGaplistName} to show the new Name and prints a Message on 
 * 				the Screen.</li>
 * 
 * 			<li>{@link #onGapListCountChangedNotify(String[])}:
 * 				Will be called, when the amount of Gaplists was changed. This will update 
 * 				{@link #gaplists} and acknowledge the User by printing a Message on the 
 * 				Screen.</li>
 * 
 * 			<li>{@link #onGapListUpdatedNotify(Song[])}:
 * 				Will be called, whenever the current Gaplist was updated. This happens 
 * 				every time a new Songs is played or a Song was deleted or moved. It will 
 * 				update the {@link #oldGaplistPane} by executing a new {@link SetGaplistTask} 
 * 				and updates the User via {@link #showFail(String)}.</li>
 * 
 * 			<li>{@link #onNewOutput(String)}:
 * 				Will be called, whenever the Server sends a new Output to the Client. This 
 * 				will print the Message into {@link #txtDebugs} to display it on the Screen.
 * 				</li>
 * 
 * 			<li>{@link #onNextTrackNotify(String, String, boolean)}:
 * 				Will be called, whenever a new Track is played on the Server. Updates {@link 
 * 				#lblPlayingTrack} and {@link #lblTrackNext}, as well as updating the User, 
 * 				that a new Song is played.</li>
 * 
 * 			<li>{@link #onPauseResumeNotify(boolean)}:
 * 				Will be called, when the current Song was paused or resumed. Updates {@link 
 * 				#btnPlayPause} and {@link #menuPlayPause} and prints a Message on the Screen.
 * 				</li>
 * 
 * 			<li>{@link #onPlayerCountChangedNotify(int)}:
 * 				Will be called, when the amount of connected Players to the Server has 
 * 				changed. As there is nothing to display, this Method doesn't do anything.
 * 				</li>
 * 
 * 			<li>{@link #onWishListUpdatedNotify(Song[])}:
 * 				Will be called, whenever the Wishlist was updated. Updates {@link 
 * 				#lblTrackNext} and {@link #oldPane}. Also notifies the User about this.</li>
 * 
 * 			<li>{@link #setActive(boolean)}:
 * 				Sets the state of the Window, depending on the given {@code boolean}, either 
 * 				enabled or disabled.</li>
 * 
 * 			<li>{@link #setIpAndPort(String, int)}:
 * 				Updates the Title of {@link #frame} to display these Values.</li>
 * 
 * 			<li>{@link #setLanguage(String)}:
 * 				Sets the Language of the Client to the given Language.</li>
 * 
 * 			<li>{@link #setSelectedGaplistIndex(int)}:
 * 				Is used to set the SelectionIntervall of the {@link #oldGaplistPane} to the 
 * 				given Value.</li>
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
 * 			<li>{@link #add(String, boolean, boolean, JTextField)}:
 * 				Adds the given Link to the List, that was determined by the Parameters. 
 * 				These also determine, if it is added in front or at the end of the List.</li>
 * 
 * 			<li>{@link #addNewMessage()}:
 * 				Adds all remaining String in {@link #buffer} to {@link #txtDebugs}. If the 
 * 				amount of Messages exceeds 200, this will also delete the first Messages 
 * 				until there are 200 Messages in that Pane.</li>
 * 
 * 			<li>{@link #constructFrame()}:
 * 				Creates the Frame and places the Components on their belonging spots.</li>
 * 
 * 			<li>{@link #moveTrackDown(int)}:
 * 				Moves the Track at the given Index one position down.</li> 
 * 			
 * 			<li>{@link #moveTrackUp(int)}:
 * 				Moves the Track at the given Index one position up.</li>
 * 
 * 			<li>{@link #pauseResume(boolean)}:
 * 				Will be called by {@link #onPauseResumeNotify(boolean)} to update {@link 
 * 				#btnPlayPause} and {@link #menuPlayPause}.</li>
 * 
 * 			<li>{@link #pressPause()}:
 * 				Is called, whenever {@link #btnPlayPause} was pressed. Will send a Message 
 * 				to the Server to pause/resume the Track that is currently playing.</li>
 * 
 * 			<li>{@link #removeAllVotes()}:
 * 				Sends a Message to the Server to remove all Votes, that are saved on it. Is 
 * 				called, when the MenuItem for this was clicked.</li>
 * 
 * 			<li>{@link #removeVote()}:
 * 				Sends a Message to the Server to remove the Vote for this Client.</li>
 * 
 * 			<li>{@link #saveGaplist()}:
 * 				Sends a Message to the Server to save the current Gaplist.</li>
 * 
 * 			<li>{@link #seek(boolean)}:
 * 				Seeks in the direction that is determined by the parameter. If it is {@code 
 * 				true}, it will seek forward, else it will seek backwards.</li>
 * 
 * 			<li>{@link #setNextTrack()}:
 * 				Updates {@link #lblTrackNext} to the Name of the new Song, that will be 
 * 				played next.</li>
 * 
 * 			<li>{@link #setNowPlaying(String)}:
 * 				Updates {@link #lblPlayingTrack} to the Name of the new Song, that is 
 * 				currently played.</li>
 * 
 * 			<li>{@link #setTexts()}:
 * 				Updates the Texts of all components to match the new Language. Is called by 
 * 				{@link #setLanguage(String)}.</li>
 * 
 * 			<li>{@link #skip()}:
 * 				Sends a Message to the Server to skip the current Track.</li>
 * 
 * 			<li>{@link #vote(int)}:
 * 				Sends a Message to the Server to vote for the Track with the given Index.
 * 				</li>
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
 * 			<li>{@link #btnPlayPause}:
 * 				The {@link JButton}, that is used to pause/resume the Track when pressed.
 * 				</li>
 * 
 * 			<li>{@link #buffer}:
 * 				The {@link StringBuilder}, that will parse the Debug-Message from the Server 
 * 				to single String values, that will be printed into {@link #txtDebugs}.</li>
 * 
 * 			<li>{@link #changed}:
 * 				Determines, if the current Gaplist was changed and not yet saved by the User.
 * 				</li>
 * 
 * 			<li>{@link #collector}:
 * 				The {@link Collector}, that provides additional Methods for the Window.</li>
 * 
 * 			<li>{@link #components}:
 * 				All Components of this Window. Is used to update their Texts when changing 
 * 				the Language.</li>
 * 
 * 			<li>{@link #currentURL}:
 * 				The URL of the currently playing Song.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 
 * 			<li>{@link #gaplist}:
 * 				The current Gaplist as an Array of {@link Song}s.</li>
 * 			
 * 			<li>{@link #gaplists}:
 * 				All Gaplists saved on the Server as an Array of {@code String}s.</li>
 * 
 * 			<li>{@link #gaplistsWindow}:
 * 				The {@link DisplayGaplistsWindow}, that might have been opened by the User.
 * 				</li>
 * 
 * 			<li>{@link #lblFail}:
 * 				The {@link JLabel}, that displays Messages from the Server.</li>
 * 
 * 			<li>{@link #lblGaplistName}:
 * 				The {@link JLabel}, that displays the Name of the current Gaplist.</li>
 * 
 * 			<li>{@link #lblNoGaplist}:
 * 				The {@link JLabel}, that displays the amount of Tracks in the current 
 * 				Gaplist.</li>
 * 
 * 			<li>{@link #lblNoWishlist}:
 * 				The {@link JLabel}, that displays the amount of Tracks in the Wishlist.</li>
 * 
 * 			<li>{@link #lblPlayingTrack}:
 * 				The {@link JLabel}, that displays the Name of the currently playing Track.
 * 				</li>
 * 
 * 			<li>{@link #lblTrackNext}:
 * 				The {@link JLabel}, that displays the Name of the next Track.</li>
 * 
 * 			<li>{@link #localServer}:
 * 				Determines, if the Server, this Client is connected to, is running locally.
 * 				</li>
 * 
 * 			<li>{@link #menuPlayPause}:
 * 				The {@link JMenuItem}, that is used to pause/resume the current Track.</li>
 * 
 * 			<li>{@link #oldGaplistPane}:
 * 				The {@link JScrollPane}, that displays the Gaplist as a Table.</li>
 * 
 * 			<li>{@link #oldPane}:
 * 				The {@link JScrollPane}, that displays the Wishlist as a Table.</li>
 * 
 * 			<li>{@link #pauseIcon}:
 * 				The Icon, that is used instead of the Text "Pause".</li>
 * 
 * 			<li>{@link #playIcon}:
 * 				The Icon, that is used instead of the Text "Play".</li>
 * 
 * 			<li>{@link #scrollPane}:
 * 				The {@link JScrollPane}, that displays {@link #txtDebugs}.</li> 
 * 
 * 			<li>{@link #seekBackwardIcon}:
 * 				The Icon, that is used instead of the Text "Seek Backwards".</li>
 * 
 * 			<li>{@link #seekForwardIcon}:
 * 				The Icon, that is used instead of the Text "Seek Forward".</li>
 * 
 * 			<li>{@link #skipIcon}:
 * 				The Icon, that is used instead of the Text "Skip".</li>
 * 
 * 			<li>{@link #title}:
 * 				The {@code String}, that is used as Title for the {@link #frame}.</li>
 * 
 * 			<li>{@link #txtDebugs}:
 * 				The {@link JTextArea}, where the Debug-Messages are displayed.</li>
 * 
 * 			<li>{@link #txtLink}:
 * 				The {@link JTextField}, where the User can enter a Link to a Video.</li>
 * 
 * 			<li>{@link #wishlist}:
 * 				The Wishlist as an Array of {@link Song}s.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection} to the Server, that is used to send/receive 
 * 				Message to/from the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.9
 * @see DefaultNotificationListener
 * @see PauseResumeNotificationListener
 * @see GapListNotificationListener
 * @see DebugNotificationListener
 */
public class MainWindow extends Window implements DefaultNotificationListener, 
PauseResumeNotificationListener, GapListNotificationListener, DebugNotificationListener{
	
	//TODO: Set the MainWindow disabled while the AckWindow is open.
	//TODO: Check, what happens, if other Windows are open when the Server is shut down.
	
	/**
	 * <p style="margin-left: 10px"><em><b>collector</b></em></p>
	 * <p style="margin-left: 20px">{@code private final Collector collector}</p>
	 * <p style="margin-left: 20px">The {@link Collector}, that will perform Actions with 
	 * external Information.</p>
	 */
	private final Collector collector;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtLink</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextField txtLink}</p>
	 * <p style="margin-left: 20px">The TextField that contains the Link.</p>
	 * @see JTextField
	 */
	private JTextField txtLink;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblFail</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblFail}</p>
	 * <p style="margin-left: 20px">The Label that displays possible Messages.</p>
	 * @see JLabel
	 */
	private JLabel lblFail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, that displays this Window.</p>
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private final ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection}, that will send the 
	 * Messages.</p>
	 */
	private final ServerConnection wrapper;

	/**
	 * <p style="margin-left: 10px"><em><b>gaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] gaplist}</p>
	 * <p style="margin-left: 20px">The Array, that contains all {@link Song}s in the 
	 * Gaplist.</p>
	 */
	private Song[] gaplist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private Song[] wishlist}</p>
	 * <p style="margin-left: 20px">The Array, that contains all {@link Song}s in the 
	 * Wishlist.</p>
	 */
	private Song[] wishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>btnPlayPause</b></em></p>
	 * <p style="margin-left: 20px">{@code private JButton btnPlayPause}</p>
	 * <p style="margin-left: 20px">The Button, that can be pressed to pause/resume a Track.
	 * </p>
	 * @see JButton
	 */
	private JButton btnPlayPause;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblGaplistName</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblGaplistName}</p>
	 * <p style="margin-left: 20px">The Label, that will display the Name of the current 
	 * Gaplist.</p>
	 * @see JLabel
	 */
	private JLabel lblGaplistName;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblPlayingTrack</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblPlayingTrack}</p>
	 * <p style="margin-left: 20px">The Label, that will display the Name of the current 
	 * Track.</p>
	 * @see JLabel
	 */
	private JLabel lblPlayingTrack;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblTrackNext</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblTrackNext}</p>
	 * <p style="margin-left: 20px">The Label, that will display the Name of the next Track.
	 * </p>
	 * @see JLabel
	 */
	private JLabel lblTrackNext;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblNoGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNoGaplist}</p>
	 * <p style="margin-left: 20px">The Label, that will display the number of Tracks in the 
	 * Gaplist.</p>
	 * @see JLabel
	 */
	private JLabel lblNoGaplist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>lblNoWishlist</b></em></p>
	 * <p style="margin-left: 20px">{@code private JLabel lblNoWishlist}</p>
	 * <p style="margin-left: 20px">The Label, that will display the number of Tracks in the 
	 * Wishlist.</p>
	 * @see JLabel
	 */
	private JLabel lblNoWishlist;
	
	/**
	 * <p style="margin-left: 10px"><em><b>oldPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane oldPane}</p>
	 * <p style="margin-left: 20px">The ScrollPane, that contains the old Wishlist-Table. 
	 * Has to be stored to be able to keep the table updated.</p>
	 * @see JScrollPane
	 */
	private JScrollPane oldPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b></b></em></p>
	 * <p style="margin-left: 20px">{@code }</p>
	 * <p style="margin-left: 20px">The ScrollPane, that contains the old Gaplist-Table. Has 
	 * to be stored to be able to keep the table updated.</p>
	 * @see JScrollPane
	 */
	private JScrollPane oldGaplistPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplists</b></em></p>
	 * <p style="margin-left: 20px">{@code private String[] gaplists}</p>
	 * <p style="margin-left: 20px">The Gaplists, saved on the Server.</p>
	 */
	private String[] gaplists;
	
	/**
	 * <p style="margin-left: 10px"><em><b>playIcon</b></em></p>
	 * <p style="margin-left: 20px">{@code private ImageIcon playIcon}</p>
	 * <p style="margin-left: 20px">The Icon, that will be displayed instead of "Play" as a 
	 * String.</p>
	 * @see ImageIcon
	 */
	private ImageIcon playIcon;
	
	/**
	 * <p style="margin-left: 10px"><em><b>pauseIcon</b></em></p>
	 * <p style="margin-left: 20px">{@code private ImageIcon pauseIcon}</p>
	 * <p style="margin-left: 20px">The Icon, that will be displayed instead of "Pause" as a 
	 * String.</p>
	 * @see ImageIcon
	 */
	private ImageIcon pauseIcon;
	
	/**
	 * <p style="margin-left: 10px"><em><b>seekBackwardIcon</b></em></p>
	 * <p style="margin-left: 20px">{@code private ImageIcon seekBackwardIcon}</p>
	 * <p style="margin-left: 20px">The Icon, that will be displayed instead of "Seek 
	 * Backward" as a String.</p>
	 * @see ImageIcon
	 */
	private ImageIcon seekBackwardIcon;
	
	/**
	 * <p style="margin-left: 10px"><em><b>seekForwardIcon</b></em></p>
	 * <p style="margin-left: 20px">{@code private ImageIcon seekForwardIcon}</p>
	 * <p style="margin-left: 20px">The Icon, that will be displayed instead of "Seek 
	 * Forward" as a String.</p>
	 * @see ImageIcon
	 */
	private ImageIcon seekForwardIcon;
	
	/**
	 * <p style="margin-left: 10px"><em><b>skipIcon</b></em></p>
	 * <p style="margin-left: 20px">{@code private ImageIcon skipIcon}</p>
	 * <p style="margin-left: 20px">The Icon, that will be displayed instead of "Skip" as a 
	 * String.</p>
	 * @see ImageIcon
	 */
	private ImageIcon skipIcon;
	
	/**
	 * <p style="margin-left: 10px"><em><b>buffer</b></em></p>
	 * <p style="margin-left: 20px">{@code private StringBuilder buffer}</p>
	 * <p style="margin-left: 20px">The StringBuilder, that will buffer the Messages to 
	 * maintain functionality and correctness of the Messages.</p>
	 */
	private StringBuilder buffer;
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtDebugs</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextArea txtDebugs}</p>
	 * <p style="margin-left: 20px">The TextArea for the Messages from the Server.</p>
	 * @see JTextArea
	 */
	private JTextArea txtDebugs;
	
	/**
	 * <p style="margin-left: 10px"><em><b>scrollPane</b></em></p>
	 * <p style="margin-left: 20px">{@code private JScrollPane scrollPane}</p>
	 * <p style="margin-left: 20px">The {@link JScrollPane}, that contains the DebugArea.</p>
	 */
	private JScrollPane scrollPane;
	
	/**
	 * <p style="margin-left: 10px"><em><b>components</b></em></p>
	 * <p style="margin-left: 20px">{@code private HashMap<String, Component> components}</p>
	 * <p style="margin-left: 20px">All components of this Client as a {@link HashMap} of 
	 * {@code String}s and {@link Component}s.</p>
	 */
	private HashMap<String, Component> components;
	
	/**
	 * <p style="margin-left: 10px"><em><b>options</b></em></p>
	 * <p style="margin-left: 20px">{@code private OptionsWindow options}</p>
	 * <p style="margin-left: 20px">The OptionsWindow, that will be opened, when the User 
	 * clicked on "Edit > Preferences".</p>
	 */
//	private OptionsWindow options;
	
	/**
	 * <p style="margin-left: 10px"><em><b>gaplistsWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private DisplayGalistsWindow}</p>
	 * <p style="margin-left: 20px">The {@link DisplayGaplistsWindow}, that will show the 
	 * saved Gaplists when opened.</p>
	 */
	private DisplayGaplistsWindow gaplistsWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>localServer</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean localServer}</p>
	 * <p style="margin-left: 20px">Determines, if a local Server is running with this 
	 * Client as Host.</p>
	 */
	private boolean localServer;
	
	/**
	 * <p style="margin-left: 10px"><em><b>menuPlayPause</b></em></p>
	 * <p style="margin-left: 20px">{@code private JMenuItem menuPlayPause}</p>
	 * <p style="margin-left: 20px">The {@link JMenuItem}, that is can be used to 
	 * pause/resume the current Track from the Menu.</p>
	 */
	private JMenuItem menuPlayPause;
	
	/**
	 * <p style="margin-left: 10px"><em><b>changed</b></em></p>
	 * <p style="margin-left: 20px">{@code private boolean changed}</p>
	 * <p style="margin-left: 20px">Determines, if the Gaplist was changed.</p>
	 */
	private boolean changed;
	
	/**
	 * <p style="margin-left: 10px"><em><b>MainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public MainWindow(Collector, ServerConnection, 
	 * String, int, String, String, boolean)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Main-Screen. Will set the 
	 * parameters to their belonging variables.</p>
	 * @param collector	The {@link Collector}, that will perform Actions with extern needed 
	 * 					information.
	 * @param wrapper	The {@link ServerConnection}, that will send the Messages.
	 * @param ip	The IP of the Server, this Client is connected to.
	 * @param iport	The Port of the Server, this Client is connected to.
	 * @param adminPassword	The Password for the Admin-Permission.
	 * @param playerPassword	The Password for the Player-Permission.
	 * @param localServer	The boolean value, if the Server, the Client is connected to, is 
	 * 						running on the same device as the Client.
	 * @since 1.0
	 */
	public MainWindow(Collector collector, ServerConnection wrapper, 
			String ip, int iport, String adminPassword, String playerPassword, 
			boolean localServer) {
		this.collector = collector;
		this.frame = new JFrame();
		this.wrapper = wrapper;
		this.localServer = localServer;

		buffer = new StringBuilder("");
		
		pauseIcon = new ImageIcon(MainWindow.class.getResource("/resources/pause.png"));
		pauseIcon.setImage(pauseIcon.getImage().getScaledInstance(35, 35, 
				Image.SCALE_DEFAULT));
		playIcon = new ImageIcon(MainWindow.class.getResource("/resources/play.png"));
		playIcon.setImage(playIcon.getImage().getScaledInstance(35, 35, 
				Image.SCALE_DEFAULT));
		seekBackwardIcon = 
				new ImageIcon(MainWindow.class.getResource("/resources/seekbackward.png"));
		seekBackwardIcon.setImage(seekBackwardIcon.getImage().getScaledInstance(35, 35, 
				Image.SCALE_DEFAULT));
		seekForwardIcon = 
				new ImageIcon(MainWindow.class.getResource("/resources/seekforward.png"));
		seekForwardIcon.setImage(seekForwardIcon.getImage().getScaledInstance(35, 35, 
				Image.SCALE_DEFAULT));
		skipIcon = new ImageIcon(MainWindow.class.getResource("/resources/skip.png"));
		skipIcon.setImage(skipIcon.getImage().getScaledInstance(35, 35, 
				Image.SCALE_DEFAULT));
		
	//	options = new OptionsWindow(collector, adminPassword, playerPassword);

		wrapper.addDefaultNotificationListener(this);
		wrapper.addGapListNotificationListener(this);
		wrapper.addPauseResumeNotificationListener(this);
		wrapper.addDebugNotificationListener(this);

		components = new HashMap<String, Component>();
		if (!localServer)
			setIpAndPort(ip, iport);
		else
			try {
				setIpAndPort(InetAddress.getLocalHost().getHostAddress(), iport);
			} catch (UnknownHostException | NullPointerException e) {
				setIpAndPort(ip, iport);
			}
		changed = false;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Creates the {@link #frame} by calling 
	 * {@link #constructFrame()} and sets it visible.</p>
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
	 * <p style="margin-left: 10px"><em><b>setIpAndPort</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setIpAndPort(String, int)}</p>
	 * <p style="margin-left: 20px">Sets the IP and Port of the Server, the Client is 
	 * connected to, so the Title of the Frame can display it.</p>
	 * @param ip	The IP of the Server, the Client is connected to.
	 * @param port	The Port of the Server, the Client is connected to.
	 * @see JFrame#setTitle(String)
	 * @since 1.0
	 */
	public void setIpAndPort(String ip, int port) {
		frame.setTitle("JukePi - "+ip+":"+port);
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
	 * <p style="margin-left: 10px"><em><b>skip</b></em></p>
	 * <p style="margin-left: 20px">{@code private void skip()}</p>
	 * <p style="margin-left: 20px">Skips the current Song.</p>
	 * @see ServerConnection#skip(ResponseListener)
	 * @since 1.0
	 */
	private void skip() {
		wrapper.skip((String[] s) -> {	
				if (s[0].equals("true")) 
					showFail("Skipped Track successfully!"); 
				else 
					showFail("Couldn't skip the Track!");
			});
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>pressPause</b></em></p>
	 * <p style="margin-left: 20px">{@code private void pressPause()}</p>
	 * <p style="margin-left: 20px">Sends a Message to the Server, that the 
	 * Play/Pause-Button was pressed.</p>
	 * @see ServerConnection#pauseResume(ResponseListener)
	 * @since 1.0
	 */
	private void pressPause() {
		wrapper.pauseResume((String[] s) -> {
				if (s[0].equals("true"))
					wrapper.getCurrentPlaybackStatus((String[] st) -> {	
							if (st[0].equals("false"))
								showFail("Paused the Track successfully!");
							else
								showFail("Resumed the Track successfully!");
						});
				else
					wrapper.getCurrentPlaybackStatus((String[] str) -> {
							if (str[0].equals("false"))
								showFail("Couldn't resume the Track!");
							else
								showFail("Couldn't pause the Track!");
						});
		});
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>seek</b></em></p>
	 * <p style="margin-left: 20px">{@code private void seek(boolean)}</p>
	 * <p style="margin-left: 20px">Sends a Message to the Server to seek 30 seconds either 
	 * forward or backward.</p>
	 * @param forward	Determines, whether the Server should seek forward({@code true}) or 
	 * backward({@code false}).
	 * @see ServerConnection#seekForward(ResponseListener)
	 * @see ServerConnection#seekBackward(ResponseListener)
	 * @since 1.0
	 */
	private void seek(boolean forward) {
		if (forward)
			wrapper.seekForward((String[] s) -> {
					if (s[0].equals("true")) 
						showFail("Successfully sought forward!");
					else
						showFail("Couldn't seek forward!");
				});
		else
			wrapper.seekBackward((String[] s) -> {
					if (s[0].equals("true"))
						showFail("Successfully sought backwards!");
					else
						showFail("Couldn't seek backwards!");
				});
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>add</b></em></p>
	 * <p style="margin-left: 20px">{@code private void add(String, boolean, boolean, 
	 * JTextField}</p>
	 * <p style="margin-left: 20px">Adds the given Link to a List, either the Gap- or the 
	 * Wishlist.</p>
	 * @param link	The Link to the Song.
	 * @param toWishlist	Determines, whether the Song should be added to the Wishlist 
	 * 						({@code true}) or to the Gaplist ({@code false}).
	 * @param inFront	Determines, whether the Track should be added in Front of the List 
	 * 					({@code true}) or at the the End of the List ({@code false}).
	 * @param textfield The TextField, that contains the Link.
	 * @see ServerConnection#addToList(ResponseListener, String, boolean, boolean)
	 * @since 1.0
	 */
	private void add(String link, boolean toWishlist , boolean inFront, 
			JTextField textfield) {
		if (!link.isEmpty()) {
			showFail("Pending Server...");
			wrapper.addToList((String[] s) -> {	
					if (s[0].equals("true"))
						showFail("Track added!");
					else 
						showFail("Couldn't add the Track.");
					//TODO: Localization
					textfield.setText("Insert a Link here");
				}, link, toWishlist, !inFront);
		}
		else {
			showFail("No valid link!");
			textfield.setText("Insert a Link here");
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>moveTrackUp</b></em></p>
	 * <p style="margin-left: 20px">{@code private void moveTrackUp(int)}</p>
	 * <p style="margin-left: 20px">Moves the Song at the given index upwards in the Gaplist.
	 * </p>
	 * @param index	The index of the Track to be moved.
	 * @see ServerConnection#setGapListTrackUp(ResponseListener, long)
	 * @since 1.0
	 */
	private void moveTrackUp(int index) {
		if (index >=0)
			wrapper.setGapListTrackUp((String[] s)-> {
					if (s[0].equals("true")) {
						showFail("Moved Track up.");
						try{Thread.sleep(100);}catch(Exception e) {}
						setSelectedGaplistIndex(index-1);
					}
					else {
						showFail("Couldn't move Track up.");
						try{Thread.sleep(100);}catch(Exception e) {}
						setSelectedGaplistIndex(index);
					}
				}, gaplist[index].getTrackID());
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>moveTrackDown</b></em></p>
	 * <p style="margin-left: 20px">{@code private void moveTrackDown(int)}</p>
	 * <p style="margin-left: 20px">Moves the Song at the given index downwards in the 
	 * Gaplist.</p>
	 * @param index	The index of the Track to be moved.
	 * @see ServerConnection#setGapListTrackDown(ResponseListener, long)
	 * @since 1.0
	 */
	private void moveTrackDown(int index) {
		if (index >= 0)
			wrapper.setGapListTrackDown((String[] s) -> {
					if (s[0].equals("true")) {
						showFail("Moved Track down.");
						try{Thread.sleep(100);}catch(Exception e) {}
						setSelectedGaplistIndex(index+1);
					}
					else {
						showFail("Couldn't move Track down");
						try{Thread.sleep(100);}catch(Exception e) {}
						setSelectedGaplistIndex(index);
					}
				}, gaplist[index].getTrackID());
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>saveGaplist</b></em></p>
	 * <p style="margin-left: 20px">{@code private void saveGaplist()}</p>
	 * <p style="margin-left: 20px">Saves the current Gaplist on the Server.</p>
	 * @see ServerConnection#saveGapList(ResponseListener)
	 * @since 1.0
	 */
	private void saveGaplist() {
		wrapper.saveGapList((String[] s) -> {
				if (s[0].equals("true"))
					showFail("Saved Gaplist.");
				else
					showFail("Couldn't save the Gaplist.");
			});
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>pauseResume</b></em></p>
	 * <p style="margin-left: 20px">{@code private void pauseResume(boolean)}</p>
	 * <p style="margin-left: 20px">Will be executed, when a Song was paused or resumed on 
	 * the Server.</p>
	 * @param isPlaying	Determines, if the Song is now playing ({@code true}) or paused 
	 * ({@code false}).
	 * @since 1.0
	 */
	private void pauseResume(boolean isPlaying) {
		if (isPlaying) {
			btnPlayPause.setIcon(pauseIcon);
		//	btnPlayPause.setText("Pause");
			btnPlayPause.setToolTipText("Click here to pause the Track.");
			menuPlayPause.setText("Pause");
		}
		else {
			btnPlayPause.setIcon(playIcon);
		//	btnPlayPause.setText("Play");
			btnPlayPause.setToolTipText("Click here to resume the Track.");
			menuPlayPause.setText("Play");
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setNowPlaying</b></em></p>
	 * <p style="margin-left: 20px">{@code private void setNowPlaying(String)}</p>
	 * <p style="margin-left: 20px">Sets the Text of the PlayingTrackLabel to the given 
	 * title.</p>
	 * @param title	The title of the song, that is now playing.
	 * @since 1.2
	 */
	private void setNowPlaying(String title) {
		lblPlayingTrack.setText(title);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setNextTrack</b></em></p>
	 * <p style="margin-left: 20px">{@code private void setNextTrack()}</p>
	 * <p style="margin-left: 20px">Sets the Text of the NextTrackLabel to the given title.
	 * </p>
	 * @since 1.0
	 */
	private void setNextTrack() {
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setSelectedGaplistIndex</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setSelectedGaplistIndex(int)}</p>
	 * <p style="margin-left: 20px">Sets the SelectedIndex of gaplistList to the given index.
	 * </p>
	 * @param index	The index of the new Selection.
	 * @since 1.1
	 */
	public void setSelectedGaplistIndex(int index) {
		if (index >= 0) {
			try {
				((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0))
					.setRowSelectionInterval(index, index);
			}
			catch (IllegalArgumentException iae) {
				((JTable) ((JViewport) oldGaplistPane.getComponent(0)).getComponent(0))
					.setRowSelectionInterval(index-1, index-1);
			}
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>vote</b></em></p>
	 * <p style="margin-left: 20px">{@code private void vote(int)}</p>
	 * <p style="margin-left: 20px">Votes for the Song at the given index.</p>
	 * @param index	The index of the Song, that will be voted for.
	 * @since 1.3
	 */
	private void vote(int index) {
		if (index == -1) {
			showFail("Please select a Track first!");
			return;
		}
		wrapper.removeVote((String[] s)-> {});
		wrapper.voteSong((String[] s) -> {	if (s[0].equals("true"))
												showFail("Voted for the Song");
											else
												showFail("Couldn't vote for the Song");
										}, wishlist[index]);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>removeVote</b></em></p>
	 * <p style="margin-left: 20px">{@code private void removeVote()}</p>
	 * <p style="margin-left: 20px">Removes the Client's Vote.</p>
	 * @since 1.3
	 */
	private void removeVote() {
		wrapper.removeVote((String[] s) -> {	
				if (s[0].equals("true"))
					showFail("Removed your vote.");
				else
					showFail("Couldn't remove your vote.");
			});
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>removeAllVotes</b></em></p>
	 * <p style="margin-left: 20px">{@code private void removeAllVotes()}</p>
	 * <p style="margin-left: 20px">Removes all Votes from the Server.</p>
	 * @since 1.7
	 */
	private void removeAllVotes() {
		if (wrapper.deleteAllVotes())
			showFail("Deleted all Votes");
		else
			showFail("Couldn't delete all Votes");
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame()}</p>
	 * <p style="margin-left: 20px">Creates a new Frame.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		/********************Start********************/
		long start = System.currentTimeMillis();
		util.IO.println(this, "Starting to build frame");
		
		/****************Setting Lists****************/
		gaplist = wrapper.getGapList();
		wishlist = wrapper.getWishList();
		gaplists = wrapper.getAvailableGapLists();
		
		/********************Frame itself********************/
		frame.setSize(new Dimension(620,700));
		frame.getContentPane().setLayout(new NewClientLayout());
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setMinimumSize(new Dimension(617,695));
		MyAdapter adapter = new MyAdapter(this, wrapper);
		frame.addWindowListener(adapter);
		
		/********************Labels********************/
		lblFail = new JLabel("");
		frame.getContentPane().add(lblFail, NewClientLayout.FAIL_LABEL);		
		
		//TODO: Localization
		final JLabel lblGaplist = new JLabel("Tracks in the Gaplist:");		
		lblGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblGaplist, NewClientLayout.GAPLIST_LABEL);
		components.put(NewClientLayout.GAPLIST_LABEL, lblGaplist);
		
		//TODO: Localization
		final JLabel lblWishlist = new JLabel("Tracks in the Wishlist:");	
		lblWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblWishlist, NewClientLayout.WISHLIST_LABEL);
		components.put(NewClientLayout.WISHLIST_LABEL, lblWishlist);
		
		//TODO: Localization?
		lblNoGaplist = new JLabel(""+ gaplist.length);
		lblNoGaplist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoGaplist, NewClientLayout.COUNT_GAPLIST_LABEL);
		components.put(NewClientLayout.COUNT_GAPLIST_LABEL, lblNoGaplist);
		
		//TODO: Localization?
		lblNoWishlist = new JLabel("" + wishlist.length);
		lblNoWishlist.setFont(new Font("Tahoma", Font.BOLD, 11));
		frame.getContentPane().add(lblNoWishlist, NewClientLayout.COUNT_WISHLIST_LABEL);
		components.put(NewClientLayout.COUNT_WISHLIST_LABEL, lblNoWishlist);
		
		//TODO: Localization
		final JLabel lblNowPlaying = new JLabel("Now Playing:");
		lblNowPlaying.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNowPlaying, NewClientLayout.NOW_PLAYING_LABEL);
		components.put(NewClientLayout.NOW_PLAYING_LABEL, lblNowPlaying);
		
		//TODO: Localization
		final JLabel lblNextTrack = new JLabel("Next Track:");
		lblNextTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblNextTrack, NewClientLayout.NEXT_TRACK_LABEL);
		components.put(NewClientLayout.NEXT_TRACK_LABEL, lblNextTrack);
		
		lblPlayingTrack = new JLabel("");
		lblPlayingTrack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblPlayingTrack, NewClientLayout.NAME_NOW_PLAYING_LABEL);
		components.put(NewClientLayout.NAME_NOW_PLAYING_LABEL, lblPlayingTrack);
		
		lblTrackNext = new JLabel("");
		lblTrackNext.setFont(new Font("Tahoma", Font.PLAIN, 11));
		frame.getContentPane().add(lblTrackNext, NewClientLayout.NAME_NEXT_TRACK_LABEL);
		components.put(NewClientLayout.NAME_NEXT_TRACK_LABEL, lblTrackNext);
		
		lblGaplistName = new JLabel("");
		lblGaplistName.setFont(new Font("Tahoma", Font.BOLD, 11));
		lblGaplistName.setVerticalAlignment(JLabel.CENTER);
		lblGaplistName.setHorizontalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblGaplistName, NewClientLayout.GAPLIST_NAME_LABEL);
		components.put(NewClientLayout.GAPLIST_NAME_LABEL, lblGaplistName);
		
		//TODO: Localization
		final JLabel lblNameWishlist = new JLabel("Wishlist");
		lblNameWishlist.setHorizontalAlignment(JLabel.CENTER);
		lblNameWishlist.setVerticalAlignment(JLabel.CENTER);
		frame.getContentPane().add(lblNameWishlist, NewClientLayout.WISHLIST_NAME_LABEL);
		components.put(NewClientLayout.WISHLIST_NAME_LABEL, lblNameWishlist);
		
	
		/********************TextFields********************/
		//TODO: Localization?
		txtLink = new JTextField("Insert a Link here.");
		txtLink.addMouseListener(new PopClickListener(txtLink));
		frame.getContentPane().add(txtLink, NewClientLayout.LINK_TEXTFIELD);
		components.put(NewClientLayout.LINK_TEXTFIELD, txtLink);
		
		/********************RadioButtons********************/
		//TODO: Localization
		final JRadioButton rdbtnWishlist = new JRadioButton("Wishlist");
		frame.getContentPane().add(rdbtnWishlist, NewClientLayout.WISHLIST_RADIO);
		rdbtnWishlist.setSelected(true);
		components.put(NewClientLayout.WISHLIST_RADIO, rdbtnWishlist);
		
		//TODO: Localization?
		final JRadioButton rdbtnGaplist = new JRadioButton("Gaplist");
		frame.getContentPane().add(rdbtnGaplist, NewClientLayout.GAPLIST_RADIO);
		components.put(NewClientLayout.GAPLIST_RADIO, rdbtnGaplist);
		
		final ButtonGroup bg = new ButtonGroup();
		bg.add(rdbtnGaplist);
		bg.add(rdbtnWishlist);
		
		/********************CheckBoxes********************/
		//TODO: Localization
		final JCheckBox chckbxInfront = new JCheckBox("In Front");
		chckbxInfront.setToolTipText("When selected, the track will be added in Front of "
				+ "the list.");
		frame.getContentPane().add(chckbxInfront, NewClientLayout.IN_FRONT_CHECKBOX);
		
		/********************DebugArea********************/
		txtDebugs = new JTextArea();
		txtDebugs.setEditable(false);
		scrollPane = new JScrollPane(txtDebugs);
		JScrollBar sb = scrollPane.getVerticalScrollBar();
		sb.setValue(sb.getMaximum());
		frame.getContentPane().add(scrollPane, NewClientLayout.DEBUG_PANE);	
		components.put(NewClientLayout.DEBUG_PANE, txtDebugs);
		
		/********************Buttons********************/
		//TODO: Localization
		final JButton btnAdd = new JButton("Add");
		btnAdd.setToolTipText("Adds the YouTube-Link in the upper Textfield either to the "
				+ "Gaplist or the Wishlist, whatever is selected on the right.");
		frame.getContentPane().add(btnAdd, NewClientLayout.ADD_BUTTON);
		components.put(NewClientLayout.ADD_BUTTON, btnAdd);
		
		//TODO: Localization
		btnPlayPause = new JButton(playIcon);
		btnPlayPause.setMargin(new Insets(0, 0, 0, 0));
		frame.getContentPane().add(btnPlayPause, NewClientLayout.PLAY_PAUSE_BUTTON);
		components.put(NewClientLayout.PLAY_PAUSE_BUTTON, btnPlayPause);
		
//		final JButton btnSeekBackwards = 
//			new JButton("<html><body>Seek<br>Backward</body></html>");
		//TODO: Localization
		final JButton btnSeekBackwards = new JButton(seekBackwardIcon);
		btnSeekBackwards.setToolTipText("Click here to rewind 30 seconds.");
		frame.getContentPane().add(btnSeekBackwards, NewClientLayout.SEEK_BACK_BUTTON);
		components.put(NewClientLayout.SEEK_BACK_BUTTON, btnSeekBackwards);
		
		//TODO: Localization
		final JButton btnSkip = new JButton(skipIcon);
		btnSkip.setToolTipText("Click here to skip the current track.");
		frame.getContentPane().add(btnSkip, NewClientLayout.SKIP_BUTTON);
		components.put(NewClientLayout.SKIP_BUTTON, btnSkip);
		
	//	final JButton btnSeekForward = 
	//		new JButton("<html><body>Seek<br>Forward</body></html>");
		//TODO: Localization
		final JButton btnSeekForward = new JButton(seekForwardIcon);
		btnSeekForward.setToolTipText("Click here to fast forward 30 seconds.");
		frame.getContentPane().add(btnSeekForward, NewClientLayout.SEEK_FORWARD_BUTTON);
		components.put(NewClientLayout.SEEK_FORWARD_BUTTON, btnSeekForward);
		
		//TODO: Localization
		final JButton btnSave = new JButton("Save");
		btnSave.setToolTipText("Click here to save the current Gaplist on the Server.");
		frame.getContentPane().add(btnSave, NewClientLayout.SAVE_BUTTON);
		components.put(NewClientLayout.SAVE_BUTTON, btnSave);
		
		//TODO: Localization
		final JButton btnUp = new JButton("/\\");
		btnUp.setToolTipText("Click here to move the selected track upwards.");
		frame.getContentPane().add(btnUp, NewClientLayout.TRACK_UP_BUTTON);
		components.put(NewClientLayout.TRACK_UP_BUTTON, btnUp);
		
		//TODO: Localization
		final JButton btnDown = new JButton("\\/");
		btnDown.setToolTipText("Click here to move the selected track downwards.");
		frame.getContentPane().add(btnDown, NewClientLayout.TRACK_DOWN_BUTTON);
		components.put(NewClientLayout.TRACK_DOWN_BUTTON, btnDown);

		//TODO: Localization
		final JButton btnVote = new JButton("Vote");
		btnVote.setToolTipText("Click here to vote for the selected Song.");
		frame.getContentPane().add(btnVote, NewClientLayout.VOTE_BUTTON);
		components.put(NewClientLayout.VOTE_BUTTON, btnVote);
		
		/********************Panes********************/
		oldPane = new JScrollPane();
		new SetWishlistTask(wishlist, lblNoWishlist, wrapper, frame, oldPane, this)
			.execute();
		oldGaplistPane = new JScrollPane();
		new SetGaplistTask(gaplist, lblNoGaplist, wrapper, frame, oldGaplistPane, this)
			.execute();
		
		
		/********************Menu Bar********************/
		JMenuBar menuBar = new JMenuBar();
		JMenu menuServer = new JMenu("Server");
		JMenu menuEdit = new JMenu("Edit");
		JMenu menuGaplist = new JMenu("Gaplist");
		JMenu menuWishlist = new JMenu("Wishlist");
		JMenu menuTrack = new JMenu("Track");
		JMenu menuAbout = new JMenu("About");
		
		
		/***************Server Menu*********************/
		JMenuItem menuDisconnect = new JMenuItem("Disconnect");
		menuDisconnect.setAccelerator(KeyStroke.getKeyStroke('d'));
		if (localServer) {
			menuDisconnect.setText("Shut Down Server");
			menuDisconnect.setAccelerator(KeyStroke.getKeyStroke('s'));
		}
		JMenuItem menuDebug = new JMenuItem("Debug-Mode");
		menuDebug.setAccelerator(KeyStroke.getKeyStroke('b'));
		menuDisconnect.addActionListener((ActionEvent ae) -> {wrapper.close();});
		menuDebug.addActionListener((ActionEvent ae) -> {collector.showDebugWindow();});
		
		menuServer.add(menuDisconnect);
		menuServer.add(menuDebug);
		
		/*****************Edit Menu***********************/
		JMenuItem menuOptions = new JMenuItem("Preferences");
	//	menuOptions.addActionListener((ActionEvent ae) -> {options.show();});

		menuEdit.add(menuOptions);
		
		/****************Track Menu*************************/
		JMenuItem menuSkip = new JMenuItem("Skip");
		JMenuItem menuSeekBackwards = new JMenuItem("Seek Backwards");
		JMenuItem menuSeekForward = new JMenuItem("Seek Forward");
		menuPlayPause = new JMenuItem("Pause");
		JMenuItem menuCopyLink = new JMenuItem("Copy Link");
		
		menuSkip.setAccelerator(KeyStroke.getKeyStroke('s'));
		menuSeekBackwards.setAccelerator(KeyStroke.getKeyStroke('b'));
		menuSeekForward.setAccelerator(KeyStroke.getKeyStroke('f'));
		menuPlayPause.setAccelerator(KeyStroke.getKeyStroke('p'));
		menuCopyLink.setAccelerator(KeyStroke.getKeyStroke('c'));
		
		menuSkip.addActionListener((ActionEvent ae) -> {skip();});
		menuSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		menuSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		menuPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		menuCopyLink.addActionListener((ActionEvent ae) -> {
				new util.TextTransfer().setClipboardContents(getCurrentURL());
			});
		
		menuTrack.add(menuCopyLink);
		menuTrack.add(menuSkip);
		menuTrack.add(menuSeekBackwards);
		menuTrack.add(menuSeekForward);
		menuTrack.add(menuPlayPause);
		
		/********************Gaplist Menu*******************/
		JMenuItem menuSaveGaplist = new JMenuItem("Save Gaplist");
		JMenuItem menuCreateGaplist = new JMenuItem("Create new Gaplist");
		JMenuItem menuDisplayGaplists = new JMenuItem("Display all Gaplists");
		menuSaveGaplist.setAccelerator(KeyStroke.getKeyStroke('s'));
		menuCreateGaplist.setAccelerator(KeyStroke.getKeyStroke('c'));
		menuDisplayGaplists.setAccelerator(KeyStroke.getKeyStroke('d'));
		menuSaveGaplist.addActionListener((ActionEvent ae) -> {saveGaplist();});
		menuCreateGaplist.addActionListener((ActionEvent ae) -> {
				new NewListWindow(wrapper, this).show();
			});
		menuDisplayGaplists.addActionListener((ActionEvent ae) -> {gaplistsWindow.show();});
		
		menuGaplist.add(menuSaveGaplist);
		menuGaplist.add(menuCreateGaplist);
		menuGaplist.add(menuDisplayGaplists);
		
		/**********************Wishlist Menu*******************/
		JMenuItem menuRemoveVote = new JMenuItem("Remove Vote");
		JMenuItem menuRemoveAllVotes = new JMenuItem("Remove all Votes");
		menuRemoveVote.setAccelerator(KeyStroke.getKeyStroke('r'));
		menuRemoveAllVotes.setAccelerator(KeyStroke.getKeyStroke('v'));
		menuRemoveVote.addActionListener((ActionEvent ae) -> {removeVote();});
		menuRemoveAllVotes.addActionListener((ActionEvent ae) -> {removeAllVotes();});
		
		menuWishlist.add(menuRemoveVote);
		menuWishlist.add(menuRemoveAllVotes);
		
		/*********************About Menu**********************/
		JMenuItem menuVersion = new JMenuItem("Version");
		menuVersion.setAccelerator(KeyStroke.getKeyStroke('v'));
		menuVersion.addActionListener((ActionEvent ae) -> {new VersionWindow().show();});
		
		menuAbout.add(menuVersion);
		
		/*****************Finishing Menu Creation*************/
		menuBar.add(menuServer);
		menuBar.add(menuEdit);
		menuBar.add(menuTrack);
		menuBar.add(menuGaplist);
		menuBar.add(menuWishlist);
		menuBar.add(menuAbout);
		menuBar.setBackground(Color.white);
		
		components.put(NewClientLayout.MENU_BAR, menuBar);
		frame.getContentPane().add(menuBar, NewClientLayout.MENU_BAR);
		
		/********************Creating additional Windows********************/
		gaplistsWindow = new DisplayGaplistsWindow(wrapper, this, gaplists);
		
		/********************Setting Texts and Server-Communication********************/
		if (wishlist.length == 0) 
			if (gaplist.length == 0) 
				lblTrackNext.setText("NOTHING");
			else
				lblTrackNext.setText(gaplist[0].getName());
		else
			lblTrackNext.setText(wishlist[0].getName());
		
	//	this.setTexts();
		wrapper.getCurrentGapListName((String[] s) -> {
			lblGaplistName.setText("Gaplist - "+ s[0]);
		});
		
		Song current = wrapper.getCurrentSong();
		if (current != null) {
			lblPlayingTrack.setText(current.getName());
		}
		else {
			lblPlayingTrack.setText("NULL");
		}
		wrapper.getCurrentPlaybackStatus((String[] s) -> {
				if (s[0].equals("true")) {
					btnPlayPause.setToolTipText("Click here to Pause the Track.");
					btnPlayPause.setIcon(pauseIcon);
					menuPlayPause.setText("Pause");
				//	btnPlayPause.setText("Pause");
				}
				else {
					btnPlayPause.setToolTipText("Click here to resume the Track");
					btnPlayPause.setIcon(playIcon);
					menuPlayPause.setText("Play");
				//	btnPlayPause.setText("Play");
				}
			});
		/********************Adding Listeners********************/
		txtLink.addMouseListener(new TextFieldListener(
				new String[] {"Insert a Link here", "Couldn't add", "Track added", 
						"No valid"}, txtLink));
		
		txtLink.setColumns(10);
		btnSkip.addActionListener((ActionEvent ae) -> {skip();});
		btnPlayPause.addActionListener((ActionEvent ae) -> {pressPause();});
		btnSeekForward.addActionListener((ActionEvent ae) -> {seek(true);});
		btnSeekBackwards.addActionListener((ActionEvent ae) -> {seek(false);});
		btnAdd.addActionListener((ActionEvent ae) -> {
				add(txtLink.getText(), rdbtnWishlist.isSelected(),
						chckbxInfront.isSelected(), txtLink);
			});
		
		btnSave.addActionListener((ActionEvent ae) -> {saveGaplist();});
		btnUp.addActionListener((ActionEvent ae) -> {
				moveTrackUp(((JTable) ((JViewport) oldGaplistPane.getComponent(0))
						.getComponent(0)).getSelectedRow());
			});
		btnDown.addActionListener((ActionEvent ae) -> {
				moveTrackDown(((JTable) ((JViewport) oldGaplistPane.getComponent(0))
						.getComponent(0)).getSelectedRow());
			});
		btnVote.addActionListener((ActionEvent ae) -> {
				vote(((JTable) ((JViewport) oldPane.getComponent(0)).getComponent(0))
						.getSelectedRow());
		});
		
		/********************Finish Creating Client********************/
		long end = System.currentTimeMillis();
		util.IO.println(this, "Frame Constructed in " + (end-start) + " ms");
	}

	private String getCurrentURL() {
		if (wrapper.getCurrentSong() != null) {
			return wrapper.getCurrentSong().getURL();
		}
		else
			return "ABC";
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListCountChangedNotify(String[])}
	 * </p>
	 * <p style="margin-left: 20px">Will be called, whenever the amount of Gaplists saved on 
	 * the Server has changed. Will set the given {@code gapLists} as the new value for 
	 * {@link #gaplists}.</p>
	 * @param gapLists	All Gaplists saved on the Server as an Array of {@code String}s.
	 * @since 1.0
	 */
	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		showFail("Count of Gaplists changed");
		this.gaplists = gapLists;
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListChangedNotify(String)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever a new Gaplist was loaded on the 
	 * Server. Will update {@link #lblGaplistName} to display the new Value.</p>
	 * @param gapListName	The Name of the new Gaplist.
	 * @since 1.0
	 */
	@Override
	public void onGapListChangedNotify(String gapListName) {
		showFail("Gaplist changed");
		lblGaplistName.setText("Gaplist - " + gapListName);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onGapListUpdatedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onGapListUpdatedNotify(Song[])}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the Gaplist was updated. Will 
	 * set {@link #changed} to {@code true}, if the amount of Tracks has changed and updates 
	 * the {@link #oldGaplistPane} by executing a new {@link SetGaplistTask}.</p>
	 * @param songs	The {@link Song}s in the Gaplist as an Array.
	 * @since 1.0
	 */
	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		showFail("Gaplist updated");
		if (songs.length != this.gaplist.length)
			changed = true;
		new SetGaplistTask(songs, lblNoGaplist, wrapper, frame, oldGaplistPane, 
				this).execute();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onPauseResumeNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onPauseResumeNotify(boolean)}</p>
	 * <p style="margin-left: 20px">Will print a Message on the Screen, that the Track was 
	 * resumed/paused and calls {@link #pauseResume(boolean)}.</p>
	 * @param isPlaying	{@code true}, if the Track was resumed, {@code false}, if it was 
	 * 					paused.
	 * @since 1.0
	 */
	@Override
	public void onPauseResumeNotify(boolean isPlaying) {
		if (isPlaying)
			showFail("Track resumed");
		else
			showFail("Track paused");
		pauseResume(isPlaying);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onWishListUpdatedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onWishListUpdatedNotify(Song[])}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the Wishlist was updated. Will 
	 * print a Message on the Screen and updates the {@link #oldPane} by executing a new 
	 * {@link SetWishlistTask}.</p>
	 * @param songs	The {@link Song}s in the Wishlist as an Array.
	 * @since 1.0
	 */
	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		new SetWishlistTask(songs, lblNoWishlist, wrapper, frame, oldPane, this).execute();
		showFail("Wishlist updated");
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onNextTrackNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onNextTrackNotify(String, String, 
	 * boolean)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever a new Track is played on the 
	 * Server. Calls {@link #setNowPlaying(String)} and {@link #setNextTrack()} to update 
	 * those Values and sets {@link #currentURL} to the new URL.</p>
	 * @param title	The Title of the new Song.
	 * @param url	The URL to the Video. Unused, since it's not the real Link to the Video.
	 * @param isVideo	Boolean value, if the Song has a Video attached to it. Unused.
	 * @since 1.0
	 */
	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		showFail("Playing next Track");
		setNowPlaying(title);
		setNextTrack();
	//	this.currentURL = wrapper.getCurrentSong().getURL(); //TODO Maybe this fixes the random freezes?
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onDisconnect</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onDisconnect()}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the Client is disconnected from 
	 * the Server without the User's Permission (e.g. Loss of Connection). Will call 
	 * {@link Collector#disconnect()}.</p>
	 * @since 1.0
	 */
	@Override
	public void onDisconnect() {
		collector.disconnect();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>doneGaplistUpdate</b></em></p>
	 * <p style="margin-left: 20px">{@code public void doneGaplistUpdate(Song[], JLabel, 
	 * JFrame, JScrollPane)}</p>
	 * <p style="margin-left: 20px">The Method, that is called, whenever the {@link 
	 * SetGaplistTask} is finished. Is called to update the references in this class.</p>
	 * @param gaplist	The new Gaplist as an Array of {@link Song}s.
	 * @param lblNoGaplist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Gaplist.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param oldGaplistPane	The {@link JScrollPane}, that displays the Gaplist as a 
	 * 							table.
	 * @since 1.6
	 */
	public void doneGaplistUpdate(Song[] gaplist, JLabel lblNoGaplist, JFrame frame, 
			JScrollPane oldGaplistPane) {
		this.gaplist = gaplist;
		frame.remove(this.lblNoGaplist);
		frame.getContentPane().add(lblNoGaplist, NewClientLayout.COUNT_GAPLIST_LABEL);
		this.lblNoGaplist = lblNoGaplist;
		this.frame = frame;
		frame.remove(this.oldGaplistPane);
		this.oldGaplistPane = oldGaplistPane;
		this.frame.revalidate();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>doneWishlistUpdate</b></em></p>
	 * <p style="margin-left: 20px">{@code public void doneWishlistUpdate(Song[], JLabel, 
	 * JFrame, JScrollPane)}</p>
	 * <p style="margin-left: 20px">The Method, that is called whenever the {@link 
	 * SetWishlistTask} is finished. Is called to update the references in this class.</p>
	 * @param wishlist	The new Wishlist as an Array of {@link Song}s.
	 * @param lblNoWishlist	The {@link JLabel}, that displays the amount of Tracks in the 
	 * Wishlist.
	 * @param frame	The {@link JFrame}, that displays this MainWindow.
	 * @param oldPane	The {@link JScrollPane}, that displays the Wishlist as a table.
	 * @since 1.6
	 */
	public void doneWishlistUpdate(Song[] wishlist, JLabel lblNoWishlist, JFrame frame, 
			JScrollPane oldPane) {
		this.wishlist = wishlist;
		frame.remove(this.lblNoWishlist);
		frame.getContentPane().add(lblNoWishlist, NewClientLayout.COUNT_WISHLIST_LABEL);
		this.lblNoWishlist = lblNoWishlist;
		this.frame = frame;
		frame.remove(this.oldPane);
		this.oldPane = oldPane;
		this.frame.revalidate();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>setLanguage</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setLanguage(String)}</p>
	 * <p style="margin-left: 20px">Sets the Language to the given Language. At first, 
	 * English will be taken as default.</p>
	 * @param lang	The new Language of the Server.
	 * @since 1.10
	 */
	public void setLanguage(String lang) {
		//TODO
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>setTexts</b></em></p>
	 * <p style="margin-left: 20px">{@code private void setTexts()}</p>
	 * <p style="margin-left: 20px">Sets the Text of all Components. Will be called, 
	 * whenever the Language was updated and at the Start of the Client.</p>
	 * @since 1.9
	 */
	@Deprecated
	@SuppressWarnings(value = { "unused" })
	private void setTexts() {
		try {
			int size = 0;
			String workingDir = MainWindow.class.getProtectionDomain().getCodeSource()
					.getLocation().getPath();
			System.out.println(workingDir);
			File file = new File(workingDir + "\\data\\lang\\Deutsch.lang");
			Scanner fileScanner = new Scanner(file);
			ArrayList<String> texts = new ArrayList<String>();
			while (fileScanner.hasNextLine()) {
				texts.add(fileScanner.nextLine());
				size++;
			}
			
			for (int i = 0; i < size; i++)
				if (i != 0 && i != 19 && i != 20)
					texts.set(i, texts.get(i).substring(texts.get(i).indexOf("= ")+2));
			
			((JLabel)components.get(NewClientLayout.GAPLIST_LABEL)).setText(texts.get(1));
			((JLabel)components.get(NewClientLayout.GAPLIST_LABEL))
					.setToolTipText(texts.get(1));
			((JLabel)components.get(NewClientLayout.WISHLIST_LABEL)).setText(texts.get(2));
			((JLabel)components.get(NewClientLayout.WISHLIST_LABEL))
					.setToolTipText(texts.get(2));
			((JLabel)components.get(NewClientLayout.NOW_PLAYING_LABEL))
					.setText(texts.get(3));
			
			fileScanner.close();
		} catch (NullPointerException | FileNotFoundException fnfe) {
			showFail("Nappel " + fnfe.getClass());
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>onClientCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onClientCountChangedNotify(int)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the amount of connected Clients 
	 * has changed. As there is nothing to do, this Method does nothing.</p>
	 * @param newClientCount	The Amount of Clients, that are connected to the Server. 
	 * 								Unused.
	 * @since 1.8
	 */
	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		// Nothing to do here
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onPlayerCountChangedNotify</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onPlayerCountChangedNotify(int)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever the amount of connected Players 
	 * has changed. As there is nothing to do, this Method does nothing.</p>
	 * @param newPlayerCount	The amount of Players, that are connected to the Server. 
	 * 							Unused.
	 * @since 1.8
	 */
	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		//Nothing to do here
	}

	/**
	 * <p style="margin-left: 10px"><em><b>onNewOutput</b></em></p>
	 * <p style="margin-left: 20px">{@code public void onNewOutput(String)}</p>
	 * <p style="margin-left: 20px">Will be called, whenever a new Message shall be printed 
	 * into the Debug-Console. Adds the given {@code ouput} to {@link #buffer} and calls 
	 * {@link #addNewMessage()}.</p>
	 * @since 1.8
	 */
	@Override
	public void onNewOutput(String output) {
		buffer.append(output+"\n");
		addNewMessage();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>addNewMessage</b></em></p>
	 * <p style="margin-left: 20px">{@code private void addNewMessage()}</p>
	 * <p style="margin-left: 20px">Adds a new Message to the Debug-TextArea.</p>
	 * @since 1.8
	 */
	private void addNewMessage() {
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
			scrollPane.getVerticalScrollBar().setValue(scrollPane.getVerticalScrollBar()
					.getMaximum());
		}
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
	 * <p style="margin-left: 10px"><em><b>acknowledged</b></em></p>
	 * <p style="margin-left: 20px">{@code public void acknowledged()}</p>
	 * <p style="margin-left: 20px">The User was acknowledged by the {@link AckWindow} and 
	 * the value of {@link #changed} is set to {@code false}. </p>
	 * @since 1.9
	 */
	public void acknowledged() {
		this.changed = false;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>getChanged()</b></em></p>
	 * <p style="margin-left: 20px">{@code public boolean getChanged()}</p>
	 * <p style="margin-left: 20px">Returns, if the Gaplist was changed since the last Save.
	 * </p>
	 * @return	{@code true}, if the Gaplist was changed, {@code false}, else.
	 * @since 1.9
	 */
	public boolean getChanged() {
		return changed;
	}
}