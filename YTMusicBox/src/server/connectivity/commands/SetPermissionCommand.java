package server.connectivity.commands;

import java.io.BufferedWriter;

import messages.Permission;
import server.YTJBServer;
import server.connectivity.Connection;

public class SetPermissionCommand extends Command {

	private YTJBServer server;
	private Permission permission;
	private String pw;
	private Connection connection;

	public SetPermissionCommand(BufferedWriter out, int messageType, YTJBServer server, String permission,String pw, Connection conn) {
		super(out, messageType);
		this.server  =server;
		this.permission = Permission.valueOf(permission);
		this.pw = pw;
		this.connection = conn;
	}

	@Override
	public boolean handle() {
		if (server.getPW(permission).equals(pw)){
			connection.addPermission(permission);
			response(""+true);
			return true;
		}else {
			connection.removePermission(permission);
			response(""+false);
			return false;
			
		}
	}

}
