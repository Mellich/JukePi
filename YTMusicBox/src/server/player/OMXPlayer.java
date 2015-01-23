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
	private BufferedWriter out;

	@Override
	public void play(MusicTrack track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track.getVideoURL());
		if (playerProcess != null){
			try {
				out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
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
	public boolean skip() {
		try {
			out.write("q");
			out.flush();
			IO.printlnDebug(this, "Skipped track successfully");
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not skip track successfully");
		}
		return false;
	}

	@Override
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

	@Override
	public boolean isPlaying() {
		return playing;
	}

	@Override
	public boolean seekForward() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean seekBackward() {
		// TODO Auto-generated method stub
		return false;
	}

}
