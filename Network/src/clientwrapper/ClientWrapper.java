package clientwrapper;

import clientinterface.listener.NotificationListener;
import clientinterface.listener.ResponseListener;

public interface ClientWrapper {
	public void addNotificationListener(NotificationListener listener);
	public void removeNotificationListener(NotificationListener listener);
	public void addToList(ResponseListener response,String url,boolean toWishList,boolean toBack);
	public void deleteFromList(ResponseListener response,int index);
	public void getCurrentTrackTitle(ResponseListener response);
	public void getGapList(ResponseListener response);
	public void getWishList(ResponseListener response);
	public void skip(ResponseListener response);
	public void pauseResume(ResponseListener response);
	public void switchToGapList(ResponseListener response,String name);
	public void deleteGapList(ResponseListener response,String name);
	public void getAvailableGapLists(ResponseListener response);
	public void saveGapList(ResponseListener response);
	public void getCurrentGapListName(ResponseListener response);
	public void getTitleFromGapList(ResponseListener response,String name);
	public void getCurrentPlaybackStatus(ResponseListener response);
	public void setGapListTrackUp(ResponseListener response,int index);
	public void setGapListTrackDown(ResponseListener response,int index);
	public void getNextVideoURL(ResponseListener response);
	public void getLoadGapListStatus(ResponseListener response);
	public void notifyPlayerFinished(ResponseListener response);
	public void setMeAsPlayer();
	public boolean connect();
	public boolean isConnected();
	public boolean close();
}
