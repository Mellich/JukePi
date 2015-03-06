package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import messages.MessageType;

public class NotifyListener {
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Gib den Link hier ein: ");
		try {
			Socket socket = new Socket("192.168.178.35",12345);//"192.168.178.34"
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			boolean running = true;
			writer.write(""+MessageType.GETGAPLIST);
			writer.newLine();			
			writer.flush();
			BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			while (running){
					System.out.println(r.readLine());
			}
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
