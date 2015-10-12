package server;

import messages.ParseStatus;



/**a music track that can be played by the server
 * 
 * @author Mellich
 *
 */
public class MusicTrack {
	
	/**specificates the type of a music track
	 * 
	 * @author Mellich
	 *
	 */
	public enum TrackType{
		YOUTUBE,
		SENDED,
		UNKNOWN
	}
	
	private static long currentTrackID = 0;
	
	
	private String shortURL;
	private String title;
	private String videoURL = "";
	private TrackType type;
	private long trackID;
	private int voteCount = 0;
	private boolean isVideo = true;
	private boolean isFromSavedGapList = false;
	
	public MusicTrack(MusicTrack m){
		this(m.getMusicType(),m.getTitle(),m.getVideoURL(),m.getShortURL(),m.isFromSavedGapList());
	}
	
	public MusicTrack(TrackType t,String name, String videoURL,String shortURL, boolean isFromSavedGapList) {
		this(t,name,videoURL,shortURL);
		this.isFromSavedGapList = isFromSavedGapList;
	}
	
	/**creates a new music track
	 * 
	 * @param t the type of the new music track. see MessageType
	 * @param name the title of the track
	 * @param videoURL the url to the track (or the path)
	 */
	public MusicTrack(TrackType t,String name, String videoURL,String shortURL) {
		this.title = name;
		this.videoURL = videoURL;
		type = t;
		this.shortURL = shortURL;
		trackID = currentTrackID;
		currentTrackID++;
		if (shortURL.contains("//soundcloud.com/"))
			isVideo = false;
	}
	
	public MusicTrack(TrackType t, String name, String shortURL,boolean isFromSavedGapList){
		this(t,name,"",shortURL,isFromSavedGapList);
	}
	
	public void setVoteCount(int count){
		voteCount = count;
	}
	
	public void setTitle(String t){
		title = t;
	}
	
	public void setVideoURL(String videoURL){
		this.videoURL = videoURL;
	}
	
	public int getVoteCount(){
		return voteCount;
	}
	
	public String getTitle(){
		return title;
	}
	
	public boolean getIsVideo(){
		return isVideo;
	}
	
	public TrackType getMusicType(){
		return type;
	}
	
	public long getTrackID(){
		return trackID;
	}
	
	public boolean isReady(){
		return !(this.videoURL.equals("") || this.isParsing() || isError());
	}
	
	public boolean isParsing(){
		return this.videoURL.equals("PARSING");
	}
	
	public boolean isError(){
		return this.videoURL.equals("ERROR");
	}
	
	public String getShortURL(){
		return shortURL;
	}
	
	public String getVideoURL(){
		return videoURL;
	}
	
	public boolean isFromSavedGapList(){
		return this.isFromSavedGapList;
	}

	public String getParseStatus() {
		if (this.isParsing())
			return ParseStatus.PARSING.toString();
		if (!this.isReady())
			if (this.isError())
				return ParseStatus.ERROR.toString();
			else return ParseStatus.NOT_PARSED.toString();
		else return ParseStatus.PARSED.toString();
	}

}
