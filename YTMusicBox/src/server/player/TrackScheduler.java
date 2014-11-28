package server.player;

import java.util.concurrent.Semaphore;

import messages.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import utilities.IO;


public class TrackScheduler extends Thread {
	
	private YTJBServer server;
	private boolean running;
	private MusicPlayer player;
	private MusicTrack current;
	private Semaphore playableTrack = new Semaphore(0);
	private Semaphore playerAvailable = new Semaphore(0);
	
	public TrackScheduler(YTJBServer server) {
		this.server = server;
		running = true;
	}
	
	public void notifyPlayableTrack(){
		playableTrack.release();
	}
	
	public void notifyPlayerAvailable(){
		playerAvailable.release();
	}

	
	public synchronized void setRunning(boolean r){
		running = r;
	}
	
	public boolean skip(){
		if (player != null)
			return player.skip();
		else return false;
	}
	
	public boolean pauseResume(){
		if (player != null){
			boolean result = player.pauseResume();
			String[] args = new String[1];
			args[0] = ""+isPlaying();
			server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
			return result;
		}
		return false;
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
			player = null;
			while (running){
				IO.printlnDebug(this, "getting next track in the list");
				current = server.chooseNextTrack();
				String[] args = new String[1];
				while (current == null){
					IO.printlnDebug(this, "waiting for a track added to a list...");
					playableTrack.acquire();
					current = server.chooseNextTrack();
				}
				if (server.getPlayerCount() == 0){
					IO.printlnDebug(this, "Waiting for available player...");
					playerAvailable.acquire();
				}
				String[] argsNext = new String[2];
				argsNext[0] = current.getTitle();
				argsNext[1] = current.getVideoURL();
				server.notifyClients(MessageType.NEXTTRACKNOTIFY,argsNext);
				args[0] = ""+true;
				server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
				IO.printlnDebug(this,"Playing next track: "+current.getTitle());
				player = new ClientPlayer(server,this);
				player.play(current);
				player = null;
				args[0] = ""+false;
				server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
