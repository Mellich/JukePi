package client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;


public class URLSender {
	
	public static void main(String[] args) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Gib den Link hier ein: ");
		try {
			Socket socket = new Socket("192.168.178.20",22222);//"192.168.178.34"
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
			1;	;		break;
				default: for (int i = 0; i < 10;i++)
							writer.write(input);
				}*/
					writer.write(input);
					writer.newLine();
					writer.flush();
					BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
					System.out.println(r.readLine());
				/*
				if (r.readLine().equals(""+MessageType.READYFORRECEIVENOTIFY)){
					System.out.println("Sende Datei...");
					IO.sendFile(socket, input );
				}*/
			}
			reader.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}