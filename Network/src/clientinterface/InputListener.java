package clientinterface;

import java.io.BufferedReader;
import java.io.IOException;

import messages.MessageType;
import clientinterface.handler.NotificationHandler;
import clientinterface.handler.ResponseHandler;
import clientwrapper.ClientNotifyWrapper;

public class InputListener implements Runnable {

	private boolean running = true;
	private BufferedReader input;
	private ClientNotifyWrapper notifyWrapper;
	private ResponseController responses;
	
	public InputListener(BufferedReader input, ClientNotifyWrapper notifyWrapper,ResponseController responses) {
		this.input = input;
		this.notifyWrapper = notifyWrapper;
		this.responses = responses;
	}
	
	@Override
	public void run() {
		try {
			while (running){
				String line = input.readLine();
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
