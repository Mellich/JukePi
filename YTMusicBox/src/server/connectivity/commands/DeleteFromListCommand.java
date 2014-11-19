package server.connectivity.commands;

import java.io.BufferedWriter;
import java.io.File;

import server.MusicTrack;
import server.MusicTrack.TrackType;
import server.YTJBServer;
import utilities.IO;

public class DeleteFromListCommand extends Command {

	private boolean fromWishList;
	private int trackIndex;
	private YTJBServer server;
	
	public DeleteFromListCommand(BufferedWriter out,int messageType,YTJBServer server, boolean fromWishList,int trackIndex) {
		super(out, trackIndex);
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
		}catch (IndexOutOfBoundsException e){
			response(""+false);
		}
		return true;
	}

}
