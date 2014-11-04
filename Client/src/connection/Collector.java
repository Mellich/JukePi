package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;

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
	private JLabel nowPlaying;
	private JLabel nextTrack;
	private Sender s;
	private StabilityThread st;
/*	private SenderReaderThread srt;
	private NotifierReaderThread nrt;
*/	
	public Collector() {
		s = new Sender();
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
	
	public boolean connect(String IP, String port) {
		try {
			int iport = Integer.parseInt(port);
			notifier = new Socket(IP, iport);
			sender = new Socket(IP, iport);
			notifierWriter = new BufferedWriter(new OutputStreamWriter(notifier.getOutputStream()));
			senderWriter = new BufferedWriter(new OutputStreamWriter(sender.getOutputStream()));
			notifierReader = new BufferedReader(new InputStreamReader(notifier.getInputStream()));
			senderReader = new BufferedReader(new InputStreamReader(sender.getInputStream()));
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
	
	public boolean skip(JLabel fail) {
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
}
