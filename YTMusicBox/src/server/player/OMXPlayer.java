package server.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import server.MusicTrack;
import server.YTJBServer;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer implements MusicPlayer {
	
	private Process playerProcess;
	private boolean playing = true;
	private BufferedWriter out;
	private YTJBServer server;
	private boolean showLogoOnPause = true;
	
	public OMXPlayer(YTJBServer server) {
		this.server = server;
	}

	@Override
	public void play(MusicTrack track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track.getVideoURL());
		if (playerProcess != null){
			try {
				out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
				server.showLogo(false);
				playerProcess.waitFor();
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			}
			finally{
				server.showLogo(true);
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
			if (showLogoOnPause){
				server.showLogo(!playing);
			}
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

}
