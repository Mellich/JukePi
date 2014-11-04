package server.connectivity.handler;

import java.io.File;
import java.net.Socket;

import network.MessageType;
import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;

public class DeleteFromListCommandHandler extends CommandHandler {

	private boolean fromWishList;
	private int trackIndex;
	private YTJBServer server;
	
	public DeleteFromListCommandHandler(Socket s,YTJBServer server, boolean fromWishList,int trackIndex) {
		super(s);
		this.fromWishList = fromWishList;
		this.trackIndex = trackIndex;
		this.server = server;
	}

	@Override
	public boolean handle() {
		try{
			MusicTrack track = server.deleteFromList(fromWishList, trackIndex);
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
