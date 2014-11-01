package server.connectivity.handler;

import java.net.Socket;
import java.util.LinkedList;

import server.MusicTrack;

public class GetListCommandHandler extends CommandHandler {

	private LinkedList<MusicTrack> list;
	
	public GetListCommandHandler(Socket s, LinkedList<MusicTrack> list) {
		super(s);
		this.list = list;
	}

	@Override
	public boolean handle() {
		for (int i = 0; i < list.size(); i++){
			sendMessage(i+". "+list.get(i).getTitle());
		}
		return true;
	}

}
