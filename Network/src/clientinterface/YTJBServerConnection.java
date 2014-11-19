package clientinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import messages.MessageType;
import clientinterface.listener.NotifyListener;
import clientinterface.listener.ResponseListener;

public class YTJBServerConnection implements ServerConnection {
	
	private ArrayList<NotifyListener> notifyListener;
	private Socket socket;
	private String ipAddress;
	private int port;
	private ResponseController responses;
	private Thread inputListener;
	private BufferedWriter output;
	
	public YTJBServerConnection(String ip, int port) {
		this.port = port;
		this.ipAddress = ip;
		this.notifyListener = new ArrayList<NotifyListener>();
		responses = new ResponseControllerImpl();
	}

	@Override
	public void addNotifyListener(NotifyListener listener) {
		notifyListener.add(listener);

	}

	@Override
	public void removeNotifyListener(NotifyListener listener) {
		notifyListener.remove(listener);
	}

	@Override
	public boolean connect() {
		try {
			socket = new Socket(ipAddress,port);
			this.inputListener = new Thread(new InputListener(new BufferedReader(new InputStreamReader(socket.getInputStream())),notifyListener,responses));
			inputListener.start();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean close() {
		try {
			inputListener.interrupt();
			//TODO: auf alle threads warten
			socket.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public void sendMessage(ResponseListener listener, int messageType) {
		try {
			responses.addReponseListener(messageType, listener);
			output.write(""+messageType);
			output.newLine();
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sendMessage(ResponseListener listener, int messageType,
			String messageArgument) {
		try {
			responses.addReponseListener(messageType, listener);
			output.write(""+messageType+MessageType.SEPERATOR+messageArgument);
			output.newLine();
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
