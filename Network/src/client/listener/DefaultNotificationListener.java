package client.listener;

import client.serverconnection.Song;

public interface DefaultNotificationListener {
	public void onWishListUpdatedNotify(Song[] songs);
	public void onNextTrackNotify(String title,String url,boolean isVideo);
	public void onDisconnect();
}
