package client.serverconnection;

import java.io.IOException;

public class UDPTimeoutException extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1811555544273154772L;
	
	@Override
	public String getMessage() {
		return "A connection via UDP could not be established. No running server found in network!";
	}

}
