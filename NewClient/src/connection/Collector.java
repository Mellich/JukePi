package connection;

import javax.swing.JFrame;

import server.Server;
import server.ServerFactory;
import windows.LogIn;
import windows.MainWindow;
import windows.Window;
import client.ServerConnectionFactory;
import client.listener.NotificationListener;
import client.serverconnection.ServerConnection;

public class Collector implements NotificationListener{

	/**
	 * Time in ms when the wrapper should check the connectivity of the server if no response 
	 * arrived.
	*/
	private static final int CONNECTIONCHECKINTERVALL = 15000;
	
	/**
	 * The wrapper, that will send the Messages.
	 */
	private ServerConnection wrapper;
	
	private LogIn loginScreen;
	
	private MainWindow mainScreen;
	
	private Server localServer;
	
	private JFrame visibleScreen;
	
	public Collector() {
	/*	gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
		gaplistCollectionModel = new DefaultListModel<String>();
		contentModel = new DefaultListModel<String>();
	*/	wrapper = ServerConnectionFactory.createServerConnection(CONNECTIONCHECKINTERVALL);
		visibleScreen = new JFrame();
		loginScreen = new LogIn(this, visibleScreen);
	}
	
	@Override
	public void onPauseResumeNotify(boolean isPlaying) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSeekNotify(boolean forward) {
		//Nothing needed
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	public boolean connect(String ip, String port) {
		showFail(loginScreen, "Pending IP, please wait!");
		int iport = -1;
		try {
			iport = Integer.parseInt(port);
		} catch (NumberFormatException e) {
			return false;
		}
		wrapper.addNotificationListener(this);
		
		if (wrapper.connect(ip, iport)) {
			loginScreen.close();
			mainScreen = new MainWindow(this, visibleScreen, wrapper);
			mainScreen.show();
			mainScreen.setIpAndPort(ip, iport);
			return true;
		}
		else {
			showFail(loginScreen, "Incorrect Server information. Please try another IP-Address.");
			return false;
		}
	}
	
	public void disconnect() {
		mainScreen.close();
		wrapper.close();
		if (localServer != null)
			localServer.shutDown();
		visibleScreen.getContentPane().removeAll();
		loginScreen = new LogIn(this, visibleScreen);
		loginScreen.show();
	}
	
	public void showFail(Window window,String text) {
		window.showFail(text);
	}
	
	public void createLocalServer(int port){
		localServer = ServerFactory.createServer(port);
		localServer.startUp();
		this.connect("localhost", ""+port);
	}
	
	public void startUp() {
		this.loginScreen.show();
	}

	public void showUDPFail(String string) {
		showFail(loginScreen, string);
	}
}
