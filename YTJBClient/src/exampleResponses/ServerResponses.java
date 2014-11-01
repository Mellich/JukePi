package exampleResponses;

import java.util.LinkedList;

public class ServerResponses {
	
	private LinkedList<String> gapList;
	private LinkedList<String> wishList;
	
	public ServerResponses() {
		gapList = new LinkedList<String>();
		wishList = new LinkedList<String>();
	}
	
	public boolean addToGapList(String title) {
		return gapList.add(title);
	}
	
	public boolean addToWishList(String title) {
		return wishList.add(title);
	}
	
	public LinkedList<String> getGapList() {
		return gapList;
	}
	
	public LinkedList<String> getWishList() {
		return wishList;
	}
	
	public int getGapListSize() {
		return gapList.size();
	}
	
	public int getWishListSize() {
		return wishList.size();
	}
	
	public String getPlayingFile() {
		return gapList.getFirst();
	}
	
	public boolean skip() {
		String first = gapList.removeFirst();
		if (first == null)
			return false;
		gapList.addLast(first);
		if (first.equals(gapList.getFirst()))
			return false;
		return true;
	}
	
	public String getNextSong() {
		return gapList.get(1);
	}
}
