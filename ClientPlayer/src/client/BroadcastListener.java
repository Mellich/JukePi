package client;

import javafx.application.Platform;
import client.visuals.IdleViewer;
import clientwrapper.ClientWrapper;
import utilities.IO;

public class BroadcastListener implements Runnable {
	
	private ClientWrapper server;
	private IdleViewer viewer;

	public BroadcastListener(ClientWrapper server,IdleViewer viewer) {
		this.server = server;
		this.viewer = viewer;
	}

	@Override
	public void run() {
		IO.printlnDebug(this, "Waiting for message from server...");
		String[] address = server.waitForUDPConnect();
		String ip = address[0];
		int port = Integer.parseInt(address[1]);
		if (server.connect(ip,port))
			IO.printlnDebug(this, "Connected!");
		server.setMeAsPlayer();
		server.getCurrentGapListName((String[] s) -> viewer.currentGaplistSync(s[0]));
		Platform.runLater(() -> viewer.editConnectionDetails(ip, port));
	}

}
