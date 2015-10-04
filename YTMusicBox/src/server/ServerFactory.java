package server;

import java.net.BindException;

public class ServerFactory {
	public static Server createServer(int port) throws BindException{
		return new YTJBServer(port);
	}
	
	public static Server createServer() throws BindException{
		return new YTJBServer();
	}
	
	private ServerFactory(){
		
	}
}
