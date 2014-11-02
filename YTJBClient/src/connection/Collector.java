package connection;

import java.util.LinkedList;

import javax.swing.DefaultListModel;

/**
 * A Class to collect all informations about Gaplist, Wishlist, etc.
 * @author Haeldeus
 *
 */
public class Collector {

	private LinkedList<String> gaplist;
	private LinkedList<String> wishlist;
//	private LinkedList<String> tracklist;
	private Sender s;
	
	public Collector() {
		gaplist = new LinkedList<String>();
		wishlist = new LinkedList<String>();
	//	tracklist = new LinkedList<String>();
		s = new Sender();
		
		addToGapList("Bonobo - Black Sands");
		addToGapList("Bonobo - Kiara");
		addToGapList("Charlie Boulala - Sonnenkind");
		
		addToWishList("Monkey Safari - Jorg - Original Mix");
		addToWishList("Jay Z, Alicia Keys - Empire State Of Mind");
		addToWishList("Yelle - Ce Jeu - Tepr Remix");
		addToWishList("Tiesto - Say Something");
		addToWishList("The Avener - Fade Out Lines");
		addToWishList("Dimitri Vegas, Like Mike, Sander van Doom - Project T - Original Mix");
	}
	
	public void addToGapList(String link) {
		gaplist.add(link);
		s.sendMessage(MessageType.GAPYOUTUBE, link);
	}
	
	public void addToWishList(String link) {
		wishlist.add(link);
		s.sendMessage(MessageType.YOUTUBE, link);
	}
	
	public void deleteFromGapList(int index, DefaultListModel<String> gaplistmodel, DefaultListModel<String> tracklist) {
		gaplist.remove(index);
		gaplistmodel.clear();
		tracklist.clear();
		tracklist.addElement(getPlayingFile());
		for (String i : wishlist)
			tracklist.addElement(i);
		for (String i : gaplist) {
			gaplistmodel.addElement(i);
			tracklist.addElement(i);
		}	
		s.sendMessage(MessageType.DELETEFROMGAPLIST, ""+index);
	}
	
	public int getWishListSize() {
		return wishlist.size();
	}
	
	public int getGapListSize() {
		return gaplist.size();
	}
	
	public void deleteFromWishList(int index, DefaultListModel<String> wishlistmodel, DefaultListModel<String> tracklist) {
		wishlist.remove(index);
		wishlistmodel.clear();
		tracklist.clear();
		tracklist.addElement(getPlayingFile());
		for (String i : wishlist) {
			wishlistmodel.addElement(i);
			tracklist.addElement(i);
		}
		for (String i : gaplist) {
			tracklist.addElement(i);
		}
		//Need to be implemented on Server-Side
	//	s.sendMessage(MessageType.DELETEFROMWISHLIST, ""+index);
	}
	
	public LinkedList<String> getGapList() {
		s.sendMessage(MessageType.GETGAPLIST, null);
		return gaplist;
	}
	
	public LinkedList<String> getWishList() {
		s.sendMessage(MessageType.GETWISHLIST, null);
		return wishlist;
	}
	
	public String getPlayingFile() {
		s.sendMessage(MessageType.GETCURRENTTRACK, null);
		if (!gaplist.isEmpty())
			return gaplist.getFirst();
		else
			return null;
	}
	
	public boolean skip() {
		s.sendMessage(MessageType.SKIP, null);
		//TODO
		return true;
	}
	
	public String getNextSong() {
		if (wishlist.isEmpty())
			if (gaplist.getFirst().equals(getPlayingFile()))
				return gaplist.get(1);
			else
				return gaplist.getFirst();
		else
			if (wishlist.getFirst().equals(getPlayingFile()))
				return wishlist.get(1);
			else
				return wishlist.getFirst();
	}
	
	public LinkedList<String> getTrackList() {
		LinkedList<String> result = new LinkedList<String>();
		result.add(getPlayingFile());
		for (String i : getWishList())
			result.add(i);
		for (String i : getGapList())
			result.add(i);
		return result;
	}
	
	public void notifyMe(int msg) {
		switch (msg) {
		case MessageType.LISTSUPDATEDNOTIFY: notifyGUILists();break;
		case MessageType.NEXTTRACKNOTIFY: updatePlayingFile();break;
		default: break;
		}
	}
	
	public void play() {
		s.sendMessage(MessageType.PAUSERESUME, null);
	}
	
	public void pause() {
		s.sendMessage(MessageType.PAUSERESUME, null);
	}
	
	public void notifyGUILists() {
		
	}
	
	public void updatePlayingFile() {
		
	}
}
