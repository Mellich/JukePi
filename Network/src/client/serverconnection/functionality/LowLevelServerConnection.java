package client.serverconnection.functionality;

import client.listener.ResponseListener;

public interface LowLevelServerConnection {
	public boolean connect();
	public boolean close();
	public String getIPAddress();
	public int getPort();
	public boolean sendMessage(ResponseListener listener,int messageType);
	public boolean sendMessage(ResponseListener listener,int messageType,String messageArgument);
	public boolean sendMessage(int messageType);
	public boolean sendMessage(int messageType,String messageArgument);
	public String[] sendBlockingMessage(int messageType);
	public String[] sendBlockingMessage(int messageType,String messageArgument);
}
