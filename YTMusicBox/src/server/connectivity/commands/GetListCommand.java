package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.MessageType;
import server.YTJBServer;

public class GetListCommand extends Command {

	private boolean fromWishList;
	private YTJBServer server;
	private long mac;
	
	public GetListCommand(BufferedWriter out,int messageType,YTJBServer server,long macAddress, boolean fromWishList) {
		super(out, messageType);
		this.fromWishList = fromWishList;
		this.server = server;
		this.mac = macAddress;
	}

	@Override
	public boolean handle() {
		response(""+server.getVote(mac)+MessageType.SEPERATOR+server.getTitle(fromWishList));
		return true;
	}

}
