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
	
	public  synchronized boolean addVote(long trackID,long macAddress){
		if (!macs.containsKey(macAddress)){
			MusicTrack temp = null;
			for (MusicTrack m : wishList){
				if (m.getTrackID() == trackID){ 
					m.setVoteCount(m.getVoteCount() +1);
					temp = m;
					break;
				}
			}
			if (temp != null){
				int i = wishList.indexOf(temp);
				while (i >= 1 && wishList.get(i).getVoteCount() > wishList.get(i - 1).getVoteCount()){
					MusicTrack m2 = wishList.get(i);
					wishList.set(i, wishList.get(i-1));
					wishList.set(i-1, m2);
					i--;
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
			}
			return temp != null;
		}else return false;
	}
	
	public  synchronized boolean removeVote(long macAddress){
		if (macs.containsKey(macAddress)){
			long trackID = macs.get(macAddress);
			macs.remove(macAddress);
			MusicTrack temp = null;
			for (MusicTrack m : wishList){
				if (m.getTrackID() == trackID){ 
					m.setVoteCount(m.getVoteCount() -1);
					temp = m;
					break;
				}
			}
			if (temp != null){
				int i = wishList.indexOf(temp);
				while (i < wishList.size() - 1 && wishList.get(i).getVoteCount() < wishList.get(i + 1).getVoteCount()){
					MusicTrack m2 = wishList.get(i);
					wishList.set(i, wishList.get(i+1));
					wishList.set(i+1, m2);
					i++;
				}
				return true;
			}
			return false;
		}
		else return false;
	}
	
	public  synchronized long getVotedTrackID(long macAddress){
		if (macs.containsKey(macAddress))
			return macs.get(macAddress);
		else return -1L;
	}
	
	public  synchronized int getVoteCount(long trackID){
		if (votes.containsKey(trackID))
			return votes.get(trackID).size();
		else return 0;
	}
	
	public  synchronized ArrayList<Long> removeTrack(long trackID){
		ArrayList<Long> list = votes.remove(trackID);
		if (list != null)
			for (Long l : list){
				macs.remove(l);
			}
		return list;
	}

	public synchronized boolean removeAllVotes() {
			for (MusicTrack m : wishList){
				m.setVoteCount(0);
			}
			votes.clear();
			macs.clear();
			return true;
	}

}
