package gui;

import server.Server;

public class StartUpThread extends Thread{
	//TODO Lambda-Function to start the Server.
	
	private Server s;
	
	public StartUpThread(Server s) {
		this.s= s;
	}
	
	@Override
	public void run() {
		s.startUp();
	}
}
