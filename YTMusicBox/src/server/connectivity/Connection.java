package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import messages.MessageType;
import messages.Permission;
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
	private boolean isDebugListener = false;
	private boolean isPauseResumeListener = false;
	private boolean isDefaultListener = false;
	private boolean isSeekListener = false;
	private boolean isGapListListener = false;
	private boolean isPlayer = false;
	private long macAddress = -1L;
	private long version = -1L;
	private List<Permission> permissions;
	
	public Connection(Socket s,YTJBServer server,ConnectionWaiter waiter) {
		this.socket = s;
		this.server = server;
		this.waiter = waiter;
		this.permissions = new ArrayList<Permission>();
	}
	
	@Override
	public void run() {
		super.run();
		try {
			out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));
			in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
			permissions.add(Permission.STANDARD);
			while (running){
				String message = receiveMessage();
				if (message == null || this.isInterrupted())		//if connection is lost, close connection. if not handled here
					break;					//it would cause a NumberFormatException in the CommandHandler and the connection wouldn't close
				try{
					CommandHandler newCommand = new CommandHandler(out,server,this,message);
					newCommand.start();
				}
				catch (NumberFormatException | IndexOutOfBoundsException e){
					IO.printlnDebug(this, "Wrong command format was sent by client:"+message);
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
	
	public boolean checkPermission(Permission p){
		return permissions.contains(p);
	}
	
	public void addPermission(Permission p){
		if (!permissions.contains(p))
			permissions.add(p);
		StringBuilder sb  =new StringBuilder();
		for (Permission temp : permissions){
			sb.append(temp+", ");
		}
		IO.printlnDebug(this, "Connection permissions: "+sb);
	}
	
	public void removePermission(Permission p){
		permissions.remove(p);
	}
	
	public void setIsPlayer(boolean b){
		isPlayer = true;
	}
	
	public boolean isPlayer(){
		return isPlayer;
	}
	
	public void setMACAddress(long mac){
		macAddress = mac;
	}
	
	public void setVersion(long v){
		version = v;
	}
	
	public long getVersion(){
		return version;
	}

	public long getMACAddress(){
		return macAddress;
	}
	
	public void closeConnection(){
		try{
			socket.close();
		}
		catch (IOException e){
			IO.printlnDebug(this, "Could not close connection");
		}
	}
	
	public void setAsDebugListener(){
		isDebugListener = !isDebugListener;
	}
	
	public void setAsPauseResumeListener(){
		isPauseResumeListener = !isPauseResumeListener;
	}
	
	public void setAsDefaultListener(){
		isDefaultListener = !isDefaultListener;
	}
	
	public void setAsSeekListener(){
		isSeekListener = !isSeekListener;
	}
	
	public void setAsGapListListener(){
		isGapListListener = !isGapListListener;
	}
	
	
	
	
	public void notify(int messageType,List<String> args){
		StringBuilder builder = new StringBuilder();
		for (String s: args){
			builder.append(s+MessageType.SEPERATOR);
		}
		if (messageType == MessageType.DEBUGOUTPUTNOTIFY && isDebugListener ||
				messageType == MessageType.PLAYERCOUNTCHANGEDNOTIFY && isDebugListener ||
				messageType == MessageType.CLIENTCOUNTCHANGEDNOTIFY && isDebugListener ||
				messageType == MessageType.NEXTTRACKNOTIFY && isDefaultListener ||
				messageType == MessageType.WISHLISTUPDATEDNOTIFY && isDefaultListener ||
				messageType == MessageType.PAUSERESUMENOTIFY && isPauseResumeListener ||
				messageType == MessageType.GAPLISTCOUNTCHANGEDNOTIFY && isGapListListener ||
				messageType == MessageType.GAPLISTUPDATEDNOTIFY && isGapListListener ||
				messageType == MessageType.SEEKNOTIFY && isSeekListener ||
				messageType == MessageType.GAPLISTCHANGEDNOTIFY && isGapListListener) {
			if (messageType == MessageType.GAPLISTUPDATEDNOTIFY || messageType == MessageType.WISHLISTUPDATEDNOTIFY)
				builder.insert(0, ""+server.getVote(macAddress)+MessageType.SEPERATOR);
			new NotifyClientCommand(out,MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY,messageType,builder.toString()).handle();
		}
	}


	private String receiveMessage() throws IOException {
		return in.readLine();
	}
}
