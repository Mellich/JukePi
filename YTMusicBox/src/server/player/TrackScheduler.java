package server.player;

import java.util.LinkedList;

import server.IO;
import server.MusicTrack;


public class TrackScheduler extends Thread {
	
	private LinkedList<MusicTrack> wishList;
	private LinkedList<MusicTrack> gapList;
	private boolean running;
	private MusicPlayer player;
	private MusicTrack current;
	
	public TrackScheduler(LinkedList<MusicTrack> wishList, LinkedList<MusicTrack> gapList) {
		this.wishList = wishList;
		this.gapList = gapList;
		running = true;
	}

	
	public synchronized void setRunning(boolean r){
		running = r;
	}
	
	private MusicTrack chooseNext(){
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
				switch (current.getMusicType()){
				case SENDED: player = new SendedFilePlayer();
					break;
				default: player = new YoutubePlayer();
				}
				player.play(current);
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
