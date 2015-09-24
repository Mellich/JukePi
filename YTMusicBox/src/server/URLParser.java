package server;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import server.player.TrackScheduler;
import utilities.IO;
import utilities.ProcessCommunicator;

public class URLParser extends Thread {
	
	private static Semaphore nextURLMutex = new Semaphore(1);
	private Semaphore newNotParsedURL = new Semaphore(0);
	private boolean notParsedURLExists = true;
	private YTJBServer server;
	private volatile boolean restartRequired = false;
	private TrackScheduler scheduler;
	private int number;
	
	public URLParser(YTJBServer server,TrackScheduler scheduler, int i) {
		this.server = server;
		this.scheduler = scheduler;
		this.number = i;
	}
	
	public void notifyNewURL(){
		notParsedURLExists = true;
		if (restartRequired){
			newNotParsedURL.release();
		}
	}
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Parse thread "+ number+" started!");
		try {
			while(true){
				while(notParsedURLExists){
					nextURLMutex.acquire();
					IO.printlnDebug(this, number+": check for not parsed URLs");
					MusicTrack m = server.getNextNotParsedURL();
					if (m != null){
						try {
							m.setVideoURL("PARSING");
							nextURLMutex.release();
							String out = ProcessCommunicator.parseShortURLToVideoURL(m.getShortURL(), server.getWorkingDir());
							if (out != null){
								IO.printlnDebug(this, number+": Track parsed: "+m.getTitle());
								m.setVideoURL(out);
							}else{
								IO.printlnDebug(this, number+": Track couldn't be parsed: "+m.getTitle());
								m.setVideoURL("ERROR");								
							}
							server.notifyListUpdate(m);
							if (out != null)
								scheduler.notifyPlayableTrack();
						} catch (IOException e) {
							IO.printlnDebug(this, number+": Error while parsing URL: "+m.getShortURL());
							e.printStackTrace();
						}
					}else{
						nextURLMutex.release();
						notParsedURLExists = false;
						IO.printlnDebug(this, number+": No not parsed URL found!");
					}
				}
				this.restartRequired = true;
				newNotParsedURL.acquire();
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, number+": Closed because of interrupt");
		}
	}
}
