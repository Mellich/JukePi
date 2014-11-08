package server.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import server.MusicTrack;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer implements MusicPlayer {
	
	private Process playerProcess;
	private boolean playing = true;

	@Override
	public void play(MusicTrack track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track.getVideoURL());
		if (playerProcess != null){
			try {
				playerProcess.waitFor();
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			}
		}
		else{
			IO.printlnDebug(this, "Error during music playback");
		}
	}

	@Override
	public void skip() {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
		try {
			out.write("q");
			out.flush();
			IO.printlnDebug(this, "Skipped track successfully");
		} catch (IOException e) {
			IO.printlnDebug(this, "could not skip track successfully");
		}
		
	}

	@Override
	public void pauseResume() {
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
		try {
			out.write(' ');
			out.flush();
			playing = !playing;
		} catch (IOException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
		}

	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

}
