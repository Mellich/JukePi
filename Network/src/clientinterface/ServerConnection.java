package clientinterface;

import clientinterface.listener.NotifyListener;
import clientinterface.listener.ResponseListener;

public interface ServerConnection {
	public void addNotifyListener(NotifyListener listener);
	public void addResponseListener(ResponseListener listener);
	public boolean removeNotifyListener(NotifyListener listener);
	public boolean removeResponseListener(ResponseListener listener);
	public boolean connect();
	public void close();
	public void sendMessage(int messageType);
	public void sendMessage(int messageType,String messageArgument);
}
