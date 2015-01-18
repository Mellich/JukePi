package client.serverconnection.functionality;

import java.io.BufferedReader;
import java.io.IOException;

import messages.MessageType;
import client.serverconnection.ServerConnectionNotifier;
import client.serverconnection.functionality.YTJBLowLevelServerConnection.AliveChecker;
import client.serverconnection.functionality.handler.NotificationHandler;
import client.serverconnection.functionality.handler.ResponseHandler;

public class InputListener implements Runnable {

	private boolean running = true;
	private BufferedReader input;
	private ServerConnectionNotifier notifyWrapper;
	private ResponseController responses;
	private AliveChecker checker;
	
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
		} catch (IOException | NullPointerException e) {
			notifyWrapper.onNotify(MessageType.DISCONNECT,null);
		}
	}

}
