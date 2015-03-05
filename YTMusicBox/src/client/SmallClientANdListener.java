package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import client.listener.DefaultNotificationListener;
import client.serverconnection.ServerConnection;
import client.serverconnection.impl.YTJBServerConnection;

public class SmallClientANdListener implements DefaultNotificationListener {
	
	public SmallClientANdListener() {
		ServerConnection server = new YTJBServerConnection(15000);
		if (server.connect("localhost",22222)){
			server.addNotificationListener(this);
			System.out.println(server.getCurrentGapListName());
			System.out.println(server.getLoadGapListStatus().getLoadedTrackCount());
			System.out.println(server.getCurrentPlaybackStatus());
			System.out.println(server.getCurrentTrackTitle());
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			try {
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
	public void onPauseResumeNotify(boolean isPlaying) {
		System.out.println("Play Status changed to "+isPlaying);

	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		System.out.println("New GapList:");
		for (String s : title)
			System.out.print(s+", ");
		System.out.println();

	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		System.out.println("New WishList:");
		for (String s : title)
			System.out.print(s+", ");
		System.out.println();

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
	public void onSeekNotify(boolean forward) {
		// TODO Auto-generated method stub
		
	}

}
