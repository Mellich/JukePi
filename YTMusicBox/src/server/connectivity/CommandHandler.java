package server.connectivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import server.IO;

/**handles a specific command
 * 
 * @author Mellich
 *
 */
public abstract class CommandHandler {

	private Socket socket;
	
	/**creates a new command handler
	 * 
	 * @param s the socket of the connection, that should be handled
	 */
	public CommandHandler(Socket s){
		socket = s;
	}
	
	/**sends a string to the client
	 * 
	 * @param s the string that should be sended
	 */
	protected void sendMessage(String s){
		try{
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			IO.printlnDebug(this, "Sending Message: "+s);
			out.write(s);
			out.newLine();
			out.flush();
		}
		catch (IOException e){
			IO.printlnDebug(this, "ERROR could not send message to client");
		}
	}
	
	/**receives a line from the client
	 * 
	 * @return the received line. returns null, when error occurred
	 */
	protected String receiveMessage(){
		try{
		BufferedReader networkInput;
		networkInput = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		IO.printlnDebug(this, "Waiting for input...");
		return networkInput.readLine();
		}
		catch (IOException e){
			IO.printlnDebug(this, "ERROR could not receive message from client");			
		}
		return null;
	}
	
	/**handles the command
	 * 
	 * @return returns true when handle was successful
	 */
	public abstract boolean handle();
}
