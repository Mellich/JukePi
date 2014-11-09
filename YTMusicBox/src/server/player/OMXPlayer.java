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
	public void skip() {
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
		try {
			out.write(' ');
			out.flush();
			playing = !playing;
			if (showLogoOnPause){
				server.showLogo(!playing);
			}
		} catch (IOException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
		}

	}

	@Override
	public boolean isPlaying() {
		return playing;
	}

}
