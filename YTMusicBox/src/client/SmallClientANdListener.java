package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.Song;
import client.serverconnection.impl.YTJBServerConnection;

public class SmallClientANdListener implements DefaultNotificationListener {
	
	public SmallClientANdListener() {
		ServerConnection server = new YTJBServerConnection(15000);
		if (server.connect("localhost",22222)){
			server.addDefaultNotificationListener(this);
			System.out.println("Current Gaplist: "+server.getCurrentGapListName());
			System.out.println("Current Playback status: "+server.getCurrentPlaybackStatus());
			System.out.println("Current track title: "+server.getCurrentTrackTitle());
			Song[] gapList = server.getGapList();
			//server.addToList("https://www.youtube.com/watch?v=ZrK3MjSJ9gM", true, true);
			for (Song s : gapList)
				System.out.println(s.getTrackID()+", "+s.getName()+", "+s.getVotes()+", "+s.isOwnVote());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
				reader.readLine();
				Song[] wishList = server.getWishList();
				server.voteSong(wishList[1]);
				reader.readLine();
				server.removeVote();
				reader.readLine();
				server.close();
			} catch (IOException e1) {
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
		System.out.println("Next Track: "+title);
	}

	@Override
	public void onDisconnect() {
		System.out.println("Disconnected!");

	}

	@Override
	public void onWishListUpdatedNotify(Song[] songs) {
		System.out.println("New WishList:");
		for (Song s : songs)
			System.out.println(s.getTrackID()+", "+s.getName()+", "+s.getVotes()+", "+s.isOwnVote());
		System.out.println();
	}

}
