package server;

import java.io.IOException;

import utilities.ConditionVariable;
import utilities.IO;
import utilities.ProcessCommunicator;

public class URLParser implements Runnable{
	
	private YTJBServer server;
	private Thread[] parseThreads;
	private ConditionVariable lock;
	
	public URLParser(YTJBServer server) {
		this.server = server;
		lock = server.getLock();
		this.parseThreads = new Thread[Runtime.getRuntime().availableProcessors()];
		for (int i = 0 ; i < parseThreads.length;i++){
			parseThreads[i] = new Thread(this);
		}
	}
	
	public void startUp(){
		for (Thread t : parseThreads){
			t.start();
		}
		IO.printlnDebug(this, "Parse threads started! Count: "+parseThreads.length);
	}
	
	
	
	@Override
	public void run() {
		try {
			while(true){
				IO.printlnDebug(this, "Parse thread: check for not parsed URLs");
				lock.lock();
				MusicTrack m = server.getNextNotParsedURL();
				if (m != null){
					try {
						m.setVideoURL("PARSING");
						lock.unlock();
						String[] out = new String[2];
						if (m.getShortURL().equals(m.getTitle())){
							out = ProcessCommunicator.parseShortURLToVideoURLAndTitle(m.getShortURL(), server.getWorkingDir());
						}
						else {
							out[1] = ProcessCommunicator.parseShortURLToVideoURL(m.getShortURL(), server.getWorkingDir());
							out[0] = m.getTitle();
						}
						if (out[1] != null){
							m.setTitle(out[0]);
							IO.printlnDebug(this, "Parse thread: Track parsed: "+m.getTitle());
							m.setVideoURL(out[1]);
						}else{
							IO.printlnDebug(this, "Parse thread: Track couldn't be parsed: "+m.getTitle());
							m.setVideoURL("ERROR");								
						}
						server.notifyListUpdate(m);
						if (out != null){
							lock.lock();
							lock.getPlayableTrackAvailable().signal();
							lock.unlock();
						}
					} catch (IOException e) {
						IO.printlnDebug(this, "Parse thread: Error while parsing URL: "+m.getShortURL());
						e.printStackTrace();
					}
				}else{
					IO.printlnDebug(this, "Parse Thread: No not parsed URL found!");
					lock.getNotParsedTrackAvailable().await();
					lock.unlock();
				}
			}
		} catch (InterruptedException e) {
			IO.printlnDebug(this, "Parse thread: Closed because of interrupt");
		}
	}

	public void shutDown() {
		for (Thread t : parseThreads){
			t.interrupt();
		}
	}
}
