package server;

import java.net.BindException;

public class ServerFactory {
	public static Server createServer(int port, String adminPassword, String playerPassword) throws BindException{
		return new YTJBServer(port, adminPassword, playerPassword);
	}
	
	public static Server createServer() throws BindException{
		return new YTJBServer();
	}
	
	private ServerFactory(){
		
	}
}
