package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import utilities.IO;
import network.MessageType;


public class URLSender {
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Gib den Link hier ein: ");
		try {
			Socket socket = new Socket("192.168.178.34",12345);//"192.168.178.34"
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			boolean running = true;
			while (running){
				String input = reader.readLine();
				/*switch (input.charAt(0)){
				case 'q': running = false;
					break;
				case ' ': writer.write("4");
					break;
				case 's': writer.write("3");
					break;
				default: writer.write("5;\t;"+input);
				}*/
				writer.write("2;\t;music.mp3");
				writer.newLine();
				writer.flush();
				BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				if (r.readLine().equals(""+MessageType.READYFORRECEIVENOTIFY)){
					System.out.println("Sende Datei...");
					IO.sendFile(socket, input );
				}
			}
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}