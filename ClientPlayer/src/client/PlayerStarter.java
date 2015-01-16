package client;

import java.util.concurrent.Semaphore;

import utilities.IO;
import client.visuals.IdleViewer;
import client.visuals.Visualizer;
import clientinterface.listener.NotificationListener;
import clientwrapper.ClientWrapper;
import clientwrapper.YTJBClientWrapper;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayerStarter extends Application implements NotificationListener {
	
	private ClientWrapper server;
	private volatile OMXPlayer player = null;
	private Visualizer viewer;
	private Thread listenBroadcast;
	private Semaphore playerMutex = new Semaphore(1);
	private volatile int pauseResumeWaitingCount = 0;
	private volatile int skipWaitingCount = 0;

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		server = new YTJBClientWrapper(15000);
		viewer = new IdleViewer(primaryStage,server);
		viewer.showIdleScreen(true);
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
					viewer.showIdleScreen(!player.isPlaying());
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
			viewer.showIdleScreen(true);
			if (player != null)
					player.skip();
			player = new OMXPlayer(server);
			player.play(videoURL);
			if (skipWaitingCount == 1)
				viewer.showIdleScreen(false);
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
		viewer.showIdleScreen(true);
		if (player != null)
			player.skip();
		viewer.resetView();
		listenBroadcast = new Thread(new BroadcastListener(server,viewer));
		listenBroadcast.start();
	}

	@Override
	public void onGapListChangedNotify(String name) {
		viewer.updateInfos();
	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		viewer.updateInfos();
		
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		viewer.updateInfos();
	}

}
