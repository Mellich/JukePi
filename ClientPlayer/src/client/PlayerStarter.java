package client;

import java.util.concurrent.Semaphore;

import utilities.IO;
import client.visuals.IdleViewer;
import clientinterface.listener.NotificationListener;
import clientwrapper.ClientWrapper;
import clientwrapper.YTJBClientWrapper;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

public class PlayerStarter extends Application implements NotificationListener {
	
	private ClientWrapper server;
	private volatile OMXPlayer player = null;
	private IdleViewer viewer;
	private Thread listenBroadcast;
	private Semaphore playerMutex = new Semaphore(1);
	private volatile int pauseResumeWaitingCount = 0;
	private volatile int skipWaitingCount = 0;

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		viewer = new IdleViewer(primaryStage);
		viewer.showLogoSync(true);
		server = new YTJBClientWrapper(15000);
		server.addNotificationListener(this);
		listenBroadcast = new Thread(new BroadcastListener(server,viewer));
		listenBroadcast.start();
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onPauseResumeNotify(boolean isPlaying) {
		pauseResumeWaitingCount++;
		try {
			playerMutex.acquire();
			if (player != null && isPlaying != player.isPlaying()){
				player.pauseResume();
				if (pauseResumeWaitingCount == 1)
					viewer.showLogoSync(!player.isPlaying());
			}
			pauseResumeWaitingCount--;
			playerMutex.release();			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL) {
		skipWaitingCount++;
		try {
			playerMutex.acquire();
			viewer.showLogoSync(true);
			if (player != null)
					player.skip();
			player = new OMXPlayer(server);
			player.play(videoURL);
			if (skipWaitingCount == 1)
				viewer.showLogoSync(false);
			skipWaitingCount--;
			playerMutex.release();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnect() {
		IO.printlnDebug(this, "Disconnect from Server!");
		viewer.showLogoSync(true);
		if (player != null)
			player.skip();
		Platform.runLater(() -> viewer.editConnectionDetails("NICHT VERBUNDEN", 0));
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
