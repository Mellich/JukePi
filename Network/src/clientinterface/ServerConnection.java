package clientinterface;

import clientinterface.listener.ResponseListener;

public interface ServerConnection {
	public boolean connect();
	public boolean close();
	public void sendMessage(ResponseListener listener,int messageType);
	public void sendMessage(ResponseListener listener,int messageType,String messageArgument);
}
