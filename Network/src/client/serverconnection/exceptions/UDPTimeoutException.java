package client.serverconnection.exceptions;

import java.io.IOException;

/**
 * The Exception, that will be thrown, when the UDP-Connection times out.
 * @author Mellich
 * @version 1.0
 */
public class UDPTimeoutException extends IOException {

	/**
	 * The Version UID.
	 */
	private static final long serialVersionUID = -1811555544273154772L;
	
	@Override
	public String getMessage() {
		return "A connection via UDP could not be established. No running server found in network!";
	}

}
