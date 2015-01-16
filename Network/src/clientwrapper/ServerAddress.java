package clientwrapper;

/**
 * Class that represents Address and Port of a YTJBServer in the network
 * @author mellich
 *
 */
public class ServerAddress {
	private String ip;
	private int port;
	
	public ServerAddress(String ip,int port){
		this.ip = ip;
		this.port = port;
	}
	
	/**Getter-function for the ip Address of the server
 	 * 
	 * @return the IP address of the server as string representation
	 */
	public String getIPAddress(){
		return ip;
	}
	
	/**getter-function for the port of the server
	 * 
	 * @return the port of the server where clients can connect
	 */
	public int getPort(){
		return port;
	}
}
