package connection;

import java.io.BufferedWriter;

public class Sender {		//TODO: Why not static?!? You not have any variables in here

	public Sender() {
	}

	public boolean sendMessage(int messageType, String message, BufferedWriter writer) {
		try {
			writer.write(messageType+MessageType.SEPERATOR+message);		//other stuff right too complicated, this works too
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
