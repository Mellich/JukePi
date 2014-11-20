package clientwrapper;

public interface NotificationListener {
	public void onPlayPauseNotify();
	public void onGapListCountChangedNotify();
	public void onListUpdatedNotify();
}
