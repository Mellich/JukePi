package connection;


import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import messages.Permission;
import server.Server;
import server.ServerFactory;
import windows.DebugWindow;
import windows.LoginWindow;
import windows.LowClientWindow;
import windows.MainWindow;
import windows.PasswordWindow;
import windows.SetPasswordWindow;
import windows.Window;
import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;

/**
 * <p>The Collector, that will start the Client. Also provides all necessary information and 
 * Methods for each Frame, to work properly.</p>
 * <h3>Provided Methods:</h3> 
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul> 
 *			<li>{@link #Collector()}: 
 *				The Constructor for the Collector. Creates a new Connection to the Server and 
 *				instantiates a new LoginScreen. </li>
 *		
 *			<li>{@link #adminConnect(String, int, String, String, boolean)}:
 *				Connects to the Server with the given IP and port as an Admin. Will also 
 *				deliver the Passwords for Admins and Players to the MainWindow and the boolean 
 *				value, if the Server is running locally. </li>
 *		
 *			<li>{@link #connect(String, String)}: 
 *				Will parse the Port into an Integer and connects afterwards to the Server with 
 *				this Port and the given String as IP. Then, the Window, where the User can 
 *				enter the Password for Admin-Permissions, will be opened 
 *				({@link PasswordWindow}). If no Server with the given Address exists, the 
 *				Client won't connect and prints a Message on the LoginScreen. </li>
 *		
 *			<li>{@link #createLocalServer(String)}: 
 *				Starts the Creation of a local Server with the Port, given as a String. The 
 *				Client will parse the Port into an Integer and asks for the Passwords for 
 *				Admins and Players afterwards. If the Port cannot be parsed, the Client won't 
 *				ask for the Passwords and prints a Message on the LoginScreen. </li>
 *
 *			<li>{@link #createLocalServerFinal(int, String, String)}:
 *				Creates a local Server with the given Integer as Port. The Passwords for 
 *				Admins and Players are also given as Strings and will be set on the Server. 
 *				Afterwards, the Client will connect as an Admin, using 
 *				{@link #adminConnect(String, int, String, String, boolean)}. </li>
 *		
 *			<li>{@link #disconnect()}:
 *				Disconnects from the Server and will close all opened Windows. If the Server 
 *				was running locally, the Server is also shut down. Afterwards, a new 
 *				LoginWindow will be opened. </li>
 *		
 *			<li>{@link #lowConnect(String, int)}: 
 *				Connects as a standard Client to the Server with the given String as IP and 
 *				the given Integer as Port. Since it is called after the Check for 
 *				Availability, this Server will always exist. </li>
 *
 *			<li>{@link #showDebugWindow()}:
 *				Shows the Debug-Window, an exterior Window where the Debug-Messages from the 
 *				Server are printed into. This also shows the Count of Clients and Players, 
 *				that are connected to the Server. </li>
 *
 *			<li>{@link #showFail(Window, String)}:
 *				Shows the Fail-Label on the given Window with the given String as it's text.
 *				</li>
 *
 *			<li>{@link #showUDPFail(String)}:
 *				Shows the Fail-Label on the LoginWindow with the given String as it's Text. 
 *				This will be called, when the UDP-Connect failed. Since the Listener doesn't 
 *				have a reference of the LoginWindow, this Method is needed in the Collector.
 *				</li>
 *
 *			<li>{@link #startUp()}:
 *				This Method starts the Client. Will be called from the ClientStarter-Class. 
 *				</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 				None
 * 		</p> 
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<p style="margin-left: 25px">
 * 				None
 * 		</p>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 				None
 * 		</p>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 				None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #CONNECTION_CHECK_INTERVALL}:
 * 				The Time in ms, after what the Client should check the connectivity of the 
 * 				Server, if he received no response from the Server. </li>
 * 		
 * 			<li>{@link #CURRENT_VERSION}:
 * 				The Version of the Network-Interface, that is currently implemented by the 
 * 				Client.
 * 				</li>
 * 
 * 			<li>{@link #debugScreen}:
 * 				The DebugWindow, that will be displayed, if the User opens it by clicking on 
 * 				the referring MenuItem on the MainWindow.</li>
 * 		
 * 			<li>{@link #localServer}:
 * 				Determines, if the Server, the Client will be connected to, is running 
 * 				locally.</li>
 * 		
 * 			<li>{@link #loginScreen}:
 * 				The LoginWindow, that will be used by the Client to connect to a Server.</li>
 * 		
 * 			<li>{@link #mainScreen}:
 * 				The Window, that will be used by the Client, when he is connected to the 
 * 				Server.This is either the LoginWindow (when he is not yet connected), the 
 * 				MainWindow (when he is connected as an Admin), the LowClientWindow (when he is 
 * 				connected as standard Client) or the PasswordWindow (when the Client has to 
 * 				enter the Password for the Admin-Permissions).</li>
 * 		
 * 			<li>{@link #wrapper}:
 * 				The ServerConnection to the Server, that is used by the Client to send the 
 * 				Message to the Server and receive it's responses.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * @author Haeldeus
 * @version 1.4
 */
public class Collector {

	/**
	 * <p style="margin-left: 10px"><em><b>CONNECTION_CHECK_INTERVALL</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final int CONNECTION_CHECK_INTERVALL}
	 * </p>
	 * <p style="margin-left: 20px">Time in ms, when the wrapper should check the connectivity 
	 * of the server, if no response arrived.</p>
	*/
	private static final int CONNECTION_CHECK_INTERVALL = 15000;
	
	/**
	 * <p style="margin-left: 10px"><em><b>CURRENT_VERSION</b></em></p>
	 * <p style="margin-left: 20px">{@code private static final long CURRENT_VERSION}</p>
	 * <p style="margin-left: 20px">The Current implemented Interface-Version.</p>
	 */
	private static final long CURRENT_VERSION = 903L;
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private final ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@code ServerConnection}, that will send the Messages 
	 * to the Server.</p>
	 * @see ServerConnection
	 */
	private final ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>loginScreen</b></em></p>
	 * <p style="margin-left: 20px">{@code private LoginWindow loginScreen}</p>
	 * <p style="margin-left: 20px">The Login-Screen, that will be shown, when starting the 
	 * Client.</p>
	 * @see LoginWindow
	 */
	private LoginWindow loginScreen;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainScreen</b></em></p>
	 * <p style="margin-left: 20px">{@code private Window mainScreen}</p>
	 * <p style="margin-left: 20px">The Main-Screen, that will be shown after logging in to a 
	 * Server.</p>
	 * @see Window
	 * @see MainWindow
	 * @see LowClientWindow
	 * @see PasswordWindow
	 */
	private Window mainScreen;
	
	/**
	 * <p style="margin-left: 10px"><em><b>debugScreen</b></em></p>
	 * <p style="margin-left: 20px">{@code private DebugWindow debugScreen}</p><br>
	 * <p style="margin-left: 20px">The Debug-Screen, that will keep track of Debug 
	 * Notifications from the Server.</p>
	 * @see DebugWindow
	 */
	private DebugWindow debugScreen;
	
	/**
	 * <p style="margin-left: 10px"><em><b>localServer</b></em></p>
	 * <p style="margin-left: 20px">{@code private Server localServer}</p>
	 * <p style="margin-left: 20px">The possible Server, that runs locally. Will be null if 
	 * connecting to another Server, but will be an instance of {@code Server}, if the User 
	 * clicks the "Create own Server"-Button.</p>
	 * @see Server
	 */
	private Server localServer;
	
	/**
	 * <p style="margin-left: 10px"><em><b>Collector</b></em></p>
	 * <p style="margin-left: 20px">{@code public Collector()}</p>
	 * <p style="margin-left: 20px">The Constructor for the Collector. Creates a new 
	 * Connection to the Server and instantiates a new Login-Screen.</p>
	 * @since 1.0
	 */
	public Collector() {
		wrapper = ServerConnectionFactory.createServerConnection(CONNECTION_CHECK_INTERVALL,
				CURRENT_VERSION);
		loginScreen = new LoginWindow(this);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>connect</b></em></p>
	 * <p style="margin-left: 20px">{@code public boolean connect(String, String)}</p>
	 * <p style="margin-left: 20px">Connects to the given IP and Port. Also hides the 
	 * Login-Screen and opens the Main-Screen.</p>
	 * @param ip	The IP, the Client will connect to.
	 * @param port	The Port, the Client will connect to.
	 * @return	{@code true}, if the Connection was established, {@code false} else (e.g. 
	 * 			wrong data).
	 * @since 1.0
	 */
	public boolean connect(String ip, String port) {
		showFail(loginScreen, "Pending IP, please wait!");
		int iport = -1;
		try {
			iport = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return false;
		}		
		if (wrapper.connect(ip, iport)) {
			loginScreen.close();
			mainScreen = new PasswordWindow(ip, iport, new JFrame(), wrapper, this);
			mainScreen.show();
			return true;
		}
		else {
			showFail(loginScreen, 
					"Incorrect Server information. Please try another IP-Address.");
			return false;
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>lowConnect</b></em></p>
	 * <p style="margin-left: 20px">{@code public void lowConnect(String, int)}</p>
	 * <p style="margin-left: 20px">Connects with the LowClient.</p>
	 * @param ip	The IP of the Server as a {@code String} Value.
	 * @param port	The Port of the Server as an {@code Integer} Value.
	 * @since 1.2
	 * @see LowClientWindow
	 */
	public void lowConnect(String ip, int port) {
		if (loginScreen != null) {
			loginScreen.close();
			mainScreen = new LowClientWindow(this, new JFrame(), wrapper, ip, port);
			mainScreen.show();
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>adminConnect</b></em></p>
	 * <p style="margin-left: 20px">{@code public void adminConnect(String, int, String, 
	 * String, boolean)}</p>
	 * <p style="margin-left: 20px">Connects as Admin to the Server with the given IP and 
	 * Port. Also delivers the Passwords for Admins and Players to the MainWindow. If the 
	 * Server is not running locally (the {@code boolean} Parameter), the Player-Password will 
	 * be {@code UNKNOWN}, as there is no possibility for the Client to get the 
	 * Player-Password, if the Server is not running locally.</p>
	 * @param ip	The IP of the Server as a {@code String} Value.
	 * @param port	The Port of the Server as an {@code Integer} Value.
	 * @param adminPassword	The Password for the Admin-Permissions as a {@code String} Value.
	 * @param playerPassword	The Password for the Player as a {@code String} Value.
	 * @param localServer	{@code Boolean} value, if a Server is running with this Client as 
	 * 						Host.
	 * @since 1.3
	 */
	public void adminConnect(String ip, int port, String adminPassword, String playerPassword, 
			boolean localServer) {
		mainScreen = new MainWindow(this, wrapper, ip, port, adminPassword, 
				playerPassword, localServer);
		if (loginScreen != null)
			loginScreen.close();
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				mainScreen.show();
			}
		});
		debugScreen = new DebugWindow(wrapper);
		wrapper.addDebugNotificationListener(debugScreen);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>disconnect</b></em></p>
	 * <p style="margin-left: 20px">{@code public void disconnect()}</p>
	 * <p style="margin-left: 20px">Disconnects from the Server and displays the Login-Screen.
	 * </p>
	 * @since 1.0
	 */
	public void disconnect() {
		if (debugScreen != null)
			debugScreen.close();
		mainScreen.close();
		wrapper.close();
		if (localServer != null)
			localServer.shutDown();
		loginScreen = new LoginWindow(this);
		mainScreen = loginScreen;
		mainScreen.show();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(Window, String)}</p>
	 * <p style="margin-left: 20px">Shows the Fail-Label with the given {@code text} on the 
	 * given {@code Window}.</p>
	 * @param window	The Window to display the Message on.
	 * @param text	The text to be shown.
	 * @since 1.0
	 * @see Window
	 */
	public void showFail(Window window, String text) {
		window.showFail(text);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>createLocalServer</b></em></p>
	 * <p style="margin-left: 20px">{@code public void createLocalServer(String)}</p>
	 * <p style="margin-left: 20px">Starts the Creation of a new local Server. Will close 
	 * the {@code LoginWindow} and open a new {@code SetPasswordWindow} to enter the 
	 * Passwords. If the given Port cannot be parsed, the LoginScreen won't close and a 
	 * Message is printed on it.</p>
	 * @param port	The Port, the Server will have, as a {@code String} Value.
	 * @since 1.0
	 * @see LoginWindow
	 * @see SetPasswordWindow
	 */
	public void createLocalServer(String port){
		try {
			int iport = -1;
			iport = Integer.parseInt(port);
			if (loginScreen != null)
				loginScreen.close();
			mainScreen = new SetPasswordWindow(this, iport);
			mainScreen.show();
		} catch (NumberFormatException nfe) {
			showFail(loginScreen, "Please enter a real Number as Port");
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>createLocalServerFinal</b></em></p>
	 * <p style="margin-left: 20px">{@code public void createLocalServerFinal(int, String, 
	 * String)}</p>
	 * <p style="margin-left: 20px">The final Method to create a local Server. This is 
	 * called by the {@code SetPasswordWindow}, when the User entered the Passwords needed for 
	 * the Server. If the Port is already in use, no Server will be created, the 
	 * {@code LoginWindow} will be shown again and a Message is printed on it.</p>
	 * @param port	The Port of the new Server as an {@code Integer} Value.
	 * @param adminPassword	The Password, that has to be entered to connect as an Admin, as a 
	 * 						{@code String} Value
	 * @param playerPassword	The Password, that has to be entered to connect as a Player, 
	 * 							as a {@code String} Value
	 * @since 1.4
	 */
	public void createLocalServerFinal(int port, String adminPassword, String playerPassword) {
		try {
			localServer = ServerFactory.createServer(port, adminPassword, playerPassword);
			//TODO: make selectable, if youtube-dl should be updated or not
			localServer.startUp();		//throws IOException, if the Port is already in use.
			wrapper.connect("localhost", port);
			wrapper.addPermission(Permission.ADMIN, adminPassword);
			mainScreen.close();
			adminConnect("localhost", port, adminPassword, playerPassword, true);
		} catch (IOException e) {
			loginScreen.show();
			showFail(loginScreen, "Port is already in use, please enter another Port.");
			mainScreen.close();
		}
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>startUp</b></em></p>
	 * <p style="margin-left: 20px">{@code public void startUp()}</p>
	 * <p style="margin-left: 20px">Starts the Client and shows the {@code LoginWindow}.</p>
	 * @since 1.0
	 * @see LoginWindow
	 */
	public void startUp() {
		this.loginScreen.show();
	}

	/**
	 * <p style="margin-left: 10px"><em><b>showUDPFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showUDPFail(String)}</p>
	 * <p style="margin-left: 20px">Calls the showFail-Method, when the UDP-Connect failed.
	 * This is needed, because the Listener doesn't have an Instance of any Window to call 
	 * {@code showFail(Window, String)}.</p>
	 * @param string	The Message to be shown.
	 * @since 1.0
	 * @see #showFail(Window, String)
	 * @see listener.UDPListener
	 */
	public void showUDPFail(String string) {
		showFail(loginScreen, string);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>showDebugWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showDebugWindow()}</p>
	 * <p style="margin-left: 20px">Shows the DebugWindow.</p>
	 * @since 1.1
	 * @see DebugWindow
	 */
	public void showDebugWindow() {
		debugScreen.show();
	}	
}
