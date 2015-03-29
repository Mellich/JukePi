package client.serverconnection.functionality;

import client.listener.ResponseListener;

/**
 * A ServerConnection on the lowest possible level.
 * @author Mellich
 * @version 1.0
 */
public interface LowLevelServerConnection {
	/**
	 * Connects to the Server.
	 * @return	{@code true}, if the Connection was established, {@code false} else.
	 * @since 1.0
	 */
	public boolean connect();
	
	/**
	 * Closes the current Connection.
	 * @return	{@code true}, if the Connection was closed successfully, {@code false} else.
	 * @since 1.0
	 */
	public boolean close();
	
	/**
	 * Gets the IP of the Server, the Connection was established to.
	 * @return	The IP of the Server.
	 * @since 1.0
	 */
	public String getIPAddress();
	
	/**
	 * Gets the Port of the Server, the Connection was established to.
	 * @return	The Port of the Server.
	 * @since 1.0
	 */
	public int getPort();
	
	/**
	 * Sends a Message with the given Parameters.
	 * @param listener	The {@link ResponseListener}, that will react to the answer of the 
	 * Server.
	 * @param messageType	The Type of the Command.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public boolean sendMessage(ResponseListener listener,int messageType);
	
	/**
	 * Sends a Message with the given Parameters.
	 * @param listener	The {@link ResponseListener}, that will react to the answer of the
	 * Server.
	 * @param messageType	The Type of the Command.
	 * @param messageArgument	Arguments, that will be sent along with the MessageType.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public boolean sendMessage(ResponseListener listener,int messageType,String messageArgument);
	
	/**
	 * Sends a Message with the given Parameters.
	 * @param messageType	The Type of the Command.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public boolean sendMessage(int messageType);
	
	/**
	 * Sends a Message with the given Parameters.
	 * @param messageType	The Type of the Command.
	 * @param messageArgument	Arguments, that will be sent along with the MessageType.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public boolean sendMessage(int messageType,String messageArgument);
	
	/**
	 * Sends a Message with the given Parameters, that will block the Server.
	 * @param messageType 	The Type of the Command.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public String[] sendBlockingMessage(int messageType);
	
	/**
	 * Sends a Message with the given Parameters, that will block the Server.
	 * @param messageType	The Type of the Command.
	 * @param messageArgument	Arguments, that will be sent along with the MessageType.
	 * @return	{@code true}, if the Message was sent successfully, {@code false} else.
	 * @since 1.0
	 */
	public String[] sendBlockingMessage(int messageType,String messageArgument);
}
