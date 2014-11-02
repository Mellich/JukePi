package server.connectivity.handler;

import java.io.File;
import java.net.Socket;
import java.util.LinkedList;

import server.MusicTrack;
import server.MusicTrack.TrackType;
import utilities.IO;

public class DeleteFromListCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	private int trackIndex;
	
	public DeleteFromListCommandHandler(Socket s, LinkedList<MusicTrack> list,int trackIndex) {
		super(s);
		this.list = list;
		this.trackIndex = trackIndex;
	}

	@Override
	public boolean handle() {
		try{
			MusicTrack track = null;
			synchronized (list){
				track = list.remove(trackIndex);
			}
			if (track.getMusicType() == TrackType.SENDED){
				File musicFile = new File(track.getURL());
				if (musicFile.exists()){
					musicFile.delete();
					IO.printlnDebug(this, "Music file deleted from disc successfully");
				}
				else IO.printlnDebug(this, "Could not delete music file: file does not exist");
			}
			response(""+true);
		}catch (IndexOutOfBoundsException e){
			response(""+false);
		}
		return true;
	}

}
