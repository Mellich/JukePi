package server;

public class ServerFactory {
	public static Server createServer(int port){
		return new YTJBServer(port);
	}
	
	private ServerFactory(){
		
	}
}
