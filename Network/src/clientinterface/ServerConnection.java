package clientinterface;

import clientinterface.listener.ResponseListener;

public interface ServerConnection {
	public boolean connect();
	public boolean close();
	public String getIPAddress();
	public int getPort();
	public boolean sendMessage(ResponseListener listener,int messageType);
	public boolean sendMessage(ResponseListener listener,int messageType,String messageArgument);
	public boolean sendMessage(int messageType);
	public boolean sendMessage(int messageType,String messageArgument);
}
