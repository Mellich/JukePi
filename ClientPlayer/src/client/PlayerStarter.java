package client;

import java.util.concurrent.Semaphore;

import utilities.IO;
import client.listener.*;
import client.player.OMXPlayerFactory;
import client.player.Player;
import client.player.PlayerFactory;
import client.player.VLCPlayerFactory;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.visuals.IdleViewer;
import client.visuals.Visualizer;
import javafx.application.Application;
import javafx.stage.Stage;

public class PlayerStarter extends Application implements DefaultNotificationListener, PauseResumeNotificationListener, GapListNotificationListener, SeekNotificationListener, DebugNotificationListener {
	
	private ServerConnection server;
	
	/**
	 * set the used player here
	 */
	private PlayerFactory playerFactory = new OMXPlayerFactory();
	private volatile Player player = null;
	private Visualizer viewer;
	private volatile boolean videoMode = false;
	private Thread listenBroadcast;
	private Semaphore playerMutex = new Semaphore(1);
	private volatile int pauseResumeWaitingCount = 0;
	private volatile int skipWaitingCount = 0;
	private volatile int seekWaitingCount = 0;
	
	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		server = ServerConnectionFactory.createServerConnection(15000,900);
		viewer = new IdleViewer(primaryStage,server);
		viewer.showIdleScreen(true);
		listenBroadcast = new Thread(new BroadcastListener(server,viewer,this));
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
				Thread.sleep(100);
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
	public void onNextTrackNotify(String title, String videoURL,boolean isVideo) {
		skipWaitingCount++;
		try {
			viewer.showDebugInfo("New Skip request! In Line:"+skipWaitingCount+" Title: "+title);
			playerMutex.acquire();
			videoMode = isVideo;
			if (player != null){
					player.skip();
			}
			if (player == null)
				player = playerFactory.newInstance(this);
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
		viewer.resetView();
		viewer.showIdleScreen(true);
		if (player != null)
			player.skip();
		player = null;
		listenBroadcast = new Thread(new BroadcastListener(server,viewer,this));
		listenBroadcast.start();
	}

	@Override
	public void onGapListChangedNotify(String name) {
		viewer.updateInfos();
	}

	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		viewer.updateInfos();
		
	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		viewer.updateInfos();
	}
	
	public void trackIsFinished(boolean wasSkipped){
		viewer.showIdleScreen(true);
		IO.printlnDebug(this, "Track finished!");
		if (!wasSkipped)
			server.notifyPlayerFinished((String[] s) -> {viewer.updateInfos();});
	}

	@Override
	public void onClientCountChangedNotify(int newClientCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPlayerCountChangedNotify(int newPlayerCount) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onNewOutput(String output) {
		viewer.showDebugInfo("SERVER: "+output);
		
	}

	@Override
	public void onSeekNotify(boolean forward) {
		seekWaitingCount++;
		viewer.showDebugInfo("New seek request! In Line:"+seekWaitingCount);
		try {
			playerMutex.acquire();
			if (player != null){
				if (forward)
					player.seekForward();
				else player.seekBackward();
			}
		} catch (Exception e) {
			viewer.showDebugInfo("Error while seek in track: "+e.getLocalizedMessage());
		}
		finally{
			seekWaitingCount--;
			playerMutex.release();
		}		
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

}
