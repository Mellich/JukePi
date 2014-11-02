package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.YTJBServer;
import server.MusicTrack.TrackType;
import utilities.IO;

public class BeginSentFileCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private YTJBServer server;
	private String filename;
	
	public BeginSentFileCommandHandler(Socket s,YTJBServer server, LinkedList<MusicTrack> list, String filename) {
		super(s);
		this.list = list;
		this.server = server;
		this.filename = filename;
	}

	@Override
	public boolean handle() {
		response(""+MessageType.READYFORRECEIVENOTIFY);
		if (IO.receiveAndSaveFile(getSocket(), filename)){
			MusicTrack m = new MusicTrack(TrackType.SENDED,filename, filename);
			addToList(m);
			response(""+true);
			server.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
			return true;
		}
		response(""+false);
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
