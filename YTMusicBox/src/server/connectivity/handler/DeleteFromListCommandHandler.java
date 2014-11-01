package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import server.MusicTrack;

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
		synchronized (list){
		sendMessage("geloescht: "+list.remove(trackIndex).getTitle());
		}
		return true;
	}

}
