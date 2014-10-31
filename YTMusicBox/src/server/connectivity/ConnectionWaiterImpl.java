package server.connectivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

import server.IO;
import server.MusicTrack;
import server.player.YTTrackScheduler;

/**listens on a port through a given server socket and accepts incoming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionWaiterImpl extends Thread {
	
	private ServerSocket server;
	private LinkedList<MusicTrack> urlList;
	private LinkedList<MusicTrack> gapList;
	private YTTrackScheduler scheduler;
	private boolean running;
	
	public ConnectionWaiterImpl(ServerSocket s, LinkedList<MusicTrack> u,LinkedList<MusicTrack> gapList,YTTrackScheduler scheduler) {
		server = s;
		urlList = u;
		running = true;
		this.scheduler = scheduler;
		this.gapList = gapList;
	}
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Waiter waits for new music wishes...");
		try {
			while (running){
				Socket s = server.accept();
				ConnectionHandlerImpl handler = new ConnectionHandlerImpl(s,urlList,gapList,scheduler);
				IO.printlnDebug(this, "New Connection established");
				handler.start();
			}
		} catch (IOException e) {
		}
		IO.printlnDebug(this, "Waiter was shut down");
	}
	
	public synchronized void setRunning(boolean r){
		running = r;
	}

}
