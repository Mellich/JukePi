package server.connectivity.handler;

import java.net.Socket;
import server.YTJBServer;

public class GetListCommandHandler extends CommandHandler {

	private boolean fromWishList;
	private YTJBServer server;
	
	public GetListCommandHandler(Socket s,YTJBServer server, boolean fromWishList) {
		super(s);
		this.fromWishList = fromWishList;
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(server.getTitle(fromWishList));
		return true;
	}

}
