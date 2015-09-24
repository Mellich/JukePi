package server.player;

import java.util.ArrayList;
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
	
	private volatile boolean playableTrackAquired = false;
	
	public TrackScheduler(YTJBServer server) {
		this.server = server;
		running = true;
	}
	
	public synchronized void notifyPlayableTrack(){
		if (playableTrackAquired){
			playableTrack.release();
			playableTrackAquired = false;
		}
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
			ArrayList<String> args = new ArrayList<String>();
			args.add(""+isPlaying());
			server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
			return result;
		}
		return false;
	}
	
	public boolean seekForward(){
		ArrayList<String> args = new ArrayList<String>();
		args.add(""+true);
		server.notifyClients(MessageType.SEEKNOTIFY, args);
		return player.seekForward();
	}
	
	public boolean seekBackward(){
		ArrayList<String> args = new ArrayList<String>();
		args.add(""+false);
		server.notifyClients(MessageType.SEEKNOTIFY, args);
		return player.seekBackward();
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
				ArrayList<String> args = new ArrayList<String>();
				args.add(""+false); //TODO hier weiter
				while (current == null){
					IO.printlnDebug(this, "waiting for a parsed track...");
					args.set(0, ""+false);
					server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
					playableTrackAquired = true;
					playableTrack.acquire();
					current = server.chooseNextTrack();
				}
				if (server.getPlayerCount() == 0){
					args.set(0, ""+false);
					server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
					IO.printlnDebug(this, "Waiting for available player...");
					playerAvailable.acquire();
				}
				while (!current.isReady()){
					Thread.sleep(100);
				}
				args.set(0,""+true);
				server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
				ArrayList<String> argsNext = new ArrayList<String>();
				argsNext.add(current.getTitle());
				argsNext.add(current.getVideoURL());
				argsNext.add(""+current.getIsVideo());
				server.notifyClients(MessageType.NEXTTRACKNOTIFY,argsNext);
				IO.printlnDebug(this,"Playing next track: "+current.getTitle());
				player = new ClientPlayer(server,this);
				player.play(current);
				player = null;
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
