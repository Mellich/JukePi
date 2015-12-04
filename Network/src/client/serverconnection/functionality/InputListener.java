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
	 * the ExectionThread of the network interface
	 */
	private ExecutionThread executor;
	
	/**
	 * Creates a new Listener for the Input.
	 * @param input	The {@link BufferedReader} for the InputStream.
	 * @param notifyWrapper	The Wrapper, that will be added to the {@link NotificationHandler}.
	 * @param responses	The Controller, that will be added to the {@link ResponseHandler}.
	 * @param checker	The Checker, that will check, if the Connection is still established.
	 * @param exe The ExecutionThread of the network interface
	 * @since 1.0
	 */
	public InputListener(BufferedReader input, ServerConnectionNotifier notifyWrapper,ResponseController responses, AliveChecker checker, ExecutionThread exe) {
		this.input = input;
		this.notifyWrapper = notifyWrapper;
		this.responses = responses;
		this.checker = checker;
		this.executor = exe;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()){
				String line = input.readLine();
				if (!line.equals("")){
					checker.setLastResponse();
					String[] params = line.split(MessageType.SEPERATOR);
					int messageType = Integer.parseInt(params[0]);
					if (messageType == MessageType.RESPONSENOTIFY){
						executor.add(new ResponseHandler(responses,params));
					}else{
						executor.add(new NotificationHandler(notifyWrapper,messageType,params));				
					}
				}
			}
		} catch (IOException | NullPointerException e) {
			notifyWrapper.onNotify(MessageType.DISCONNECT,null);
		}
	}

}
