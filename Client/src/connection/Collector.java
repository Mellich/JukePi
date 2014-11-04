package connection;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.net.Socket;

import javax.swing.JLabel;
import javax.swing.JRadioButton;

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
/*	private SenderReaderThread srt;
	private NotifierReaderThread nrt;
*/	
	public Collector() {
		// TODO Auto-generated constructor stub
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
	
	public boolean connect(String IP, String port) {
		try {
			System.out.println(port);
			int iport = Integer.parseInt(port);
			System.out.println(""+iport);
			System.out.println(IP);
			notifier = new Socket(IP, iport);
			sender = new Socket(IP, iport);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
