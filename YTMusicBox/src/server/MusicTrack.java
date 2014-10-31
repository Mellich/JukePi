package server;

public class MusicTrack {
	
	private String title;
	private String url;
	private int type;
	
	public MusicTrack(int t,String name, String videoURL) {
		this.title = name;
		url = videoURL;
		type = t;
	}
	
	public String getTitle(){
		return title;
	}
	
	public int getMusicType(){
		return type;
	}
	
	
	public String getURL(){
		return url;
	}

}
