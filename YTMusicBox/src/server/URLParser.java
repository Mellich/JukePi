package server;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import server.player.TrackScheduler;
import utilities.IO;
import utilities.ProcessCommunicator;

public class URLParser extends Thread {
	
	private Semaphore newNotParsedURL = new Semaphore(0);
	private boolean notParsedURLExists = true;
	private YTJBServer server;
	private volatile boolean existsParsedURL = false;
	private volatile boolean restartRequired = false;
	private TrackScheduler scheduler;
	
	public URLParser(YTJBServer server,TrackScheduler scheduler) {
		this.server = server;
		this.scheduler = scheduler;
	}
	
	public void notifyNewURL(boolean existsParsedURL){
		notParsedURLExists = true;
		this.existsParsedURL = existsParsedURL;
		if (restartRequired){
			newNotParsedURL.release();
		}
	}
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Started extra parse thread");
		try {
			while(true){
				while(notParsedURLExists){
					IO.printlnDebug(this, "check for not parsed URLs");
					MusicTrack m = server.getNextNotParsedURL();
					if (m != null){
						try {
							m.setVideoURL("PARSING");
							m.setVideoURL(ProcessCommunicator.parseShortURLToVideoURL(m.getShortURL(), server.getWorkingDir()));
							IO.printlnDebug(this, "Track parsed: "+m.getTitle());
							server.notifyListUpdate(m);
							if (!existsParsedURL){
								scheduler.notifyPlayableTrack();
							}
						} catch (IOException e) {
							IO.printlnDebug(this, "Error while parsing URL: "+m.getShortURL());
							e.printStackTrace();
						}
					}else{
						notParsedURLExists = false;
						IO.printlnDebug(this, "No not parsed URL found!");
					}
				}
				this.restartRequired = true;
				newNotParsedURL.acquire();
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Closed because of interrupt");
		}
	}
}
