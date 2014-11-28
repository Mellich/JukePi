package clientinterface;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import messages.MessageType;
import clientinterface.listener.ResponseListener;
import clientwrapper.ClientNotifyWrapper;

public class YTJBServerConnection implements ServerConnection {
	
	private ClientNotifyWrapper notifyWrapper;
	private Socket socket;
	private String ipAddress;
	private int port;
	private ResponseController responses;
	private Thread inputListener;
	private BufferedWriter output;
	
	public YTJBServerConnection(ClientNotifyWrapper notifyWrapper,String ip, int port) {
		this.port = port;
		this.ipAddress = ip;
		this.notifyWrapper = notifyWrapper;
		responses = new ResponseControllerImpl();
	}

	@Override
	public boolean connect() {
		try {
			socket = new Socket(ipAddress,port);
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.inputListener = new Thread(new InputListener(new BufferedReader(new InputStreamReader(socket.getInputStream())),notifyWrapper,responses));
			inputListener.start();
			this.sendMessage(MessageType.DECLAREMEASNOTIFY);
			return true;
		} catch (IOException | NullPointerException e) {
			return false;
		}
	}

	@Override
	public boolean close() {
		try {
			inputListener.interrupt();
			socket.close();
			inputListener.join();
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	@Override
	public void sendMessage(ResponseListener listener, int messageType) {
		responses.addReponseListener(messageType, listener);
		this.sendMessage(messageType, "");
	}

	@Override
	public void sendMessage(ResponseListener listener, int messageType,
		String messageArgument) {
		responses.addReponseListener(messageType, listener);
		this.sendMessage(messageType, messageArgument);
	}

	@Override
	public void sendMessage(int messageType) {
		this.sendMessage(messageType, "");
	}

	@Override
	public void sendMessage(int messageType, String messageArgument) {
		try {
			output.write(""+messageType+MessageType.SEPERATOR+messageArgument);
			output.newLine();
			output.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
