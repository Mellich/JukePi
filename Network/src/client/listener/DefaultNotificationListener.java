package client.listener;

public interface DefaultNotificationListener {
	public void onWishListUpdatedNotify(String[] title);
	public void onNextTrackNotify(String title,String url,boolean isVideo);
	public void onDisconnect();
}
