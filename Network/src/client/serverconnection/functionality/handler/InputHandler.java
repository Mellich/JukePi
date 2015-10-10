package client.serverconnection.functionality.handler;

/**
 * Handles the input from the socket and will be executed by the execute thread
 * 
 * @author mellich
 *
 */
public interface InputHandler {
	
	/**
	 * executes the input
	 */
	public void execute();
}
