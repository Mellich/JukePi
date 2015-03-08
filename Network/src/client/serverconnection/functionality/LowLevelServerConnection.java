package client.serverconnection.functionality;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

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
	
	public static InetAddress getLocalIPAddress(){
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null; 
	}
}
