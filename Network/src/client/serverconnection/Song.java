package client.serverconnection;

/**
 * The Class, that displays a Song with all it's properties.
 * @author Mellich
 * @version 1.0
 */
public class Song {
	/**
	 * The ID of this Song.
	 */
	private long trackID;
	
	/**
	 * The name of the Song.
	 */
	private String name;
	
	/**
	 * The amount of Votes for this Song, when it's in the Wishlist.
	 */
	private int votes;
	
	/**
	 * Determines, if the Song is the own Vote of the Client.
	 */
	private boolean ownVote;

	private boolean parsed;
	
	/**
	 * Creates a new Song-Instance.
	 * @param trackID	The ID of the Track.
	 * @param name	The Name of the Track.
	 * @param votes	The amount of Votes for the Track.
	 * @param ownVote	Determines, if the Track is the own Vote of the Client.
	 * @since 1.0
	 */
	public Song(long trackID, String name, int votes, boolean ownVote,boolean parsed){
		this.trackID = trackID;
		this.name = name;
		this.votes = votes;
		this.ownVote = ownVote;
		this.parsed = parsed;	}
	
	/**
	 * Returns, if the Song is the own Vote of the Client.
	 * @return	{@code true}, if the Song is the own Vote of the Client, {@code false} else.
	 * @since 1.0
	 */
	public boolean isOwnVote(){
		return ownVote;
	}
	
	/**
	 * Returns the ID of the Track.
	 * @return	The ID of the Track.
	 * @since 1.0
	 */
	public long getTrackID(){
		return trackID;
	}
	
	/**
	 * Returns the Name of the Song.
	 * @return	The Name of the Song.
	 * @since 1.0
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * Returns the amount of Votes for the Song.
	 * @return	The Amount of Votes for the Song.
	 * @since 1.0
	 */
	public int getVotes(){
		return votes;
	}
	
	public boolean isParsed(){
		return parsed;
	}
}
