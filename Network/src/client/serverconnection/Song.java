package client.serverconnection;

public class Song {
	private long trackID;
	private String name;
	private int votes;
	private boolean ownVote;
	
	public Song(long trackID, String name, int votes, boolean ownVote){
		this.trackID = trackID;
		this.name = name;
		this.votes = votes;
		this.ownVote = ownVote;
	}
	
	public boolean isOwnVote(){
		return ownVote;
	}
	
	public long getTrackID(){
		return trackID;
	}
	
	public String getName(){
		return name;
	}
	
	public int getVotes(){
		return votes;
	}
}
