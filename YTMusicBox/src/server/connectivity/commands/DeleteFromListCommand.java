package server.connectivity.commands;

import java.io.BufferedWriter;
import java.io.File;

import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;

public class DeleteFromListCommand extends Command {

	private boolean fromWishList;
	private long trackID;
	private YTJBServer server;
	
	public DeleteFromListCommand(BufferedWriter out,int messageType,YTJBServer server, boolean fromWishList,long trackID) {
		super(out, messageType);
		this.fromWishList = fromWishList;
		this.trackID = trackID;
		this.server = server;
	}

	@Override
	public boolean handle() {
		try{
			MusicTrack track = server.deleteFromList(fromWishList, trackID);
			if (track.getMusicType() == TrackType.SENDED){
				File musicFile = new File(track.getShortURL());
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
