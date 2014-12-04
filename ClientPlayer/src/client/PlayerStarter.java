package client;

import java.util.List;

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

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		viewer = new IdleViewer(primaryStage);
		viewer.showLogoSync(true);
		List<String> args = this.getParameters().getRaw();
		String ip = args.get(0);
		int port = Integer.parseInt(args.get(1));
		server = new YTJBClientWrapper(ip,port);
		server.addNotificationListener(this);
		if (server.connect())
			IO.printlnDebug(this, "Connected!");
		server.setMeAsPlayer();
		server.getCurrentGapListName((String[] s) -> viewer.currentGaplistSync(s[0]));
		viewer.editConnectionDetails(ip, port);
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
