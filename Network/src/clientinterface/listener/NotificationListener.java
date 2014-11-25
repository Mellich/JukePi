package clientinterface.listener;

public interface NotificationListener {
	public void onPauseResumeNotify();
	public void onGapListCountChangedNotify();
	public void onGapListChangedNotify();
	public void onGapListUpdatedNotify();
	public void onWishListUpdatedNotify();
	public void onNextTrackNotify();
	public void onDisconnect();
}
