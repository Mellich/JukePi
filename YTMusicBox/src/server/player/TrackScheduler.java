package server.player;

import java.util.concurrent.Semaphore;

import network.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import utilities.IO;


public class TrackScheduler extends Thread {
	
	private YTJBServer server;
	private boolean running;
	private MusicPlayer player;
	private MusicTrack current;
	public Semaphore playableTrack = new Semaphore(0);
	
	public TrackScheduler(YTJBServer server) {
		this.server = server;
		running = true;
	}

	
	public synchronized void setRunning(boolean r){
		running = r;
	}
	
	public void skip(){
		if (player != null)
			player.skip();
	}
	
	public void pauseResume(){
		if (player != null)
			player.pauseResume();
	}
	
	public boolean isPlaying(){
		if (player != null)
			return player.isPlaying();	
		else return false;
	}

	
	public MusicTrack getCurrentTrack(){
		return current;
	}
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Player is ready to play your music...");
		try {
			while (running){
				IO.printlnDebug(this, "getting next track in the list");
				player = null;
				current = server.chooseNextTrack();
				while (current == null){
					IO.printlnDebug(this, "waiting for a track added to a list...");
					playableTrack.acquire();
					current = server.chooseNextTrack();
				}
				server.showLogo(false);
				IO.printlnDebug(this,"Playing next track: "+current.getTitle());
				server.notifyClients(MessageType.NEXTTRACKNOTIFY);
				player = new OMXPlayer();
				player.play(current);
				server.showLogo(true);
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
