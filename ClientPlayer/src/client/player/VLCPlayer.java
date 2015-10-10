package client.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Semaphore;

import utilities.IO;
import client.PlayerStarter;

public class VLCPlayer implements Runnable, Player {
	
	public class VLCNotFoundException extends RuntimeException{

		/**
		 * 
		 */
		private static final long serialVersionUID = -3163448637440793502L;
		
		@Override
		public String getMessage() {
			return "VLC could not be found on the system!";
		}
		
	}
	
	private class ReaderThread implements Runnable{

		private BufferedReader in;

		public ReaderThread(BufferedReader in) {
			this.in = in;
		}
		
		@Override
		public void run() {
			try {
				out.write("info\n");
				out.flush();
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
								trackFinished.release();
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
	private Semaphore trackFinished = new Semaphore(0);
	
	public VLCPlayer(PlayerStarter parent) {
		this.parent = parent;
		try {
			playThread = new Thread(this);
			playerProcess =  new ProcessBuilder(programURI,"--extraintf=\"rc\"","--control=\"qt\"","--rc-host=\"localhost:8080\"").start();
			if (playerProcess != null){
				InetAddress localhost = InetAddress.getByName("localhost");
				boolean success = false;
				while (!success){
					try{
						socket = new Socket(localhost, 8080);
					    out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
					    success  = true;
					    reader = new Thread(new ReaderThread(in));
					    reader.start();
					}catch (ConnectException e){
						IO.printlnDebug(this, "Connection to VLC could not be established! Trying again...");
						Thread.sleep(200);
					}
				}

			}	
			else{
				IO.printlnDebug(this, "VLC could not be started!");
				throw new VLCNotFoundException();
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void play(String track) {
		try {
			playThread.join();
			wasSkipped= false;
			out.write("add "+track+"\n");
			out.flush();
			isPlaying = true;
			Thread.sleep(100);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playThread = new Thread(this);
		lastSkipAction = System.currentTimeMillis();
		playThread.start();
		this.resume();

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
			wasSkipped = true;
			long currentTime = System.currentTimeMillis();
			if (currentTime - lastSkipAction < SKIPWAITDURATION)
				Thread.sleep(SKIPWAITDURATION - (currentTime - lastSkipAction));
			lastSkipAction = System.currentTimeMillis();
			out.write("stop\n");
			out.flush();
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

	private boolean pauseResume() {
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
		if (playerProcess != null){
			try {
				trackFinished.acquire();				
				parent.trackIsFinished(this.wasSkipped);
				IO.printlnDebug(this, "finished playback!");
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			} 
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

}
