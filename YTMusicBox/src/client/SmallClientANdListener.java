package client;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import clientinterface.listener.NotificationListener;
import clientwrapper.ClientWrapper;
import clientwrapper.YTJBClientWrapper;

public class SmallClientANdListener implements NotificationListener {
	
	public SmallClientANdListener() {
		ClientWrapper server = new YTJBClientWrapper(15000);
		if (server.connect("192.168.178.34",22222)){
			server.addNotificationListener(this);
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

}
