package server;



public class GapListLoader extends Thread {
	
	
	private YTJBServer server;
	
	public GapListLoader( YTJBServer server) {
		this.server = server;
	}
	
	@Override
	public void run() {
		super.run();
		server.loadGapListFromFile();
	}
}
