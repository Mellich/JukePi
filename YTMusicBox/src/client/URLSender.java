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
			String url = reader.readLine();
			reader.close();
			Socket socket = new Socket("192.168.178.34",12345);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			//writer.write("1;\t;"+url);
			writer.write("3");
			writer.newLine();
			writer.flush();
			BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			System.out.println(r.readLine());
			r.close();
			writer.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
