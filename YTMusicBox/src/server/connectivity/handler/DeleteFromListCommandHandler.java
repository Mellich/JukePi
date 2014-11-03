package server.connectivity.handler;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;

public class DeleteFromListCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private int trackIndex;
	private YTJBServer server;
	
	public DeleteFromListCommandHandler(Socket s,YTJBServer server, LinkedList<MusicTrack> list,int trackIndex) {
		super(s);
		this.list = list;
		this.trackIndex = trackIndex;
		this.server = server;
	}

	@Override
	public boolean handle() {
		try{
			MusicTrack track = null;
			synchronized (list){
				track = list.remove(trackIndex);
			}
			if (track.getMusicType() == TrackType.SENDED){
				File musicFile = new File(track.getShortURL());
				if (musicFile.exists()){
					musicFile.delete();
					IO.printlnDebug(this, "Music file deleted from disc successfully");
				}
				else IO.printlnDebug(this, "Could not delete music file: file does not exist");
			}
			response(""+true);
			server.notifyClients(MessageType.LISTSUPDATEDNOTIFY);
		}catch (IndexOutOfBoundsException e){
			response(""+false);
		}
		return true;
	}

}
