package clientinterface.listener;

public interface NotificationListener {
	public void onPauseResumeNotify(boolean isRunning);
	public void onGapListCountChangedNotify(String[] gapLists);
	public void onGapListChangedNotify(String gapListName);
	public void onGapListUpdatedNotify(String[] title);
	public void onWishListUpdatedNotify(String[] title);
	public void onNextTrackNotify(String Title,String videoURL);
	public void onDisconnect();
}
