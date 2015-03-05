package client.listener;

public interface GapListNotificationListener {
	public void onGapListCountChangedNotify(String[] gapLists);
	public void onGapListChangedNotify(String gapListName);
	public void onGapListUpdatedNotify(String[] title);
}
