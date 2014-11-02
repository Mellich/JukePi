package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import server.MusicTrack;
import server.YTJBServer;
import utilities.IO;

public class SaveGapListCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> gapList;
	
	public SaveGapListCommandHandler(Socket s, LinkedList<MusicTrack> gapList) {
		super(s);
		this.gapList = gapList;
	}

	@Override
	public boolean handle() {
		IO.saveGapListToFile(gapList, YTJBServer.GAPLISTFILENAME);
		IO.printlnDebug(this, "saved list successfully");
		response(""+true);
		return true;
	}

}
