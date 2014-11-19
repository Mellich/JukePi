package clientinterface;

import clientinterface.listener.NotifyListener;
import clientinterface.listener.ResponseListener;

public interface ServerConnection {
	public void addNotifyListener(NotifyListener listener);
	public void removeNotifyListener(NotifyListener listener);
	public boolean connect();
	public boolean close();
	public void sendMessage(ResponseListener listener,int messageType);
	public void sendMessage(ResponseListener listener,int messageType,String messageArgument);
}
