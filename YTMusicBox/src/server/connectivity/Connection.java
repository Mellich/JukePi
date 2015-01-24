package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import messages.MessageType;
import server.YTJBServer;
import server.connectivity.commands.NotifyClientCommand;
import server.connectivity.commands.UnknownCommand;
import utilities.IO;

public class Connection extends Thread {
	
	private Socket socket;
	private YTJBServer server;
	private BufferedWriter out;
	private BufferedReader in;
	private boolean running = true;
	private ConnectionWaiter waiter;
	
	public Connection(Socket s,YTJBServer server,ConnectionWaiter waiter) {
		this.socket = s;
		this.server = server;
		this.waiter = waiter;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (running){
				String message = receiveMessage();
				if (message == null || this.isInterrupted())		//if connection is lost, close connection. if not handled here
					break;					//it would cause a NumberFormatException in the CommandHandler and the connection wouldn't close
				IO.printlnDebug(this, "Received message: "+message);
				try{
					CommandHandler newCommand = new CommandHandler(out,server,this,message);
					newCommand.start();
				}
				catch (NumberFormatException | IndexOutOfBoundsException e){
					IO.printlnDebug(this, "Wrong command format was sendet by client:"+message);
					new UnknownCommand(out,MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY,""+message).handle();
				}
			}
			socket.close();
		} catch (IOException | NullPointerException e) {
			IO.printlnDebug(this, "ERROR: lost client connection!");
		}
		finally{
			waiter.removeConnection(this);
			server.removeNotifiable(this);
			server.removePlayer(this);
		}
	}
	
	public void closeConnection(){
		try{
			socket.close();
		}
		catch (IOException e){
			IO.printlnDebug(this, "Could not close connection");
		}
	}
	
	public void notify(int messageType,String[] args){
			new NotifyClientCommand(out,MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY,messageType,args).handle();
	}


	private String receiveMessage() throws IOException {
		IO.printlnDebug(this, "Waiting for input...");
		return in.readLine();
	}
}
