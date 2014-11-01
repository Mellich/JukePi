package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import utilities.IO;

public class SendedFileCommandHandler extends CommandHandler {

	
	private String filename;
	private LinkedList<MusicTrack> list;
	
	public SendedFileCommandHandler(Socket s, String filename,LinkedList<MusicTrack> list) {
		super(s);
		this.filename = filename;
		this.list = list;
	}

	@Override
	public boolean handle() {
		sendMessage(""+MessageType.READYFORRECEIVENOTIFY);
		if (IO.receiveAndSaveFile(getSocket(), filename)){
			MusicTrack m = new MusicTrack(TrackType.SENDED,filename, filename);
			addToList(m);
			return true;
		}
		else return false;
	}
	
	private int addToList(MusicTrack track){
		IO.printlnDebug(this, "adding parsed input to list");
		synchronized(list){
			list.add(track);
			return list.size();
		}
	}

}
