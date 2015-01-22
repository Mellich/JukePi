package server;

public interface Server {
	public void startUp();
	public void shutDown();
	public String getWorkingDir();
	public int getCurrentClientCount();
	public int getCurrentPlayerCount();
	
}
