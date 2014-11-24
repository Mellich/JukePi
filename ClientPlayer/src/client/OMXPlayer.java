package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import clientwrapper.ClientWrapper;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer {
	
	private Process playerProcess;
	private boolean playing = false;
	private boolean finished = false;
	private boolean wasSkipped = false;
	private BufferedWriter out;
	private ClientWrapper server;

	public void play(String track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track);
		if (playerProcess != null){
			try {
				out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
				playing = true;
				playerProcess.waitFor();
				finished = true;
				if (!wasSkipped)
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
	
	public synchronized boolean isFinished(){
		return finished;
	}
	
	public OMXPlayer(ClientWrapper server) {
		this.server = server;
	}

	public boolean skip() {
		try {
			out.write("q");
			out.flush();
			IO.printlnDebug(this, "Skipped track successfully");
			wasSkipped = true;
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not skip track successfully");
		}
		return false;
	}

	public boolean pauseResume() {
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

}
