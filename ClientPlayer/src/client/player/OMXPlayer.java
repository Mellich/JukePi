package client.player;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import client.PlayerStarter;
import utilities.IO;
import utilities.ProcessCommunicator;

public class OMXPlayer implements Runnable, Player{
	
	private final static long SKIPWAITDURATION = 1000;
	
	private volatile Process playerProcess;
	private volatile boolean isPlaying = false;
	private volatile boolean wasSkipped = false;
	private BufferedWriter out;
	private PlayerStarter parent;
	private volatile Thread playThread;
	private volatile long lastSkipAction = 0;

	/* (non-Javadoc)
	 * @see client.Player#play(java.lang.String)
	 */
	@Override
	public void play(String track) {
		playerProcess = ProcessCommunicator.getExternPlayerProcess(track);
		isPlaying = true;
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

	/* (non-Javadoc)
	 * @see client.Player#skip()
	 */
	@Override
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

	/* (non-Javadoc)
	 * @see client.Player#pauseResume()
	 */
	private boolean pauseResume() {
		try {
			out.write(' ');
			out.flush();
			isPlaying = !isPlaying;
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see client.Player#isPlaying()
	 */
	@Override
	public boolean isPlaying() {
		return isPlaying;
	}
	
	/* (non-Javadoc)
	 * @see client.Player#seekForward()
	 */
	@Override
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
	
	/* (non-Javadoc)
	 * @see client.Player#seekBackward()
	 */
	@Override
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
				isPlaying = true;
				playerProcess.waitFor();
				parent.trackIsFinished(this.isSkipped());
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			}
			isPlaying = false;
		}
		else{
			IO.printlnDebug(this, "Error during music playback");
		}
	}

	@Override
	public boolean pause() {
		if(isPlaying){
			return pauseResume();
		}
		return true;
	}

	@Override
	public boolean resume() {
		if (!isPlaying){
			return pauseResume();
		}
		return true;
	}

	@Override
	public void destroy() {
		try {
			out.write('q');
			out.flush();
			playThread.join();
		} catch (IOException | InterruptedException e) {
			IO.printlnDebug(this, "Couldnot destroy player process: not reachable!");
		}
	}

}
