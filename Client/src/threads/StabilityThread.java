package threads;

import gui.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;

import connection.Collector;
import connection.MessageType;
import connection.Sender;

/**
 * A Thread that will check the Connection.
 * @author Haeldeus
 *
 */
public class StabilityThread extends Thread{
	
	/**
	 * The IP of the Server as a String
	 */
	private String ip;
	
	/**
	 * The Port of the Server
	 */
	private int port;
	
	/**
	 * The Frame that will be changed, when the Connection is lost.
	 */
	private JFrame frame;
	
	/**
	 * The Socket to the Server.
	 */
	private Socket s;
	
	/**
	 * The BufferedReader, that will read the answers of the Server.
	 */
	private BufferedReader reader;
	
	/**
	 * The BufferedWriter, that will send the Messages to the Server.
	 */
	private BufferedWriter writer;
	
	/**
	 * This variable will determine, if the Thread is running or not.
	 */
	private boolean running;
	
	/**
	 * The Label, that will display possible Messages.
	 */
	private JLabel fail;
	
	/**
	 * The Sender, that will send the Messages.
	 */
	private Sender sender;
	
	/**
	 * The Collector, that provides necessary Methods.
	 */
	private Collector c;
	
	/**
	 * The Constructor for the Thread.
	 * @param ip	The IP of the Server as a String.
	 * @param port	The Port of the Server.
	 * @param frame	The Frame, that will be changed, if the Connection is lost.
	 * @param fail	The Label, that will display possible Messages.
	 * @param c	The Collector, that provides necessary Methods.
	 */
	public StabilityThread(String ip, int port, JFrame frame, JLabel fail, Collector c) {
		this.ip = ip;
		this.port = port;
		this.frame = frame;
		this.running = true;
		this.fail = fail;
		this.c = c;
		this.sender = new Sender();
	}
	
	/**
	 * Executes the Test.
	 */
	public void run() {
		try {
			s = new Socket(ip, port);
			writer = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			reader = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			while (running) {
				Thread.sleep(5000);
				try {
					if (!sender.sendMessage(MessageType.ISREADY, "", writer))
						throw new IOException();
					reader.readLine();
				} catch (Exception e){
					fail.setText("Connection lost");
					fail.setVisible(true);
					frame.add(fail);
					frame.repaint();
					Thread.sleep(10000);
					frame.getContentPane().removeAll();
					GUI.main(null);
					frame.setEnabled(false);
					frame.setVisible(false);
					c.disconnect();
				}
			}
		} catch (InterruptedException e) {
			try {
				s.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}