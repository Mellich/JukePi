package client;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javafx.application.Platform;
import client.visuals.IdleViewer;
import clientwrapper.ClientWrapper;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer {
	
	private Process playerProcess;
	private boolean playing = false;
	private boolean wasSkipped = false;
	private boolean finished = false;
	private BufferedWriter out;
	private ClientWrapper server;
	private IdleViewer viewer;

	public void play(String track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track);
		if (playerProcess != null){
			try {
				out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
				playing = true;
				Platform.runLater(() -> viewer.showLogo(false));
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
	
	public OMXPlayer(ClientWrapper server,IdleViewer v) {
		this.server = server;
		this.viewer = v;
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
			if (!finished)
				Platform.runLater(() -> viewer.showLogo(!playing));
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
