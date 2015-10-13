package connection;


import java.net.BindException;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import server.Server;
import server.ServerFactory;
import windows.DebugWindow;
import windows.LogIn;
import windows.LowClientWindow;
import windows.MainWindow;
import windows.Window;
import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;

/**
 * The Collector, that will start the Client. Also provides all necessary information for each Frame, to work properly.
 * @author Haeldeus
 * @version 1.2
 */
public class Collector {

	/**
	 * Time in ms, when the wrapper should check the connectivity of the server, if no response 
	 * arrived.
	*/
	private static final int CONNECTIONCHECKINTERVALL = 15000;
	
	/**
	 * The Current Interface-Version, that is implemented.
	 */
	private static final long CURRENT_VERSION = 901L;
	
	/**
	 * The wrapper, that will send the Messages.
	 * @see ServerConnection
	 */
	private final ServerConnection wrapper;
	
	/**
	 * The Login-Screen, that will be shown, when starting the Client.
	 * @see LogIn
	 */
	private LogIn loginScreen;
	
	/**
	 * The Main-Screen, that will be shown after logging in to a Server.
	 * @see Window
	 * @see MainWindow
	 * @see LowClientWindow
	 */
	private Window mainScreen;
	
	/**
	 * The Debug-Screen, that will keep track of Debug Notifications from the Server.
	 * @see DebugWindow
	 */
	private DebugWindow debugScreen;
	
	/**
	 * The possible Server, that runs locally. Will be null if connecting to another Server, 
	 * but will be an instance of Server, if the User clicks the "Create own Server"-Button.
	 * @see Server
	 */
	private Server localServer;
	
	/**
	 * The Screen, that is currently visible.
	 * @see JFrame
	 */
	private JFrame visibleScreen;
	
	/**
	 * The Gaplist, which is played on the Server.
	 * @see Song
	 */
	private Song[] gaplist;
	
	/**
	 * The Wishlist of Songs, that will be played.
	 * @see Song
	 */
	private Song[] wishlist;
	
	/**
	 * The Constructor for the Collector. Creates a new Connection to the Server and 
	 * instantiates a new Login-Screen.
	 * @since 1.0
	 */
	public Collector() {
		wrapper = ServerConnectionFactory.createServerConnection(CONNECTIONCHECKINTERVALL,CURRENT_VERSION);
		visibleScreen = new JFrame();
		loginScreen = new LogIn(this, visibleScreen);
	}

	/**
	 * Connects to the given IP and Port. Also hides the Login-Screen and opens the 
	 * Main-Screen.
	 * @param ip	The IP, the Client will connect to.
	 * @param port	The Port, the Client will connect to.
	 * @return	true, if the Connection was established, false else (e.g. wrong data).
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

//		mainScreen = new MainWindow(this, visibleScreen, wrapper, gaplist, wishlist, ip, iport);
		
		if (wrapper.connect(ip, iport)) {
			loginScreen.close();
			mainScreen = new MainWindow(this, visibleScreen, wrapper, gaplist, wishlist, ip, iport);
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					mainScreen.show();
				}
			});
			debugScreen = new DebugWindow(wrapper);
			wrapper.addDebugNotificationListener(debugScreen);
			return true;
		}
		else {
			showFail(loginScreen, "Incorrect Server information. Please try another IP-Address.");
			return false;
		}
	}
	
	/**
	 * Connects with the LowClient.
	 * @param ip	The IP of the Server as a String Value.
	 * @param port	The Port of the Server as a String Value.
	 * @return	{@code true}, if the Connection was established, {@code false} else.
	 * @since 1.2
	 */
	public boolean lowConnect(String ip, String port) {
		showFail(loginScreen, "Pending IP, please wait!");
		int iport = -1;
		try {
			iport = Integer.parseInt(port);
		} catch (NumberFormatException nfe) {
			return false;
		}
		
		if (wrapper.connect(ip, iport)) {
			loginScreen.close();
			mainScreen = new LowClientWindow(this, visibleScreen, wrapper, wishlist, ip, iport);
			mainScreen.show();
			return true;
		}
		else {
			showFail(loginScreen, "Incorrect Server Information. Please try another IP-Address");
			return false;
		}
	}
	
	/**
	 * Disconnects from the Server and displays the Login-Screen.
	 * @since 1.0
	 */
	public void disconnect() {
		if (debugScreen != null)
			debugScreen.close();
		mainScreen.close();
		wrapper.close();
		if (localServer != null)
			localServer.shutDown();
		visibleScreen.getContentPane().removeAll();
		loginScreen.show();
	}
	
	/**
	 * Shows the Fail-Label with the given text on the given window.
	 * @param window	The window to display the Message on.
	 * @param text	The text to be shown.
	 * @since 1.0
	 */
	public void showFail(Window window,String text) {
		window.showFail(text);
	}
	
	/**
	 * Creates a local Server with the given Port.
	 * @param port	The Port, the Server will have.
	 * @since 1.0
	 */
	public void createLocalServer(String port){
		int iport = -1;
		try {
			iport = Integer.parseInt(port);
			localServer = ServerFactory.createServer(iport);
			//TODO: make selectable, if youtube-dl should be updated or not  
			localServer.startUp();
			this.connect("localhost", ""+port);
		} catch (NumberFormatException nfe) {
			showFail(loginScreen, "Please insert a real number at the Port-Field.");
		} catch (BindException e) {
			showFail(loginScreen, "Port is already in use, please enter another Port.");
		}
		
	}
	
	/**
	 * Starts the Client.
	 * @since 1.0
	 */
	public void startUp() {
		this.loginScreen.show();
	}

	/**
	 * Calls the showFail-Method, when the UDP-Connect failed.
	 * @param string	The Message to be shown.
	 * @since 1.0
	 */
	public void showUDPFail(String string) {
		showFail(loginScreen, string);
	}
	
	/**
	 * Sets the given lists as new Wish- and Gaplist.
	 * @param wishlist	The new Wishlist.
	 * @param gaplist	The new Gaplist.
	 * @since 1.0
	 */
	public void setLists(Song[] wishlist, Song[] gaplist) {
		this.wishlist = wishlist;
		this.gaplist = gaplist;
	}

	/**
	 * Shows the DebugWindow.
	 * @since 1.1
	 */
	public void showDebugWindow() {
		debugScreen.show();
	}
}
