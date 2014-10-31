package server.connectivity;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import server.IO;
import server.MusicTrack;
import server.ProcessCommunicator;
import server.MusicTrack.TrackType;

/**handles youtube link commands
 * 
 * @author Mellich
 *
 */
public class YoutubeCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private String url;
	
	public YoutubeCommandHandler(Socket s, LinkedList<MusicTrack> list,String url) {
		super(s);
		this.list = list;
		this.url = url;
	}

	@Override
	public boolean handle() {
		String parsedURL;
		try {
			parsedURL = ProcessCommunicator.parseStandardURL(url);
			String title = ProcessCommunicator.parseTitle(url);
			MusicTrack yURL = new MusicTrack(TrackType.YOUTUBE,title,parsedURL);
			int position = addToList(yURL);
			sendMessage(position+". "+yURL.getTitle());
			return true;
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
