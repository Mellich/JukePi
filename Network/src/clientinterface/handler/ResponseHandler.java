package clientinterface.handler;

import clientinterface.ResponseController;
import clientinterface.listener.ResponseListener;

public class ResponseHandler implements Runnable {
	
	private ResponseController responses ;
	private String[] message;

	public ResponseHandler(ResponseController responses,String[] message ) {
		this.responses = responses;
		this.message = message;
	}
	
	@Override
	public void run() {
		int messageType = Integer.parseInt(message[1]);
		ResponseListener currentResponseListener;
		synchronized(responses){
			currentResponseListener = responses.getResponseListener(messageType);
		}
		currentResponseListener.handleResponse(message[2]);
	}

}
