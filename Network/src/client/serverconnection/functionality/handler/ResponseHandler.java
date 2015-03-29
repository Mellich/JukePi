package client.serverconnection.functionality.handler;

import client.listener.ResponseListener;
import client.serverconnection.functionality.ResponseController;

/**
 * A Handler for Responses.
 * @author Mellich
 * @version 1.0
 */
public class ResponseHandler implements Runnable {
	
	/**
	 * The Controller for Responses.
	 */
	private ResponseController responses;
	
	/**
	 * The message, as an Array of Strings.
	 */
	private String[] message;

	/**
	 * Creates a new Handler.
	 * @param responses	The Controller for Responses.
	 * @param message	The message, as an Array of Strings.
	 * @since 1.0
	 */
	public ResponseHandler(ResponseController responses,String[] message ) {
		this.responses = responses;
		this.message = message;
	}
	
	@Override
	public void run() {
		int messageType = Integer.parseInt(message[1]);
		String[] arguments = new String[message.length - 2];
		for (int i = 2; i < message.length; i++){
			arguments[i - 2] = message[i];
		}
		ResponseListener currentResponseListener;
		synchronized(responses){
			currentResponseListener = responses.getResponseListener(messageType);
		}
		
		currentResponseListener.onResponse(arguments);
	}

}
