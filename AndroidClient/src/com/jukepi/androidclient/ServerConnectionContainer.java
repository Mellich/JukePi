package com.jukepi.androidclient;

import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;

/**
 * The Class, that will create a new ServerConnection, when needed.
 * @author Mellich
 * @version 1.0
 */
public class ServerConnectionContainer {
	
	/**
	 * The current ServerConnection.
	 */
	private static ServerConnection serverConnection = null;
	
	/**
	 * Returns a new ServerConnection.
	 * @return	The new ServerConnection.
	 * @since 1.0
	 */
	public static ServerConnection getServerConnection(){
		if (serverConnection == null){
			serverConnection = ServerConnectionFactory.createServerConnection(15000, true);
		}
		return serverConnection;
	}
	
}
