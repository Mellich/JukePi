package server.connectivity.handler;

import java.net.Socket;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;

public class SentFileCommandHandler extends CommandHandler {

	
	private String filename;
	private YTJBServer server;
	private boolean toWishList;
	private boolean atFirst;
	
	public SentFileCommandHandler(Socket s, String filename,YTJBServer server,boolean toWishList,boolean atFirst) {
		super(s);
		this.filename = filename;
		this.server = server;
		this.atFirst = atFirst;
		this.toWishList = toWishList;
	}

	@Override
	public boolean handle() {
		response(""+MessageType.READYFORRECEIVENOTIFY);
		if (IO.receiveAndSaveFile(getSocket(), filename)){
			MusicTrack m = new MusicTrack(TrackType.SENDED,filename, filename,filename);
			addToList(m);
			return true;
		}
		else return false;
	}
	
	private void addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		server.addToList(track, toWishList, atFirst);
	}

}
