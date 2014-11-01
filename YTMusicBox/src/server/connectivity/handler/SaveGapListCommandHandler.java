package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import server.IO;
import server.MusicTrack;
import server.YTJBServer;

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
		return true;
	}

}
