package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

import threads.NotifierReaderThread;
import threads.StabilityThread;

public class Collector {

	private JLabel gaplistlabel;
	private JLabel wishlistLabel;
	private JRadioButton wishlistRB;
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
//	private SenderReaderThread srt;
	private NotifierReaderThread nrt;
	
	public Collector() {
		s = new Sender();
		gaplist = new LinkedList<String>();
		wishlist = new LinkedList<String>();
	}
	
	public void addGaplistLabel(JLabel label) {
		this.gaplistlabel = label;
	}
	
	public void addWishlistLabel(JLabel label) {
		this.wishlistLabel = label;
	}
	
	public void addWishlistRB(JRadioButton wishlistRB) {
		this.wishlistRB = wishlistRB;
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
	
	public boolean addToList(String link) {
		if (gaplistRB.isSelected()) {
			s.sendMessage(MessageType.GAPYOUTUBE, link, senderWriter);
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
		else {
			s.sendMessage(MessageType.YOUTUBE, link, senderWriter);
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
		
		//TODO next track label
	}
	
	public void updateLists() {
		//TODO
		s.sendMessage(MessageType.GETGAPLIST, "", senderWriter);
		try {
			String answer = senderReader.readLine();
			String[] answerparts = answer.split(MessageType.SEPERATOR);
			for (int i = 1; i < answerparts.length; i++) {
				gaplist.add(answerparts[i]);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		gaplistlabel.setText(""+gaplist.size());
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
}
