package connection;

import java.io.BufferedWriter;
import java.io.IOException;

public class Sender {

	public Sender() {
	}

	public boolean sendMessage(int messageType, String message, BufferedWriter writer) {
		try {
			switch (messageType) {
			case MessageType.YOUTUBE:writer.write(""+MessageType.YOUTUBE+MessageType.SEPERATOR+message);break;
			case MessageType.SENTFILE: writer.write(""+MessageType.SENTFILE+MessageType.SEPERATOR+message);break;
			case MessageType.SKIP: writer.write(""+MessageType.SKIP);break;
			case MessageType.PAUSERESUME: writer.write(""+MessageType.PAUSERESUME);break;
			case MessageType.GAPYOUTUBE: writer.write(""+MessageType.GAPYOUTUBE+MessageType.SEPERATOR+message);break;
			case MessageType.GAPSENTFILE: writer.write(""+MessageType.GAPSENTFILE+MessageType.SEPERATOR+message);break;
			case MessageType.GAPLISTSAVETOFILE: writer.write(""+MessageType.GAPLISTSAVETOFILE);break;
			case MessageType.GETGAPLIST: writer.write(""+MessageType.GETGAPLIST);break;
			case MessageType.GETWISHLIST: writer.write(""+MessageType.GETWISHLIST);break;
			case MessageType.DELETEFROMGAPLIST: writer.write(""+MessageType.DELETEFROMGAPLIST+MessageType.SEPERATOR+message);break;
			case MessageType.ISREADY: writer.write(""+MessageType.ISREADY);break;
			case MessageType.GETCURRENTTRACK: writer.write(""+MessageType.GETCURRENTTRACK);break;
			case MessageType.GETCURRENTPLAYBACKSTATUS: writer.write(""+MessageType.GETCURRENTPLAYBACKSTATUS);break;
			case MessageType.GAPBEGINNINGYOUTUBE: writer.write(""+MessageType.GAPBEGINNINGYOUTUBE+MessageType.SEPERATOR+message);break;
			case MessageType.BEGINNINGYOUTUBE: writer.write(""+MessageType.BEGINNINGYOUTUBE+MessageType.SEPERATOR+message);break;
			case MessageType.GETAVAILABLEGAPLISTS: writer.write(""+MessageType.GETAVAILABLEGAPLISTS);break;
			case MessageType.LOADGAPLIST: writer.write(""+MessageType.LOADGAPLIST+MessageType.SEPERATOR+message);break;
			case MessageType.GAPLISTTRACKUP: writer.write(""+MessageType.GAPLISTTRACKUP+MessageType.SEPERATOR+message);break;
			case MessageType.GAPLISTTRACKDOWN: writer.write(""+MessageType.GAPLISTTRACKDOWN+MessageType.SEPERATOR+message);break;
			case MessageType.GETCURRENTGAPLISTNAME: writer.write(""+MessageType.GETCURRENTGAPLISTNAME);break;
			case MessageType.GETTITLEFROMGAPLIST: writer.write(""+MessageType.GETTITLEFROMGAPLIST+MessageType.SEPERATOR+message);break;
			default: return false;
			}
			writer.newLine();
			try {
				writer.flush();
				return true;
			} catch (IOException e) {
				return false;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
