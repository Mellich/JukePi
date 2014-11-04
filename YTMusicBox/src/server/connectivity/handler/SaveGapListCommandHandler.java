package server.connectivity.handler;

import java.net.Socket;
import server.YTJBServer;
import utilities.IO;

public class SaveGapListCommandHandler extends CommandHandler {

	private YTJBServer server;
	
	public SaveGapListCommandHandler(Socket s, YTJBServer server) {
		super(s);
		this.server = server;
	}

	@Override
	public boolean handle() {
		if (server.saveGapListToFile()){
			IO.printlnDebug(this, "saved list successfully");
			response(""+true);
			return true;
		}
		else{
			IO.printlnDebug(this, "ERROR: saving the gap list was not possible");
			response(""+false);
			return false;			
		}
	}

}
