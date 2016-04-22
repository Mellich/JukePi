package clientside;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.ServerAddress;
import client.listener.DefaultNotificationListener;
import client.listener.GapListNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.exceptions.PermissionDeniedException;
import client.serverconnection.exceptions.UDPTimeoutException;
import client.serverconnection.impl.YTJBServerConnection;
import messages.Permission;

public class JukePiCmd implements DefaultNotificationListener,
GapListNotificationListener {
	
	static final String PAUSE_CMD = "pause";
	static final String SKIP_CMD = "skip";
	static final String SCAN_CMD = "scan";
	static final String ADD_CMD = "add";
	static final String CONNECT_CMD = "connect";
	static final String ADMIN_CMD = "admin";
	static final String HELP_CMD = "help";
	
	static String[] gaplists = new String[0];
	static String currentGaplist = "UNKNOWN";
	static Song[] gaplist = new Song[0];
	static Song[] wishlist = new Song[0];
	static String currentSong = "NOTHING";
	static JukePiCmd cmdTool = new JukePiCmd();
	static ServerConnection server;
	
	static void help(){
		System.out.println("Available commands:");
		System.out.println("------------------------");
		System.out.println("scan		-	makes a udp scan in the network");
		System.out.println("admin [PW]	-	tries to get admin rights using PW as passwort");
		System.out.println("pause		-	pause/resume playback");
		System.out.println("skip		-	skip current track");
		System.out.println("help		-	show this help");
		System.out.println("close		- 	close this program");	
	}
	
	static void pause(){
		System.out.println(server.pauseResume());
	}
	
	static void skip(){
		System.out.println(server.skip());
	}
	
	static void admin(String pw){
		server.addPermission(Permission.ADMIN, pw);
		server.addGapListNotificationListener(cmdTool);	
		System.out.println("Admin rights granted!");
	}
	
	static void add(String list,String back, String url){
		System.out.println(server.addToList(url, (list.equals("wl") ? true : false), (back.equals("b") ? true : false)));
	}
	
	public static void scan(BufferedReader reader) throws PermissionDeniedException, UDPTimeoutException{
		System.out.println("Scanning network...");
		ServerAddress[] address = server.udpScanning();
		ServerAddress selected = null;
		if (address.length > 1){
			System.out.println("Available Server:");
			System.out.println("ID	ADDRESS:PORT");
			for (int i = 0; i < address.length; i++){
				System.out.println(i+".	"+address[i].getIPAddress()+":"+address[i].getPort());
			}
			boolean idselected = false;
			while(!idselected){
				try{
				System.out.println("Choose Server by ID >>");
				int input = Integer.parseInt(reader.readLine());
				if (input >= 0 && input < address.length)
					idselected = true;
				selected = address[input];
				}
				catch (Exception e){System.out.println("Input not a valid ID!");}
			}
		}else{
			selected = address[0];
		}
		server.connect(selected);
		System.out.println("Connection established to "+selected.getIPAddress()+" on port "+selected.getPort());
	}
	
	public static void handleCommand(String cmd, BufferedReader reader){
		String[] args = cmd.split(" ");
		try{
			switch (args[0]){
			case SCAN_CMD: scan(reader); break;
			case ADMIN_CMD: admin(args[1]); break;
			case PAUSE_CMD: pause(); break;
			case SKIP_CMD: skip(); break;
			case HELP_CMD: help(); break;
			case ADD_CMD: add(args[1], args.length == 4 ? args[2] : "", args.length == 4 ? args[3] : args[2]); break;
			default: System.out.println("Command unknown: "+args[0]); help();
			}
		}
		catch (Exception e){
			System.out.println("ERROR: "+e);
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		server = new YTJBServerConnection(15000);
		server.addDefaultNotificationListener(cmdTool);
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		while (true){
			try {
				System.out.print(">> ");
				String input = reader.readLine();
				if (input.equals("close")){
					break;
				}else handleCommand(input, reader);
			} catch (IOException e) {
				break;
			}
		}
		server.close();
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("New gap list count: "+gapLists.length);
		gaplists = gapLists;
		System.out.println("[END-NOTIFY]------------------------------------");		
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("New gap list : "+gapListName);
		currentGaplist = gapListName;
		System.out.println("[END-NOTIFY]------------------------------------");			
	}

	@Override
	public void onGapListUpdatedNotify(Song[] songs) {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("Gap list updated. New count: "+songs.length);
		this.gaplist = songs;
		System.out.println("[END-NOTIFY]------------------------------------");			
	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("Wish list updated. New count: "+songs.length);
		this.wishlist = songs;
		System.out.println("[END-NOTIFY]------------------------------------");	
		
	}

	@Override
	public void onNextTrackNotify(String title, String url, boolean isVideo) {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("New song: "+title);
		this.currentSong = title;
		System.out.println("[END-NOTIFY]------------------------------------");	
		
	}

	@Override
	public void onDisconnect() {
		System.out.println("[START-NOTIFY]------------------------------------");
		System.out.println("Disconnected from server!");
		System.out.println("[END-NOTIFY]------------------------------------");	
	}

}
