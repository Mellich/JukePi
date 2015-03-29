package client;

import client.serverconnection.ServerConnection;
import client.serverconnection.impl.YTJBServerConnection;

/**
 * Use this Class to create new instances of ServerConnection.
 * @author Mellich
 * @version 1.0
 */
public class ServerConnectionFactory {
	
	/**
	 * Creates a new Instance of ServerConnection and Returns it.
	 * @return the new ServerConnection instance
	 * @since 1.0
	 */
	public static ServerConnection createServerConnection(){
		return new YTJBServerConnection();
	}
	
	/**
	 * Creates a new Instance of ServerConnection and returns it.
	 * @param checkIntervall 	The Interval in ms, the Connectivity to the Server shall be 
	 * 							checked by the ServerConnection.
	 * @return The new ServerConnection Instance.
	 * @since 1.0
	 */
	public static ServerConnection createServerConnection(int checkInterval){
		return new YTJBServerConnection(checkInterval);
	}
	
	/**
	 * Creates a new Instance of ServerConnection and returns it.
	 * @param checkInterval	The Interval in ms, the Connectivity to the Server shall be
	 * 						checked by the ServerConnection.
	 * @param isAndroid	If the Client is an Android-Client, this value has to be {@code true}, 
	 * 					{@code false} else
	 * @return	The new ServerConnection Instance.
	 * @since 1.0
	 */
	public static ServerConnection createServerConnection(int checkInterval, boolean isAndroid){
		return new YTJBServerConnection(checkInterval,isAndroid);
	}
}
