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
		StringBuilder message = new StringBuilder();
		for (MusicTrack m : list){
			message.append(m.getTitle()+";\t;");
		}
		sendMessage(message.toString());
		return true;
	}

}
