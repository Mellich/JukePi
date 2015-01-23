package server.connectivity.commands;


import java.io.BufferedWriter;
import java.io.IOException;

import messages.MessageType;
import utilities.IO;

/**handles a specific command
 * 
 * @author Mellich
 *
 */
public abstract class Command {

	private BufferedWriter out;
	private int messageType;
	
	/**creates a new command handler
	 * 
	 * @param out the buffered writer for the socket to response to
	 * @param messageType the message type of the received message
	 */
	public Command(BufferedWriter out,int messageType){
		this.out = out;
		this.messageType = messageType;
	}
	
	
	/**response for a request
	 * 
	 * @param s the response
	 */
	protected void response(String s){
		sendMessage(MessageType.RESPONSENOTIFY+MessageType.SEPERATOR+messageType+MessageType.SEPERATOR+s);
	}
	
	/**notify the client 
	 * 
	 * @param notify the type of the notification
	 */
	protected void notify(int notify,String arguments){
		sendMessage(""+notify+arguments);
	}
	
	/**sends a string to the client
	 * 
	 * @param s the string that should be sended
	 */
	private void sendMessage(String s){
		try{
			out.write(s);
			out.newLine();
			out.flush();
		}
		catch (IOException e){
			//IO.printlnDebug(this, "ERROR could not send message to client");
		}
	}
	
	/**handles the command
	 * 
	 * @return returns true when handle was successful
	 */
	public abstract boolean handle();
}
