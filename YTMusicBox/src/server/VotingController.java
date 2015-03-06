package server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class VotingController {
	private HashMap<Long,ArrayList<Long>> votes = new HashMap<Long,ArrayList<Long>>();
	private HashMap<Long,Long> macs = new HashMap<Long,Long>();
	private LinkedList<MusicTrack> wishList;
	
	public VotingController(LinkedList<MusicTrack> wishList2) {
		this.wishList = wishList2;
	}
	
	public void addVote(long trackID,long macAddress){//TODO: wishlist neu anordnen und notify senden
		if (macs.containsKey(macAddress)){
			long oldTrackID = macs.get(macAddress);
			removeVote(oldTrackID,macAddress);
		}
		if (votes.containsKey(trackID)){
			ArrayList<Long> list = votes.get(trackID);
			list.add(macAddress);
		}
		else {
			ArrayList<Long> list = new ArrayList<Long>();
			list.add(macAddress);
			votes.put(trackID, list);
		}
		macs.put(macAddress, trackID);
		for (MusicTrack m : wishList){
			if (m.getTrackID() == trackID){ 
				m.setVoteCount(m.getVoteCount() +1);
				break;
			}
		}
	}
	
	public long getVotedTrackID(long macAddress){
		return macs.get(macAddress);
	}
	
	public int getVoteCount(long trackID){
		if (votes.containsKey(trackID))
			return votes.get(trackID).size();
		else return 0;
	}
	
	public ArrayList<Long> removeTrack(long trackID){
		ArrayList<Long> list = votes.remove(trackID);
		for (Long l : list){
			macs.remove(l);
		}
		return list;
	}
	
	public void removeVote(long trackID,long macAddress){ //TODO: wishlist neu anordnen
		if (votes.containsKey(trackID)){
			ArrayList<Long> list = votes.get(trackID);
			list.remove(macAddress);
			macs.remove(macAddress);
			for (MusicTrack m : wishList){
				if (m.getTrackID() == trackID){ 
					m.setVoteCount(m.getVoteCount() -1);
					break;
				}
			}
		}
	}
}
