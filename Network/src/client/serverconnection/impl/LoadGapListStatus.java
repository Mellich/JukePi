package client.serverconnection.impl;

public class LoadGapListStatus{
	private int loaded = 0;
	private int max = 0;
	protected LoadGapListStatus(int loaded, int max){
		this.loaded = loaded;
		this.max = max;
	}
	
	public int getLoadedTrackCount(){
		return loaded;
	}
	
	public int getMaxTrackCount(){
		return max;
	}
}
