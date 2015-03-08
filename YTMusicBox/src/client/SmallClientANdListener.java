package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;

public class SmallClientANdListener implements DefaultNotificationListener {
	
	private Song[] wishList;
	
	public SmallClientANdListener() {
		ServerConnection server = new YTJBServerConnection(15000);
		if (server.connect("localhost",22222)){
			server.addDefaultNotificationListener(this);
			System.out.println("Current Gaplist: "+server.getCurrentGapListName());
			System.out.println("Current Playback status: "+server.getCurrentPlaybackStatus());
			System.out.println("Current track title: "+server.getCurrentTrackTitle());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			wishList = server.getWishList();
			try {
				String input = "";
				do{
					System.out.println("New WishList:");
					System.out.println("Track-ID Name \t \t \t \t Votes \t Your vote");
					for (Song s : wishList){
						char[] name = s.getName().subSequence(0, 30).toString().toCharArray();
						System.out.print(s.getTrackID()+"\t ");
						System.out.print(name);
						System.out.println("\t "+s.getVotes()+"\t "+s.isOwnVote());
					}
					System.out.println();
					System.out.print("Please enter a track id to vote:");
					input = reader.readLine();
					try{
						long trackID = Long.parseLong(input);
						server.removeVote();
						Song vote = null;
						for (Song s: wishList){
							if (s.getTrackID() == trackID){
								vote = s;
								break;
							}
						}
						server.voteSong(vote);
					}catch (NumberFormatException e){
						if (input.equals("update")){
							System.out.println("Update view!");
						}else System.out.println("Input not a track ID!");
					}
				} while(input != "");
				server.close();
			} catch (IOException | NullPointerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}else{
			System.out.println("Fehler beim Verbinden!");
		}
	}
	
	public static void main(String[] args) {
		SmallClientANdListener l = new SmallClientANdListener();
		l.getClass();
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL,boolean isVideo) {

	}

	@Override
	public void onDisconnect() {
		System.out.println();
		System.out.println("Disconnected!");

	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		wishList = songs;
	}

}
