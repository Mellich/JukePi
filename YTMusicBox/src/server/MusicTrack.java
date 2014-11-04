package server;


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
	
	
	private String shortURL;
	private String title;
	private String videoURL;
	private TrackType type;
	
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
	}
	
	public String getTitle(){
		return title;
	}
	
	public TrackType getMusicType(){
		return type;
	}
	
	public String getShortURL(){
		return shortURL;
	}
	
	public String getVideoURL(){
		return videoURL;
	}

}
