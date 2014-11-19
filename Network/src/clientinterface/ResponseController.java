package clientinterface;

import clientinterface.listener.ResponseListener;

public interface ResponseController {
	public void addReponseListener(int messageType,ResponseListener responseListener);
	public ResponseListener getResponseListener(int messageType);
}
