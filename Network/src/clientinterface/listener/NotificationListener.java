package clientinterface.listener;

public interface NotificationListener {
	public void onPauseResumeNotify();
	public void onGapListCountChangedNotify();
	public void onListUpdatedNotify();
	public void onNextTrackNotify();
}
