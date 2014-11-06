package server.connectivity.handler;


import java.io.BufferedWriter;
import java.io.IOException;


import network.MessageType;
import utilities.IO;

/**handles a specific command
 * 
 * @author Mellich
 *
 */
public abstract class Command {

	private BufferedWriter out;
	
	/**creates a new command handler
	 * 
	 * @param s the socket of the connection, that should be handled
	 */
	public Command(BufferedWriter out){
		this.out = out;
	}
	
	
	/**response for a request
	 * 
	 * @param s the response
	 */
	protected void response(String s){
		sendMessage(MessageType.RESPONSENOTIFY+MessageType.SEPERATOR+s);
	}
	
	/**notify the client 
	 * 
	 * @param notify the type of the notification
	 */
	protected void notify(int notify){
		sendMessage(""+notify);
	}
	
	/**sends a string to the client
	 * 
	 * @param s the string that should be sended
	 */
	private void sendMessage(String s){
		try{
			IO.printlnDebug(this, "Sending Message: "+s);
			out.write(s);
			out.newLine();
			out.flush();
		}
		catch (IOException e){
			IO.printlnDebug(this, "ERROR could not send message to client");
		}
	}
	
	/**handles the command
	 * 
	 * @return returns true when handle was successful
	 */
	public abstract boolean handle();
}
