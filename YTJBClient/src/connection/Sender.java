package connection;

import java.io.BufferedWriter;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * A Class to send the commands to the Server.
 * @author Haeldeus
 *
 */
public class Sender {
	
	private boolean running;
	
	public Sender() {
		running = false;
	}
	
	public boolean sendMessage(int messageType, String message) {
		if (running) {
			try {
				Socket socket = new Socket("192.168.178.34",12345);
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				
				switch (messageType) {
				case MessageType.YOUTUBE: writer.write("1;\t;"+message);break;
				case MessageType.SENDEDFILE: writer.write("2;\t;"+message);break;
				case MessageType.SKIP: writer.write("3");break;
				case MessageType.PAUSERESUME: writer.write("4");break;
				case MessageType.GAPYOUTUBE: writer.write("5;\t;"+message);break;
				case MessageType.GAPSENDEDFILE: writer.write("6;\t;"+message);break;
				case MessageType.GAPLISTSAVETOFILE: writer.write("7");break;
				case MessageType.GETGAPLIST: writer.write("8");break;
				case MessageType.GETWISHLIST: writer.write("9");break;
				case MessageType.DELETEFROMGAPLIST: writer.write("10;\t;"+message);break;
				case MessageType.ISREADY: writer.write("11");break;
				case MessageType.GETCURRENTTRACK: writer.write("12");break;
				case MessageType.NEXTTRACKNOTIFY: writer.write("13");break;
				case MessageType.LISTSUPDATEDNOTIFY: writer.write("14");break;
				case MessageType.GETCURRENTPLAYBACKSTATUS: writer.write("15");break;
				case MessageType.READYFORRECEIVENOTIFY: writer.write("16");break;
				default: break;
				}
				writer.close();
				socket.close(); //Just for performance reasons atm
			}
			catch (Exception e) {
					e.printStackTrace();
					return false;
			}
		}
		return true;
	}
}