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
	
	
	private String title;
	private String url;
	private TrackType type;
	
	/**creates a new music track
	 * 
	 * @param t the type of the new music track. see MessageType
	 * @param name the title of the track
	 * @param videoURL the url to the track (or the path)
	 */
	public MusicTrack(TrackType t,String name, String videoURL) {
		this.title = name;
		url = videoURL;
		type = t;
	}
	
	public static TrackType messageTypeToTrackType(int messageType){
		switch (messageType){
		case MessageType.YOUTUBE: return TrackType.YOUTUBE;
		case MessageType.SENDEDFILE: return TrackType.SENDED;
		default: return TrackType.UNKNOWN;
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public TrackType getMusicType(){
		return type;
	}
	
	
	public String getURL(){
		return url;
	}

}
