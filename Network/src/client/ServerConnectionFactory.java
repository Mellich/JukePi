package client;

import client.serverconnection.ServerConnection;
import client.serverconnection.impl.YTJBServerConnection;

/**use this class to create new instances of ServerConnection
 * 
 * @author mellich
 *
 */
public class ServerConnectionFactory {
	
	/**creates a new instance of ServerConnection and returns it
	 * 
	 * @return the new ServerConnection instance
	 */
	public static ServerConnection createServerConnection(){
		return new YTJBServerConnection();
	}
	
	/**creates a new instance of ServerConnection and returns it
	 * 
	 * @param checkIntervall the intervall in ms the connectivity to the server shall be checked by the ServerConnection
	 * @return the new ServerConnection instance
	 */
	public static ServerConnection createServerConnection(int checkIntervall){
		return new YTJBServerConnection(checkIntervall);
	}
}
