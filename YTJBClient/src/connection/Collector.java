package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;

/**
 * A Class to collect all informations about Gaplist, Wishlist, etc.
 * @author Haeldeus
 *
 */
public class Collector {

	Socket notifier;
	Socket sender;
	BufferedWriter notifierWriter;
	BufferedWriter senderWriter;
	BufferedReader notifierReader;
	BufferedReader senderReader;
	private SenderReaderThread srt;
	private NotifierReaderThread nrt;
	
	private DefaultListModel<String> gaplist;
	private DefaultListModel<String> wishlist;
	private DefaultListModel<String> tracklist;
	private JLabel currentSong;
	private Sender s;
	
	public Collector() {
		s = new Sender();
		try {
			notifier = new Socket("192.168.178.34",12345);
			sender = new Socket("192.168.178.34",12345);
			notifierWriter = new BufferedWriter(new OutputStreamWriter(notifier.getOutputStream()));
			senderWriter = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
			notifierReader = new BufferedReader(new InputStreamReader(notifier.getInputStream()));
			senderReader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			notifierWriter.write("20");
			notifierWriter.newLine();
			notifierWriter.flush();
		}
		catch (Exception e) {
			System.out.println("Noob");
		}
		srt = new SenderReaderThread(senderReader, this);
		srt.start();
		nrt = new NotifierReaderThread(notifierReader, this);
		nrt.start();
		}
	
	public void addToGapList(String link) {
		s.sendMessage(MessageType.GAPYOUTUBE, link, senderWriter);
	}
	
	public void addToWishList(String link) {
		s.sendMessage(MessageType.YOUTUBE, link, senderWriter);
		srt.addCatcher();
	}
	
	public void deleteFromGapList(int index, DefaultListModel<String> gaplistmodel, DefaultListModel<String> tracklist) {	
		s.sendMessage(MessageType.DELETEFROMGAPLIST, ""+index, senderWriter);
	}
	
	public int getWishListSize() {
		//TODO
		return 4;
	}
	
	public int getGapListSize() {
		//TODO
		return 4;
	}
	
	public void deleteFromWishList(int index, DefaultListModel<String> wishlistmodel, DefaultListModel<String> tracklist) {
		//Need to be implemented on Server-Side
	//	s.sendMessage(MessageType.DELETEFROMWISHLIST, ""+index);
	}
	
	public LinkedList<String> getGapList() {
		s.sendMessage(MessageType.GETGAPLIST, "", senderWriter);
		return srt.updateList();
	} 
	
	public LinkedList<String> getWishList() {
		s.sendMessage(MessageType.GETWISHLIST, null, senderWriter);
		//TODO
		return null;
	}
	
	public String getPlayingFile() {
		s.sendMessage(MessageType.GETCURRENTTRACK, null, senderWriter);
		return srt.getPlayingFile();
	}
	
	public boolean skip() {
		s.sendMessage(MessageType.SKIP, null, senderWriter);
		srt.skipCatcher();
		return true;
	}
	
	public String getNextSong() {
		return "Deine Mutter";
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
/*		case MessageType.LISTSUPDATEDNOTIFY: notifyGUILists();break;
		case MessageType.NEXTTRACKNOTIFY: updatePlayingFile();break;
*/		default: break;
		}
	}
	
	public void play() {
		s.sendMessage(MessageType.PAUSERESUME, null, senderWriter);
	}
	
	public void pause() {
		s.sendMessage(MessageType.PAUSERESUME, null, senderWriter);
	}
	
	public void updateLists() {
	//	a.updateGapList(gaplist);
	//	System.out.println(gaplist.get(0));
	}
	
	public void updatePlayingFile() {
		
	}
	
	public void fillModels(DefaultListModel<String> gaplist,DefaultListModel<String> wishlist,DefaultListModel<String> tracklist) {
		s.sendMessage(MessageType.GETGAPLIST, "",senderWriter);
		LinkedList<String> gl = srt.updateList();
		for (String i : gl) {
			gaplist.addElement(i);
			System.out.println(i);
		}
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
		s.sendMessage(MessageType.GETWISHLIST, "", senderWriter);
		LinkedList<String> wl = srt.updateList();
		for (String i : wl) {
			wishlist.addElement(i);
			System.out.println(i);
		}
	}
}
