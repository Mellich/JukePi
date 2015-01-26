package client;

import client.serverconnection.ServerConnection;
import client.serverconnection.UDPTimeoutException;
import client.visuals.Visualizer;
import utilities.IO;

public class BroadcastListener implements Runnable {
	
	private ServerConnection server;
	private Visualizer viewer;

	public BroadcastListener(ServerConnection server,Visualizer viewer2) {
		this.server = server;
		this.viewer = viewer2;
	}

	@Override
	public void run() {
		IO.printlnDebug(this, "Waiting for message from server...");
		boolean serverNotFound = true;
		while (serverNotFound){
			try {
				ServerAddress address = server.udpScanning();
				if (server.connect(address))
					IO.printlnDebug(this, "Connected!");
				server.setMeAsPlayer();
				viewer.updateInfos();
				serverNotFound = false;
			} catch (UDPTimeoutException e) {
				viewer.showDebugInfo("Timeout: No server found! Further searching...");
			}
		}
	}

}
