package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.YTJBServer;
import server.connectivity.handler.NotifyClientCommand;
import server.connectivity.handler.UnknownCommand;
import utilities.IO;

public class Connection extends Thread {
	
	private Socket socket;
	private YTJBServer server;
	private BufferedWriter out;
	private BufferedReader in;
	private boolean running = true;
	
	public Connection(Socket s,YTJBServer server) {
		this.socket = s;
		this.server = server;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (running){
				String message = receiveMessage();
				IO.printlnDebug(this, "Received message: "+message);
				try{
					CommandHandler newCommand = new CommandHandler(out,server,this,message);
					newCommand.start();
				}
				catch (NumberFormatException | IndexOutOfBoundsException e){
					IO.printlnDebug(this, "Wrong command format was sendet by client:"+message);
					new UnknownCommand(out,""+message).handle();
				}
			}
			socket.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: lost client connection!");
		}
		finally{
			server.removeClient(this);
		}
	}
	
	public void notify(int messageType){
			new NotifyClientCommand(out,messageType).handle();
	}


	private String receiveMessage() throws IOException {
		IO.printlnDebug(this, "Waiting for input...");
		return in.readLine();
	}
}
