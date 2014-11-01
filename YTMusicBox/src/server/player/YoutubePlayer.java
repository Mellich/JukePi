package server.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import server.IO;
import server.MusicTrack;
import server.ProcessCommunicator;

public class YoutubePlayer implements MusicPlayer {
	
	private Process playerProcess;

	@Override
	public void play(MusicTrack track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcessk(track.getURL());
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
		} catch (IOException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
		}

	}

}
