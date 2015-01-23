package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer implements Runnable{
	
	private final static long SKIPWAITDURATION = 1000;
	
	private volatile Process playerProcess;
	private volatile boolean playing = false;
	private volatile boolean wasSkipped = false;
	private BufferedWriter out;
	private PlayerStarter parent;
	private volatile Thread playThread;
	private volatile long lastSkipAction = 0;

	public void play(String track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track);
		if (playerProcess != null)
			out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
		playThread = new Thread(this);
		lastSkipAction = System.currentTimeMillis();
		playThread.start();
	}
	
	public OMXPlayer(PlayerStarter parent) {
		this.parent = parent;
	}
	
	private synchronized void setSkipped(){
		wasSkipped = true;
	}
	
	private synchronized boolean isSkipped(){
		return wasSkipped;
	}

	public boolean skip() {
		try {
			setSkipped();
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSkipAction < SKIPWAITDURATION)
				Thread.sleep(SKIPWAITDURATION - (currentTime - lastSkipAction));
			lastSkipAction = System.currentTimeMillis();
			out.write("q");
			out.flush();
			playThread.join();
			IO.printlnDebug(this, "Skipped track successfully");
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not skip track successfully");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public synchronized boolean pauseResume() {
		try {
			out.write(' ');
			out.flush();
			playing = !playing;
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
		}
		return false;
	}

	public boolean isPlaying() {
		return playing;
	}
	
	public synchronized boolean seekForward(){
		try {
			out.write("^[[C");
			out.flush();
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not seek playback");
		}
		return false;		
	}
	
	public synchronized boolean seekBackward(){
		try {
			out.write("^[[D");
			out.flush();
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not seek playback");
		}
		return false;		
	}

	@Override
	public void run() {
		if (playerProcess != null){
			try {
				playing = true;
				playerProcess.waitFor();
				parent.trackIsFinished(this.isSkipped());
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			}
			playing = false;
		}
		else{
			IO.printlnDebug(this, "Error during music playback");
		}
	}

}
