package server.connectivity.handler;

import java.io.IOException;
import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import server.MusicTrack.TrackType;
import utilities.IO;
import utilities.ProcessCommunicator;

public class BeginYoutubeCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private YTJBServer server;
	private String url;
	
	public BeginYoutubeCommandHandler(Socket s,YTJBServer server, LinkedList<MusicTrack> list,String url) {
		super(s);
		this.list = list;
		this.server = server;
		this.url = url;
	}

	@Override
	public boolean handle() {
		String parsedURL;
		try {
			parsedURL = ProcessCommunicator.parseStandardURL(url);
			if (parsedURL != null){
				String title = ProcessCommunicator.parseTitle(url);
				MusicTrack yURL = new MusicTrack(TrackType.YOUTUBE,title,parsedURL);
				addToList(yURL);
				server.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
				response(""+true);
				return true;
			}else response(""+false);
		} catch (IOException e) {
			IO.printlnDebug(this, "could not handle command");
		}
		return false;
	}
	
	private void addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		synchronized(list){
			list.addFirst(track);
		}
	}

}
