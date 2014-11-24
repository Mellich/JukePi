package connection;

import java.io.BufferedWriter;

/**
 * A Class to send Messages to the Server.
 * @author Haeldeus
 *
 */
public class Sender {		//TODO: Why not static?!? You not have any variables in here

	/**
	 * Sends a Message to the Server.
	 * @param messageType	The MessageType.
	 * @param message	The Message to be sent.
	 * @param writer	The Writer, that will send the Message.
	 * @return	True, if the Message was sent, false else.
	 */
	public boolean sendMessage(int messageType, String message, BufferedWriter writer) {
		try {
			writer.write(messageType+MessageType.SEPERATOR+message);
			writer.newLine();
			writer.flush();
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
