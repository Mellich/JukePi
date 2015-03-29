package client.listener;

/**
 *	Handles a Response, sent by the Server. 
 * @author Mellich
 * @version 1.0
 */
public interface ResponseListener {
	
	/**
	 * The Method, that will be called, whenever a Response is available.
	 * @param response	The Response as an Array of Strings.
	 * @since 1.0
	 */
	public void onResponse(String[] response);
}
