package client;

import utilities.IO;
import client.visuals.IdleViewer;
import clientinterface.listener.NotificationListener;
import clientwrapper.ClientWrapper;
import clientwrapper.YTJBClientWrapper;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayerStarter extends Application implements NotificationListener {
	
	private ClientWrapper server;
	private OMXPlayer player = null;
	private IdleViewer viewer;
	private Thread listenBroadcast;

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		viewer = new IdleViewer(primaryStage);
		viewer.showLogoSync(true);
		server = new YTJBClientWrapper();
		server.addNotificationListener(this);
		listenBroadcast = new Thread(new BroadcastListener(server,viewer));
		listenBroadcast.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public synchronized void onPauseResumeNotify(boolean isPlaying) {
		if (player != null)
			player.pauseResume();
		viewer.showLogoSync(!isPlaying);
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onNextTrackNotify(String title, String videoURL) {
		viewer.showLogoSync(true);
		if (player != null)
				player.skip();
		player = new OMXPlayer(server);
		player.play(videoURL);
		viewer.showLogoSync(false);
	}

	@Override
	public void onDisconnect() {
		IO.printlnDebug(this, "Disconnect from Server!");
		viewer.editConnectionDetails("NICHT VERBUNDEN", 0);
		listenBroadcast.start();
	}

	@Override
	public void onGapListChangedNotify(String name) {
		viewer.currentGaplistSync(name);
		server.getLoadGapListStatus((String[] s) -> viewer.gaplistStatusSync(Integer.parseInt(s[0]),Integer.parseInt(s[1])));
	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		server.getLoadGapListStatus((String[] s) -> viewer.gaplistStatusSync(Integer.parseInt(s[0]),Integer.parseInt(s[1])));
		
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		// TODO Auto-generated method stub
		
	}

}
