package server.connectivity.commands;

import java.io.BufferedWriter;

import server.YTJBServer;

public class GetListCommand extends Command {

	private boolean fromWishList;
	private YTJBServer server;
	
	public GetListCommand(BufferedWriter out,YTJBServer server, boolean fromWishList) {
		super(out);
		this.fromWishList = fromWishList;
		this.server = server;
	}

	@Override
	public boolean handle() {
		response(server.getTitle(fromWishList));
		return true;
	}

}
