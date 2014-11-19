package clientinterface;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import messages.MessageType;
import clientinterface.handler.NotificationHandler;
import clientinterface.handler.ResponseHandler;
import clientinterface.listener.NotifyListener;

public class InputListener implements Runnable {

	private boolean running = true;
	private BufferedReader input;
	private List<NotifyListener> notifyListener;
	private ResponseController responses;
	
	public InputListener(BufferedReader input, List<NotifyListener> notifyListener,ResponseController responses) {
		this.input = input;
		this.notifyListener = notifyListener;
		this.responses = responses;
	}
	
	@Override
	public void run() {
		try {
			while (running){
				String line = input.readLine();
				String[] params = line.split(MessageType.SEPERATOR);
				int messageType = Integer.parseInt(params[0]);
				if (messageType == MessageType.RESPONSENOTIFY){
					Thread t = new Thread(new ResponseHandler(responses,params));
					t.start();
				}else{
					Thread t = new Thread(new NotificationHandler(notifyListener,messageType));
					t.start();					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
