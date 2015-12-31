package clientplayer.player;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import utilities.IO;
import clientplayer.PlayerStarter;

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
				while(true){
						String s = in.readLine();
						if (s != null){
							playerLock.lock();
							IO.printlnDebug(this, s);
							String[] input = s.split(" ");
							if (input[input.length - 1].equals("Pause")){
								isPlaying = false;
							}else if (input[input.length - 1].equals("Play")){
								isPlaying = true;
							}else if (input[input.length - 1].equals("End")){
								isPlaying = false;
								if (!isFinished){
									IO.printlnDebug(this, "Notify player finished!");
									playerFinished.signalAll();
								}
							}else if (input.length == 1){
								try{
									currentTime = Integer.parseInt(input[0]);
									timeReceived.signalAll();
								} catch (NumberFormatException e){
									IO.printlnDebug(this, "Could not parse Integer: "+input[0]);;
								}
							}
							playerLock.unlock();
						}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
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
	private volatile boolean isFinished = false;
	private volatile int currentTime = 0;;
	private Lock playerLock = new ReentrantLock();
	private Condition playerFinished = playerLock.newCondition();
	private Condition timeReceived = playerLock.newCondition();
	
	public VLCPlayer(PlayerStarter parent, List<String> cmd, int port) {
		this.parent = parent;
		try {
			playThread = new Thread(this);
			ProcessBuilder playerBuilder =  new ProcessBuilder(cmd);
			List<String> commands = playerBuilder.command();
			for (String s : commands){
				IO.printlnDebug(this, s);
			}
			playerProcess = playerBuilder.start();
			if (playerProcess != null){
				InetAddress localhost = InetAddress.getByName("localhost");
				boolean success = false;
				while (!success){
					try{
						if (port != 0){
							socket = new Socket(localhost, port);
					    	out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
					    	in = new BufferedReader(new InputStreamReader(socket.getInputStream()));		
						}
						else{
					    	out = new BufferedWriter(new OutputStreamWriter(playerProcess.getOutputStream()));
					    	in = new BufferedReader(new InputStreamReader(playerProcess.getInputStream()));		

						}
					    success  = true;
					    reader = new Thread(new ReaderThread(in));
					    reader.start();
					}catch (ConnectException e){
						IO.printlnDebug(this, "Connection to VLC could not be established! Trying again...");
						Thread.sleep(200);
						//e.printStackTrace();
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
			wasSkipped= false;
			out.write("add "+track+"\n");
			out.flush();
			Thread.sleep(200);
			out.write("info\n");
			out.flush();
			Thread.sleep(200);
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		playThread = new Thread(this);
		lastSkipAction = System.currentTimeMillis();
		playThread.start();

	}
	
	private boolean updateCurrentTime(){
		try {
			playerLock.lock();
			out.write("get_time\n");
			out.flush();
			timeReceived.await();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			playerLock.unlock();
		}
		return false;
	}

	@Override
	public boolean skip() {
		if (!wasSkipped){
			try {
				playerLock.lock();
				wasSkipped = true;
				long currentTime = System.currentTimeMillis();
				if (currentTime - lastSkipAction < SKIPWAITDURATION)
					Thread.sleep(SKIPWAITDURATION - (currentTime - lastSkipAction));
				lastSkipAction = System.currentTimeMillis();
				out.write("stop\n");
				out.flush();
				playerFinished.signalAll();
				playerLock.unlock();
				Thread.sleep(200);
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
		return true;
	}

	private boolean pauseResume() {
		try {
			out.write("pause\n");
			out.flush();
			Thread.sleep(200);
			return true;
		} catch (IOException | NullPointerException | InterruptedException e) {
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
				playerLock.lock();
				isFinished = false;
				playerFinished.await();	
				//isFinished = true;
				parent.trackIsFinished(this.wasSkipped);
				IO.printlnDebug(this, "finished playback!");
			} catch (InterruptedException e) {
				IO.printlnDebug(this, "playback was cancelled forcefully");
			} 
			finally{
				playerLock.unlock();
			}
		}
		else{
			IO.printlnDebug(this, "Error during music playback");
		}
		
	}

	@Override
	public boolean pause() {
		if(isPlaying()){
			return pauseResume();
		}
		return true;
	}

	@Override
	public boolean resume() {
		if (!isPlaying()){
			return pauseResume();
		}
		return true;
	}

	@Override
	public void destroy() {
		try {
			out.write("close\n");
			out.flush();
			playerProcess.waitFor();
		} catch (IOException | InterruptedException e) {
			IO.printlnDebug(this, "Couldnot destroy player process: not reachable!");
		}
	}

}
