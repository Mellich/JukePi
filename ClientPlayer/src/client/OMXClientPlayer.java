package client;

import java.util.concurrent.Semaphore;

import utilities.IO;
import clientinterface.listener.NotificationListener;
import clientwrapper.ClientWrapper;
import clientwrapper.YTJBClientWrapper;

public class OMXClientPlayer implements NotificationListener{
	private ClientWrapper server;
	private OMXPlayer player;
	private String url = "";
	private Semaphore awaitURL = new Semaphore(0);
	
	public OMXClientPlayer(String ipAddress, int port) {
		server = new YTJBClientWrapper(ipAddress,port);
		server.addNotificationListener(this);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (server.connect())
			IO.printlnDebug(this, "Connected!");
		server.setMeAsPlayer();
		//this.playTracks();
	}
	
	private void playTracks(){
		try {
			while (true){
				awaitURL.acquire();
				player = new OMXPlayer(server);
				player.play(url);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		OMXClientPlayer c = new OMXClientPlayer(args[0],Integer.parseInt(args[1]));
	}

	@Override
	public synchronized void onPauseResumeNotify() {
		if (player != null && !player.isFinished())
			player.pauseResume();
	}

	@Override
	public synchronized void onGapListCountChangedNotify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onListUpdatedNotify() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public synchronized void onNextTrackNotify() {
		if (player != null)
			player.skip();
		server.getNextVideoURL((String[] s) -> {player = new OMXPlayer(server); player.play(s[0]);});
		/*server.getCurrentTrackTitle((String[] s) -> System.out.println("Now playing:"+s[0]));
		try {
			Thread.sleep(20000);
			server.notifyPlayerFinished((String[] s) -> {});
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	@Override
	public synchronized void onDisconnect() {
		IO.printlnDebug(this, "Disconnected!");
	}
}
