package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import clientwrapper.ClientWrapper;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer implements Runnable{
	
	private Process playerProcess;
	private boolean playing = false;
	private boolean wasSkipped = false;
	private BufferedWriter out;
	private ClientWrapper server;
	private Thread playThread;

	public void play(String track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track);
		if (playerProcess != null)
			out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
		playThread = new Thread(this);
		playThread.start();
	}
	
	public OMXPlayer(ClientWrapper server) {
		this.server = server;
	}
	
	private synchronized void setSkipped(boolean b){
		wasSkipped = true;
	}
	
	private synchronized boolean isSkipped(){
		return wasSkipped;
	}

	public boolean skip() {
		try {
			setSkipped(true);
			out.write("q");
			out.flush();
			playerProcess.destroy();
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

	@Override
	public void run() {
		if (playerProcess != null){
			try {
				playing = true;
				playerProcess.waitFor();
				if (!isSkipped())
					server.notifyPlayerFinished((String[] s) -> {});
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
