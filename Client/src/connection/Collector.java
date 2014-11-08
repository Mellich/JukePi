package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import threads.NotifierReaderThread;
import threads.StabilityThread;

public class Collector {

	private JLabel gaplistlabel;
	private JLabel wishlistlabel;
	private JRadioButton gaplistRB;
	private Socket notifier;
	private Socket sender;
	private BufferedWriter notifierWriter;
	private BufferedWriter senderWriter;
	private BufferedReader notifierReader;
	private BufferedReader senderReader;
	private JButton play;
	private JLabel nowPlaying;
	private JLabel nextTrack;
	private Sender s;
	private StabilityThread st;
	private LinkedList<String> gaplist;
	private LinkedList<String> wishlist;
	private NotifierReaderThread nrt;
	private DefaultListModel<String> gaplistModel;
	private DefaultListModel<String> wishlistModel;
	private JFrame secondFrame;
	
	public Collector() {
		s = new Sender();
		gaplist = new LinkedList<String>();
		wishlist = new LinkedList<String>();
		gaplistModel = new DefaultListModel<String>();
		wishlistModel = new DefaultListModel<String>();
	}
	
	public void addGaplistLabel(JLabel label) {
		this.gaplistlabel = label;
	}
	
	public void addWishlistLabel(JLabel label) {
		this.wishlistlabel = label;
	}
	
	public void addGaplistRB(JRadioButton gaplistRB) {
		this.gaplistRB = gaplistRB;
	}
	
	public void addNowPlayingLabel(JLabel nowPlaying) {
		this.nowPlaying = nowPlaying;
	}
	
	public void addNextTrackLabel(JLabel nextTrack) {
		this.nextTrack = nextTrack;
	}
	
	public void addPlayButton(JButton play) {
		this.play = play;
	}
	
	public void addGaplistModel(DefaultListModel<String> gaplistModel) {
		this.gaplistModel = gaplistModel;
	}
	
	public void addWishlistModel(DefaultListModel<String> wishlistModel) {
		this.wishlistModel = wishlistModel;
	}
	
	public void addSecondFrame(JFrame frame) {
		this.secondFrame = frame;
	}
	
	public boolean connect(String IP, String port) {
		try {
			int iport = Integer.parseInt(port);
			notifier = new Socket(IP, iport);
			sender = new Socket(IP, iport);
			notifierWriter = new BufferedWriter(new OutputStreamWriter(notifier.getOutputStream()));
			senderWriter = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
			notifierReader = new BufferedReader(new InputStreamReader(notifier.getInputStream()));
			senderReader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
			nrt = new NotifierReaderThread(notifierReader, this);
			nrt.start();
			notifierWriter.write("20");
			notifierWriter.newLine();
			notifierWriter.flush();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void disconnect() {
		try {
			nrt.interrupt();
			notifier.close();
			sender.close();
			st.interrupt();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void startStableTest(String ip, int port, JFrame frame, JLabel fail) {
		st = new StabilityThread(ip, port, frame, fail, this);
	//	st.start();
	}
	
	public boolean addToList(String link, boolean inFront) {
		if (gaplistRB.isSelected())
			if (!inFront)
				s.sendMessage(MessageType.GAPYOUTUBE, link, senderWriter);
			else 
				s.sendMessage(MessageType.GAPBEGINNINGYOUTUBE, link, senderWriter);
		else
			if (!inFront)
				s.sendMessage(MessageType.YOUTUBE, link, senderWriter);
			else
				s.sendMessage(MessageType.BEGINNINGYOUTUBE, link, senderWriter);
		
		if (secondFrame != null) {
			secondFrame.repaint();
		}
		
		try {
			String answer = senderReader.readLine();
			String pos[] = answer.split(MessageType.SEPERATOR);
			if (pos[1].equals("false")) {
				return false;
			} else {
				return true;
			}
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean skip() {
		s.sendMessage(MessageType.SKIP, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true"))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public boolean playButtonPressed() {
		s.sendMessage(MessageType.PAUSERESUME, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true"))
				return true;
			else
				return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void nextTrack() {
		s.sendMessage(MessageType.GETCURRENTTRACK, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			nowPlaying.setText(answerparts[1]);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (wishlist.isEmpty())
			if (gaplist.isEmpty())
				nextTrack.setText("No tracks in the lists");
			else
				if (gaplist.size() == 1)
					nextTrack.setText(gaplist.get(0));
				else
					nextTrack.setText(gaplist.get(1));
		else
			if (wishlist.size() == 1)
				nextTrack.setText(wishlist.get(0));
			else
				nextTrack.setText(wishlist.get(1));
	}
	
	public void updateLists() {
		//TODO
		s.sendMessage(MessageType.GETGAPLIST, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			gaplist.clear();
			for (int i = 1; i < answerparts.length; i++) {
				gaplist.add(answerparts[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gaplistlabel.setText(""+gaplist.size());
		
		s.sendMessage(MessageType.GETWISHLIST, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String answerparts[] = answer.split(MessageType.SEPERATOR);
			wishlist.clear();
			for (int i = 1; i < answerparts.length; i++) {
				wishlist.add(answerparts[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		wishlistlabel.setText(""+wishlist.size());
		fillModels();
		if (secondFrame != null) {
			secondFrame.repaint();
		}
	}
	
	public void updateStatus() {
		s.sendMessage(MessageType.GETCURRENTPLAYBACKSTATUS, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true")) {
				play.setText("Pause");
				play.setToolTipText("Click here to pause the current track");
			}
			else {
				play.setText("Play");
				play.setToolTipText("Click here to play the current track");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public boolean getStatus() {
		s.sendMessage(MessageType.GETCURRENTPLAYBACKSTATUS, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			if (answerparts[1].equals("true")) {
				return true;
			}
			else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
	
	public void fillModels() {
		gaplistModel.clear();
		wishlistModel.clear();
		for (String i : gaplist) {
			gaplistModel.addElement(i);
		}
		for (String i : wishlist) {
			wishlistModel.addElement(i);
		}
	}
	
	public boolean deleteTrack(int index) {
		s.sendMessage(MessageType.DELETEFROMGAPLIST, ""+index, senderWriter);
		try {
			String[] answer = senderReader.readLine().split(MessageType.SEPERATOR);
			System.out.println(answer[1]);
			if (answer[1].equals("true"))
				return true;
			else
				return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public void saveGaplist() {
		s.sendMessage(MessageType.GAPLISTSAVETOFILE, "", senderWriter);
		try {
			senderReader.readLine();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
