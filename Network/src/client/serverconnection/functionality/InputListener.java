package client.serverconnection.functionality;

import java.io.BufferedReader;
import java.io.IOException;

import messages.MessageType;
import client.serverconnection.ServerConnectionNotifier;
import client.serverconnection.functionality.YTJBLowLevelServerConnection.AliveChecker;
import client.serverconnection.functionality.handler.NotificationHandler;
import client.serverconnection.functionality.handler.ResponseHandler;

/**
 * The Listener for the Input.
 * @author Mellich
 * @version 1.0
 */
public class InputListener implements Runnable {

	/**
	 * Determines, if the Listener is running. Will be {@code true} at any time.
	 */
	private boolean running = true;
	
	/**
	 * The {@link BufferedReader}, that delivers the Input.
	 */
	private BufferedReader input;
	
	/**
	 * The Wrapper that will be added to the {@link NotificationHandler}.
	 */
	private ServerConnectionNotifier notifyWrapper;
	
	/**
	 * The Controller, that will be added to the {@link ResponseHandler}.
	 */
	private ResponseController responses;
	
	/**
	 * The Checker, that will check, if the Connection is still alive.
	 */
	private AliveChecker checker;
	
	/**
	 * Creates a new Listener for the Input.
	 * @param input	The {@link BufferedReader} for the InputStream.
	 * @param notifyWrapper	The Wrapper, that will be added to the {@link NotificationHandler}.
	 * @param responses	The Controller, that will be added to the {@link ResponseHandler}.
	 * @param checker	The Checker, that will check, if the Connection is still established.
	 * @since 1.0
	 */
	public InputListener(BufferedReader input, ServerConnectionNotifier notifyWrapper,ResponseController responses, AliveChecker checker) {
		this.input = input;
		this.notifyWrapper = notifyWrapper;
		this.responses = responses;
		this.checker = checker;
	}
	
	@Override
	public void run() {
		try {
			while (running){
				String line = input.readLine();
				if (!line.equals("")){
					checker.setLastResponse();
					if (Thread.interrupted())
						break;
					String[] params = line.split(MessageType.SEPERATOR);
					int messageType = Integer.parseInt(params[0]);
					if (messageType == MessageType.RESPONSENOTIFY){
						Thread t = new Thread(new ResponseHandler(responses,params));
						t.start();
					}else{
						Thread t = new Thread(new NotificationHandler(notifyWrapper,messageType,params));
						t.start();					
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			notifyWrapper.onNotify(MessageType.DISCONNECT,null);
		}
	}

}
