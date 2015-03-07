package client.listener;

import client.serverconnection.Song;

public interface GapListNotificationListener {
	public void onGapListCountChangedNotify(String[] gapLists);
	public void onGapListChangedNotify(String gapListName);
	public void onGapListUpdatedNotify(Song[] songs);
}
