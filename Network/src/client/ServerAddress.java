package client;

/**
 * Class that represents Address and Port of a YTJBServer in the network.
 * @author Mellich
 * @version 1.0
 */
public class ServerAddress {
	
	/**
	 * The IP of the YTJBServer.
	 */
	private String ip;
	
	/**
	 * The Port of the YTJBServer.
	 */
	private int port;
	
	/**
	 * Creates a new Instance of a ServerAddress.
	 * @param ip	The IP of the new Address.
	 * @param port	The Port of the new Address.
	 * @since 1.0
	 */
	public ServerAddress(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	/**
	 * Returns the IP of the Server.
	 * @return The IP Address of the Server as string representation.
	 * @since 1.0
	 */
	public String getIPAddress(){
		return ip;
	}
	
	/**
	 * Returns the Port of the Server.
	 * @return The Port of the Server where Clients can connect.
	 * @since 1.0
	 */
	public int getPort(){
		return port;
	}
}
