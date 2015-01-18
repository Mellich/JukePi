package client;

import java.util.concurrent.Semaphore;

import client.listener.NotificationListener;
import client.serverconnection.ServerConnection;
import client.visuals.IdleViewer;
import client.visuals.Visualizer;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayerStarter extends Application implements NotificationListener {
	
	private ServerConnection server;
	private volatile OMXPlayer player = null;
	private Visualizer viewer;
	private volatile boolean videoMode = false;
	private Thread listenBroadcast;
	private Semaphore playerMutex = new Semaphore(1);
	private volatile int pauseResumeWaitingCount = 0;
	private volatile int skipWaitingCount = 0;

	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		server = ServerConnectionFactory.createServerConnection(15000);
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
		viewer.showDebugInfo("New Pause/Resume request! In Line:"+pauseResumeWaitingCount+" New Playstatus: "+isPlaying);
		try {
			playerMutex.acquire();
			if (player != null && isPlaying != player.isPlaying()){
				player.pauseResume();
				if (pauseResumeWaitingCount == 1){
					if (videoMode)
						viewer.showIdleScreen(!player.isPlaying());
					viewer.updateInfos();
				}
			}			
		} catch (Exception e) {
			viewer.showDebugInfo("Error while pause/resume track: "+e.getLocalizedMessage());
		}
		finally{
			pauseResumeWaitingCount--;
			playerMutex.release();
		}
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL,boolean isVideo) {
		skipWaitingCount++;
		try {
			viewer.showDebugInfo("New Skip request! In Line:"+skipWaitingCount+" Title: "+title);
			playerMutex.acquire();
			videoMode = isVideo;
			if (player != null){
					player.skip();
			}
			player = new OMXPlayer(this);
			player.play(videoURL);
			if (skipWaitingCount == 1){
				if (videoMode)
					viewer.showIdleScreen(false);
				viewer.updateInfos();
			}
		} catch (Exception e) {
			viewer.showDebugInfo("Error while skipping track: "+e.getLocalizedMessage());
		}
		finally{
			skipWaitingCount--;
			playerMutex.release();
		}
	}

	@Override
	public void onDisconnect() {
		viewer.showDebugInfo("Disconnect from server...");
		viewer.showIdleScreen(true);
		if (player != null)
			player.skip();
		player = null;
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
	
	public void trackIsFinished(boolean wasSkipped){
		viewer.showIdleScreen(true);
		viewer.updateInfos();
		if (!wasSkipped)
			server.notifyPlayerFinished((String[] s) -> {});
	}

}
