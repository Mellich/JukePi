package connection;

import connection.Collector;
import clientinterface.listener.NotificationListener;

public class NotifyListener implements NotificationListener{

	private Collector c;
	
	public NotifyListener(Collector c) {
		this.c = c;
	}

	@Override
	public void onDisconnect() {
		c.disconnect();
	}

	@Override
	public void onPauseResumeNotify(boolean isRunning) {
		c.setStatus(isRunning);
	}

	@Override
	public void onGapListCountChangedNotify(String[] gapLists) {
		c.setGaplists(gapLists);
		c.fillGaplistModel();
	}

	@Override
	public void onGapListChangedNotify(String gapListName) {
		c.setGaplistName(gapListName);
		c.fillModels();
	}

	@Override
	public void onGapListUpdatedNotify(String[] title) {
		c.setGaplist(title);
		c.fillModels();
	}

	@Override
	public void onWishListUpdatedNotify(String[] title) {
		c.setWishlist(title);
		c.fillModels();
	}

	@Override
	public void onNextTrackNotify(String title, String videoURL) {
		c.setTrack(title);
		c.fillModels();
	}

}
