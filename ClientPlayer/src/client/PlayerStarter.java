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
	private OMXPlayer player;
	private IdleViewer viewer;

	@Override
	public void start(Stage primaryStage) throws Exception {
		viewer = new IdleViewer(primaryStage);
		viewer.showLogo(true);
		List<String> args = this.getParameters().getRaw();
		String ip = args.get(0);
		int port = Integer.parseInt(args.get(1));
		server = new YTJBClientWrapper(ip,port);
		server.addNotificationListener(this);
		if (server.connect())
			IO.printlnDebug(this, "Connected!");
		server.setMeAsPlayer();
		server.getCurrentGapListName((String[] s) -> viewer.currentGaplist(s[0]));
		viewer.editConnectionDetails(ip, port);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void onPauseResumeNotify() {
		if (player != null)
			player.pauseResume();		
	}

	@Override
	public void onGapListCountChangedNotify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onListUpdatedNotify() {
		server.getLoadGapListStatus((String[] s) -> viewer.gaplistStatus(Integer.parseInt(s[0]),Integer.parseInt(s[1])));
		
	}

	@Override
	public void onNextTrackNotify() {
		if (player != null)
			player.skip();
		server.getNextVideoURL((String[] s) -> {player = new OMXPlayer(server,viewer); player.play(s[0]);});	
	}

	@Override
	public void onDisconnect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGapListChangedNotify() {
		server.getCurrentGapListName((String[] s) -> viewer.currentGaplist(s[0]));
		server.getLoadGapListStatus((String[] s) -> viewer.gaplistStatus(Integer.parseInt(s[0]),Integer.parseInt(s[1])));
	}

}
