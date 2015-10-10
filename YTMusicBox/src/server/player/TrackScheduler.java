package server.player;

import java.util.ArrayList;

import messages.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import utilities.ConditionVariable;
import utilities.IO;


public class TrackScheduler extends Thread {
	
	private YTJBServer server;
	private boolean running;
	private MusicPlayer player;
	private MusicTrack current;
	private ConditionVariable lock;
	
	public TrackScheduler(YTJBServer server) {
		this.server = server;
		lock = server.getLock();
		running = true;
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
		IO.printlnDebug(this, "Player is ready to play your music...");
		try {
			player = null;
			while (running){
				lock.lock();
				IO.printlnDebug(this, "getting next track in the list");
				current = server.chooseNextTrack();
				ArrayList<String> args = new ArrayList<String>();
				args.add(""+false);
				while (current == null){
					IO.printlnDebug(this, "waiting for a parsed track...");
					args.set(0, ""+false);
					server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
					lock.getPlayableTrackAvailable().await();
					current = server.chooseNextTrack();
				}
				if (server.getPlayerCount() == 0){
					args.set(0, ""+false);
					server.notifyClients(MessageType.PAUSERESUMENOTIFY,args);
					IO.printlnDebug(this, "Waiting for available player...");
					lock.getPlayerAvailable().await();
				}
				lock.unlock();
				while (!current.isReady() && !current.isError()){
					IO.printlnDebug(this, "Warte, dass track fertig geparst wird...");
					Thread.sleep(200);
				}
				if (!current.isError()){
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
				else{
					IO.printlnDebug(this, "Track wird übersprungen: Konnte nicht geprst werden!");
				}
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
