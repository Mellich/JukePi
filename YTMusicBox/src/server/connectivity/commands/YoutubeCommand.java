package server.connectivity.commands;

import java.io.BufferedWriter;
import java.io.IOException;

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
public class YoutubeCommand extends Command {

	private String url;
	private YTJBServer server;
	private boolean toWishList;
	private boolean atFirst;
	
	public YoutubeCommand(BufferedWriter out,int messageType,YTJBServer server, boolean toWishList,boolean atFirst,String url) {
		super(out, messageType);
		this.url = url;
		this.server = server;
		this.toWishList = toWishList;
		this.atFirst = atFirst;
	}

	@Override
	public boolean handle() {
		String[] parsedURL;
		try {
			parsedURL = ProcessCommunicator.parseShortURLToVideoURLAndTitle(url);
			if (parsedURL[0] != null){
				response(""+true);
				MusicTrack yURL = new MusicTrack(TrackType.YOUTUBE,parsedURL[0],parsedURL[1],url);
				addToList(yURL);
				return true;
			}else response(""+false);
		} catch (IOException e) {
			IO.printlnDebug(this, "could not handle command");
		}
		return false;
	}
	
	private void addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		server.addToList(track, toWishList, atFirst);
	}

}
