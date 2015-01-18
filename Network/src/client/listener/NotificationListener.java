package client.listener;

public interface NotificationListener {
	public void onPauseResumeNotify(boolean isPlaying);
	public void onGapListCountChangedNotify(String[] gapLists);
	public void onGapListChangedNotify(String gapListName);
	public void onGapListUpdatedNotify(String[] title);
	public void onWishListUpdatedNotify(String[] title);
	public void onNextTrackNotify(String title,String url,boolean isVideo);
	public void onDisconnect();
}
