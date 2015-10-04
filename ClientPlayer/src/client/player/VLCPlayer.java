package client.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import utilities.IO;
import client.PlayerStarter;

public class VLCPlayer implements Runnable, Player {
	
	private class ReaderThread implements Runnable{

		private BufferedReader in;

		public ReaderThread(BufferedReader in) {
			this.in = in;
		}
		
		@Override
		public void run() {
			try {
				while(true){
						String s = in.readLine();
						if (s != null){
							IO.printlnDebug(this, s);
							String[] input = s.split(" ");
							if (input[input.length - 1].equals("Pause")){
								isPlaying = false;
							}else if (input[input.length - 1].equals("Play")){
								isPlaying = true;
							}else if (input[input.length - 1].equals("End")){
								isPlaying = false;
								playerProcess.destroy();
							}else if (input.length == 1){
								try{
									currentTime = Integer.parseInt(input[0]);
									currentTimeMutex.release();
								} catch (NumberFormatException e){
									IO.printlnDebug(this, "Could not parse Integer: "+input[0]);;
								}
							}
						}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
	private static final String programURI = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
	private final static long SKIPWAITDURATION = 1000;
	
	private PlayerStarter parent;
	private Process playerProcess = null;
	private Thread reader = null;
	private BufferedWriter out;
	private BufferedReader in;
	private Thread playThread;
	private long lastSkipAction;
	private boolean wasSkipped = false;
	private Socket socket;
	private volatile boolean isPlaying = false;
	private volatile int currentTime = 0;;
	private Semaphore currentTimeMutex = new Semaphore(0);
	
	public VLCPlayer(PlayerStarter parent) {
		this.parent = parent;
	}

	@Override
	public void play(String track) {
		playerProcess = null;
		try {
			playerProcess =  new ProcessBuilder(programURI,"--intf=\"rc\"","--rc-host=\"localhost:8080\"","-f",track).start();
			Thread.sleep(200);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		if (playerProcess != null){
			try {
				InetAddress localhost = InetAddress.getByName("localhost");
				socket = new Socket(localhost, 8080);
			    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		playThread = new Thread(this);
		lastSkipAction = System.currentTimeMillis();
		playThread.start();

	}
	
	private synchronized void setSkipped(){
		wasSkipped  = true;
	}
	
	private synchronized boolean isSkipped(){
		return wasSkipped;
	}
	
	private boolean updateCurrentTime(){
		try {
			out.write("get_time\n");
			out.flush();
			currentTimeMutex.acquire();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean skip() {
		try {
			setSkipped();
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSkipAction < SKIPWAITDURATION)
				Thread.sleep(SKIPWAITDURATION - (currentTime - lastSkipAction));
			lastSkipAction = System.currentTimeMillis();
			out.write("quit\n");
			out.flush();
			reader.interrupt();
			playerProcess.destroy();
			playThread.join();
			IO.printlnDebug(this, "Skipped track successfully");
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not skip track successfully");
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean pauseResume() {
		try {
			out.write("pause\n");
			out.flush();
			return true;
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "could not pause/resume player successfully");
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean isPlaying() {
		return isPlaying;
	}

	@Override
	public boolean seekForward() {
		updateCurrentTime();
		currentTime += 30;
		try {
			out.write("seek "+currentTime+"\n");
			out.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean seekBackward() {
		updateCurrentTime();
		currentTime -= 30;
		try {
			out.write("seek "+currentTime+"\n");
			out.flush();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public void run() {
		reader = new Thread(new ReaderThread(in));
		reader.start();
		if (playerProcess != null){
			try {
				out.write("info\n");
				out.flush();
				playerProcess.waitFor();
				parent.trackIsFinished(this.isSkipped());
				reader.interrupt();
				if (socket != null)
					socket.close();
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else{
			IO.printlnDebug(this, "Error during music playback");
		}
		
	}

}
