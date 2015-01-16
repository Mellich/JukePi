package client;

import client.visuals.Visualizer;
import clientwrapper.ClientWrapper;
import utilities.IO;

public class BroadcastListener implements Runnable {
	
	private ClientWrapper server;
	private Visualizer viewer;

	public BroadcastListener(ClientWrapper server,Visualizer viewer2) {
		this.server = server;
		this.viewer = viewer2;
	}

	@Override
	public void run() {
		IO.printlnDebug(this, "Waiting for message from server...");
		if (server.connect(server.waitForUDPConnect()))
			IO.printlnDebug(this, "Connected!");
		server.setMeAsPlayer();
		viewer.updateInfos();
	}

}
