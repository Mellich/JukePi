package server.player;

import java.util.LinkedList;

import server.IO;
import server.MessageType;
import server.MusicTrack;

//TODO: dieser player muss eigentlich der track-auswähler sein. darunter neue 
// klasse, die bindungsklasse "player" zischen dieser und dem processcomm. ist.
public class YTTrackScheduler extends Thread {
	
	private LinkedList<MusicTrack> wishList;
	private LinkedList<MusicTrack> gapList;
	private boolean running;
	private MusicPlayer player;
	
	public YTTrackScheduler(LinkedList<MusicTrack> wishList, LinkedList<MusicTrack> gapList) {
		this.wishList = wishList;
		this.gapList = gapList;
		running = true;
	}
	
	public MusicPlayer getMusicPlayer(){
		return player;
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
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Player is ready to play your music...");
		try {
			while (running){
				IO.printlnDebug(this, "getting next track in the list");
				player = null;
				MusicTrack next = chooseNext();
				while (next == null){
					//Output.printlnDebug(this, "all lists are empty. waiting for new tracks to come...");
					Thread.sleep(1000);
					next = chooseNext();
				}
				IO.printlnDebug(this,"Playing next track: "+next.getTitle());
				switch (next.getMusicType()){
				case MessageType.SENDEDFILE: player = new SendedFilePlayer();
					break;
				default: player = new YoutubePlayer();
				}
				player.play(next);
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Player was closed");
		}
	}
}
