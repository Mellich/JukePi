package server.connectivity.handler;

import java.io.BufferedWriter;

import server.YTJBServer;
import utilities.IO;

public class SaveGapListCommand extends Command {

	private YTJBServer server;
	
	public SaveGapListCommand(BufferedWriter out, YTJBServer server) {
		super(out);
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
