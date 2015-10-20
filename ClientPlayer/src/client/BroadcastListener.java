package client;

import messages.Permission;
import client.serverconnection.ServerConnection;
import client.serverconnection.exceptions.UDPTimeoutException;
import client.visuals.Visualizer;
import utilities.IO;

public class BroadcastListener implements Runnable {
	
	private ServerConnection server;
	private Visualizer viewer;
	private PlayerStarter starter;

	public BroadcastListener(ServerConnection server,Visualizer viewer2,PlayerStarter starter) {
		this.server = server;
		this.viewer = viewer2;
		this.starter = starter;
	}

	@Override
	public void run() {
		IO.printlnDebug(this, "Waiting for message from server...");
		boolean serverNotFound = true;
		while (serverNotFound){
			try {
				ServerAddress address = server.udpScanning();
				if (server.connect(address)){
					IO.printlnDebug(this, "Connected!");
					server.addDefaultNotificationListener(starter);
					server.addPermission(Permission.PLAYER, "player");
					//server.addDebugNotificationListener(starter);
					server.addGapListNotificationListener(starter);
					server.addPauseResumeNotificationListener(starter);
					server.addSeekNotificationListener(starter);
					server.setMeAsPlayer();
					Status s = starter.getCurrentStatus();
					s.setServerIP(server.getIPAddress());
					s.setServerPort(server.getPort());
					s.setGaplistTitle(server.getCurrentGapListName());
					s.setCurrentTrackTitle(server.getCurrentSong().getName());
					viewer.updateInfos(s);
					serverNotFound = false;
				}
			} catch (UDPTimeoutException e) {
				viewer.showDebugInfo("Timeout: No server found! Further searching...");
			}
		}
	}

}
