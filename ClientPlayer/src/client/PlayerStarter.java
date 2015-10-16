package client;

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
	private PlayerFactory playerFactory;
	private volatile Player player = null;
	private Visualizer viewer;
	private volatile boolean videoMode = false;
	private Thread listenBroadcast;
	
	@Override
	public synchronized void start(Stage primaryStage) throws Exception {
		if (System.getProperty("os.name").equals("Linux")){
			playerFactory = new OMXPlayerFactory();
			IO.printlnDebug(this, "Choosing OMXPlayer for media playback.");
		}else{
			playerFactory = new VLCPlayerFactory();
			IO.printlnDebug(this, "Choosing VLCPlayer for media playback.");
		}
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
		viewer.showDebugInfo("New Pause/Resume request! New Playstatus: "+isPlaying);
		try {
			if (player != null){
				if(isPlaying)
					player.resume();
				else player.pause();
				Thread.sleep(100);
				if (videoMode)
					viewer.showIdleScreen(!player.isPlaying());
				viewer.updateInfos();
			}			
		} catch (Exception e) {
			viewer.showDebugInfo("Error while pause/resume track: "+e.getLocalizedMessage());
		}
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL,boolean isVideo) {
		try {
			viewer.showDebugInfo("New Skip request! Title: "+title);
			videoMode = isVideo;
			if (player != null){
					player.skip();
			}else player = playerFactory.newInstance(this);
			player.play(videoURL);
			if (videoMode)
				viewer.showIdleScreen(false);
			viewer.updateInfos();
			
		} catch (Exception e) {
			viewer.showDebugInfo("Error while skipping track: "+e);
			if (player != null)
				player.destroy();
			player = null;
			e.printStackTrace();
		}
	}

	@Override
	public void onDisconnect() {
		viewer.showDebugInfo("Disconnect from server...");
		viewer.resetView();
		viewer.showIdleScreen(true);
		if (player != null)
			player.skip();
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
		IO.printlnDebug(this, "Track finished! was skipped="+wasSkipped);
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
		viewer.showDebugInfo("New seek request!");
		try {
			if (player != null){
				if (forward)
					player.seekForward();
				else player.seekBackward();
			}
		} catch (Exception e) {
			viewer.showDebugInfo("Error while seek in track: "+e.getLocalizedMessage());
		}	
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		// TODO Auto-generated method stub
		
	}

}
