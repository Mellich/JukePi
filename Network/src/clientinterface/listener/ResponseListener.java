package clientinterface.listener;

/**
 * 
 * @author Marius Meyer
 *
 *Handles a response sent by the server
 */
public interface ResponseListener {
	public void onResponse(String[] response);
}
