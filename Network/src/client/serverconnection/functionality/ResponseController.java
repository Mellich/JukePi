package client.serverconnection.functionality;

import client.listener.ResponseListener;

/**
 * The Controller for the Responses. 
 * @author Mellich
 * @version 1.0
 */
public interface ResponseController {
	/**
	 * Adds the given ResponseListener for the given messageType to the List of Listeners.
	 * @param messageType	The Type of the Command, the Listener will be listening to.
	 * @param responseListener	The {@link ResponseListener}, that will be added.
	 * @since 1.0
	 */
	public void addReponseListener(int messageType,ResponseListener responseListener);
	
	/**
	 * Returns the ResponseListener for the given messageType.
	 * @param messageType	The Type of the Command, the ResponseListener to be returned is 
	 * listening to.
	 * @return	The {@link ResponseListener}, that is listening to the given messageType.
	 * @since 1.0
	 */
	public ResponseListener getResponseListener(int messageType);
}
