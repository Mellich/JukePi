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

public class StabilityThread extends Thread{
	
	private String ip;
	private int port;
	private JFrame frame;
	private Socket s;
	private BufferedReader reader;
	private BufferedWriter writer;
	private boolean running;
	private JLabel fail;
	private Sender sender;
	private Collector c;
	
	public StabilityThread(String ip, int port, JFrame frame, JLabel fail, Collector c) {
		this.ip = ip;
		this.port = port;
		this.frame = frame;
		this.running = true;
		this.fail = fail;
		this.c = c;
		this.sender = new Sender();
	}
	
	@Override
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