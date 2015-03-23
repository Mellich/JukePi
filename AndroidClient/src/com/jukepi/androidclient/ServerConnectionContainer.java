package com.jukepi.androidclient;

import client.ServerConnectionFactory;
import client.serverconnection.ServerConnection;

public class ServerConnectionContainer {
	private static ServerConnection serverConnection = null;
	
	public static ServerConnection getServerConnection(){
		if (serverConnection == null){
			serverConnection = ServerConnectionFactory.createServerConnection(15000, true);
		}
		return serverConnection;
	}
	
}
