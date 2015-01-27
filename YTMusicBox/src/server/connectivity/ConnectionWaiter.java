package server.connectivity;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import server.YTJBServer;
import utilities.IO;

/**listens on a port through a given server socket and accepts incoming connections
 * 
 * @author Mellich
 *
 */
public class ConnectionWaiter extends Thread {
	
	private ServerSocket serverSocket;
	private YTJBServer server;
	private boolean running;
	private ArrayList<Connection> connections = new ArrayList<Connection>();
	
	public ConnectionWaiter(YTJBServer server) {
		this.serverSocket = server.getServerSocket();
		this.server = server;
		running = true;
	}
	
	@Override
	public void run() {
		super.run();
		IO.printlnDebug(this, "Waiter waits for new music wishes...");
		try {
			while (running){
				Socket s = serverSocket.accept();
				Connection handler = new Connection(s,server,this);
				connections.add(handler);
				IO.printlnDebug(this, "New Connection established");
				handler.start();
			}
		} catch (IOException e) {
			IO.printlnDebug(this, "Waiter closed forecefully");
		}
		for (Connection c : connections)
			c.closeConnection();
		IO.printlnDebug(this, "Waiter says goodbye");
	}
	
	public synchronized void setRunning(boolean r){
		running = r;
	}
	
	protected synchronized void removeConnection(Connection c){
		connections.remove(c);
	}

}
