package client.listener;

/**
 *	Handles a response sent by the server 
 * @author Marius Meyer
 */
public interface ResponseListener {
	public void onResponse(String[] response);
}
