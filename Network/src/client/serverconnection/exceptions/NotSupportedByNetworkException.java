package client.serverconnection.exceptions;

public class NotSupportedByNetworkException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2767700205045465387L;
	private long workVersion;
	private long neededVersion;
	
	public NotSupportedByNetworkException(long workVersion,long neededVersion) {
		this.workVersion = workVersion;
		this.neededVersion = neededVersion;
	}
	
	@Override
	public String getMessage() {
		return "The called Function is not supported by the used network version! current version="+workVersion+", needed version="+neededVersion;
	}

}
