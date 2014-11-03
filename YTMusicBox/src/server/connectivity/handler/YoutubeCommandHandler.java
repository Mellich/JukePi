package server.connectivity.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;
import utilities.ProcessCommunicator;

/**handles youtube link commands
 * 
 * @author Mellich
 *
 */
public class YoutubeCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private String url;
	private YTJBServer server;
	
	public YoutubeCommandHandler(Socket s,YTJBServer server, LinkedList<MusicTrack> list,String url) {
		super(s);
		this.list = list;
		this.url = url;
		this.server = server;
	}

	@Override
	public boolean handle() {
		String parsedURL;
		try {
			parsedURL = ProcessCommunicator.parseStandardURL(url);
			if (parsedURL != null){
				String title = ProcessCommunicator.parseTitle(url);
				MusicTrack yURL = new MusicTrack(TrackType.YOUTUBE,title,parsedURL,url);
				int position = addToList(yURL);
				server.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
				response(""+position);
				return true;
			}else response(""+false);
		} catch (IOException e) {
			IO.printlnDebug(this, "could not handle command");
		}
		return false;
	}
	
	private int addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		synchronized(list){
			list.add(track);
			return list.size();
		}
	}

}
