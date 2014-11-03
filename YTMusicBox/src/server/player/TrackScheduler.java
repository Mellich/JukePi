package server.player;

import java.io.File;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;


public class TrackScheduler extends Thread {
	
	private LinkedList<MusicTrack> wishList;
	private LinkedList<MusicTrack> gapList;
	private YTJBServer server;
	private boolean running;
	private MusicPlayer player;
	private MusicTrack current;
	
	public TrackScheduler(YTJBServer server) {
		this.wishList = server.getWishList();
		this.gapList = server.getGapList();
		this.server = server;
		running = true;
	}

	
	public synchronized void setRunning(boolean r){
		running = r;
	}
	
	private synchronized MusicTrack chooseNext(){
		if (!wishList.isEmpty()){
			return wishList.removeFirst();
		}
		else {
			if (!gapList.isEmpty()){
				MusicTrack nextURL = gapList.removeFirst();
				gapList.add(nextURL);
				return nextURL;
			}
		}
		return null;
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
				current = chooseNext();
				while (current == null){
					//Output.printlnDebug(this, "all lists are empty. waiting for new tracks to come...");
					Thread.sleep(1000);
					current = chooseNext();
				}
				IO.printlnDebug(this,"Playing next track: "+current.getTitle());
				server.notifyClients(MessageType.NEXTTRACKNOTIFY);
				player = new OMXPlayer();
				player.play(current);
				if (current.getMusicType() == TrackType.SENDED && wishList.contains(current)){
					File musicfile = new File(current.getVideoURL());
					if (musicfile.exists()){
						musicfile.delete();
					}
				}
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
