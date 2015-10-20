package client;


public class Status {
	
	private String currentTrackTitle;
	private boolean isPlaying;
	private String serverIP;
	private int serverPort;
	private LoadGapListStatus loadStatus;
	private String gaplistTitle;
		
	
	public Status(String currentTrackTitle, boolean isPlaying, String serverIP,
			int serverPort,String gaplistTitle, LoadGapListStatus loadStatus) {
		super();
		this.currentTrackTitle = currentTrackTitle;
		this.isPlaying = isPlaying;
		this.serverIP = serverIP;
		this.serverPort = serverPort;
		this.loadStatus = loadStatus;
		this.gaplistTitle = gaplistTitle;
	}
	
	public String getCurrentTrackTitle() {
		return currentTrackTitle;
	}
	public void setCurrentTrackTitle(String currentTrackTitle) {
		this.currentTrackTitle = currentTrackTitle;
	}
	public boolean isPlaying() {
		return isPlaying;
	}
	public void setPlaying(boolean isPlaying) {
		this.isPlaying = isPlaying;
	}
	public String getServerIP() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public int getServerPort() {
		return serverPort;
	}
	public void setServerPort(int serverPort) {
		this.serverPort = serverPort;
	}
	public LoadGapListStatus getLoadStatus() {
		return loadStatus;
	}
	public void setLoadStatus(LoadGapListStatus loadStatus) {
		this.loadStatus = loadStatus;
	}

	public String getGaplistTitle() {
		return gaplistTitle;
	}

	public void setGaplistTitle(String gaplistTitle) {
		this.gaplistTitle = gaplistTitle;
	}

}
