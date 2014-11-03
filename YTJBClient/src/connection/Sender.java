package connection;

import java.io.BufferedWriter;
/**
 * A Class to send the commands to the Server.
 * @author Haeldeus
 *
 */
public class Sender {
	
	private boolean running;
	
	public Sender() {
		running = true;
	}
	
	public boolean sendMessage(int messageType, String message, BufferedWriter writer) {
		if (running) {
			try {
				switch (messageType) {
				case MessageType.YOUTUBE:writer.write(""+MessageType.YOUTUBE+MessageType.SEPERATOR+message);break;
				case MessageType.SENDEDFILE: writer.write(""+MessageType.SENDEDFILE+MessageType.SEPERATOR+message);break;
				case MessageType.SKIP: writer.write(""+MessageType.SKIP);break;
				case MessageType.PAUSERESUME: writer.write(""+MessageType.PAUSERESUME);break;
				case MessageType.GAPYOUTUBE: writer.write(""+MessageType.GAPYOUTUBE+MessageType.SEPERATOR+message);break;
				case MessageType.GAPSENTFILE: writer.write(""+MessageType.GAPSENTFILE+MessageType.SEPERATOR+message);break;
				case MessageType.GAPLISTSAVETOFILE: writer.write(""+MessageType.GAPLISTSAVETOFILE);break;
				case MessageType.GETGAPLIST: writer.write(""+MessageType.GETGAPLIST);break;
		/**/	case MessageType.GETWISHLIST: writer.write(""+MessageType.GETWISHLIST);writer.newLine();writer.flush();break;
				case MessageType.DELETEFROMGAPLIST: writer.write(""+MessageType.DELETEFROMGAPLIST+MessageType.SEPERATOR+message);writer.newLine();writer.flush();break;
				case MessageType.ISREADY: writer.write(""+MessageType.ISREADY);writer.newLine();writer.flush();break;
				case MessageType.GETCURRENTTRACK: writer.write(""+MessageType.GETCURRENTTRACK);writer.newLine();writer.flush();break;
				case MessageType.NEXTTRACKNOTIFY: writer.write(""+MessageType.NEXTTRACKNOTIFY);writer.newLine();writer.flush();break;
				case MessageType.LISTSUPDATEDNOTIFY: writer.write(""+MessageType.LISTSUPDATEDNOTIFY);writer.newLine();writer.flush();break;
				case MessageType.GETCURRENTPLAYBACKSTATUS: writer.write(""+MessageType.GETCURRENTPLAYBACKSTATUS);writer.newLine();writer.flush();break;
				case MessageType.READYFORRECEIVENOTIFY: writer.write(""+MessageType.READYFORRECEIVENOTIFY);writer.newLine();writer.flush();break;
				case MessageType.RESPONSENOTIFY: writer.write(""+MessageType.RESPONSENOTIFY);writer.newLine();writer.flush();break;
				case MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY: writer.write(""+MessageType.NOTIMPLEMENTEDCOMMANDNOTIFY);writer.newLine();writer.flush();break; 
				default: return false;
				}
				writer.newLine();
				writer.flush();
			}
			catch (Exception e) {
					e.printStackTrace();
					return false;
			}
		}
		return true;
	}
}