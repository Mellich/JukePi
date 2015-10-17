package client.serverconnection.exceptions;

public class NotConnectedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1494835455363830288L;

	@Override
	public String getMessage() {
		return "No connection to a server established! First connect to a server";
	}
}
