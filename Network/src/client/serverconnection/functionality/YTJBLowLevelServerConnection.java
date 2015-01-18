package client.serverconnection.functionality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import messages.MessageType;
import client.listener.ResponseListener;
import client.serverconnection.ServerConnectionNotifier;

public class YTJBLowLevelServerConnection implements LowLevelServerConnection {
	
	private ServerConnectionNotifier notifyWrapper;
	private Socket socket;
	private String ipAddress;
	private int port;
	private ResponseController responses;
	private Thread inputListener;
	private BufferedWriter output;
	private AliveChecker checker;
	
	public YTJBLowLevelServerConnection(ServerConnectionNotifier notifyWrapper,String ip, int port) {
		this(notifyWrapper,ip,port,0);
	}
	
	public YTJBLowLevelServerConnection(ServerConnectionNotifier notifyWrapper,String ip, int port,int checkIntervall) {
		this.port = port;
		this.ipAddress = ip;
		this.notifyWrapper = notifyWrapper;
		responses = new ResponseControllerImpl();
		checker = new AliveChecker(checkIntervall);
	}

	@Override
	public boolean connect() {
		try {
			socket = new Socket(ipAddress,port);
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.inputListener = new Thread(new InputListener(new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8")),notifyWrapper,responses,checker));
			inputListener.start();
			checker.start();
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
			checker.interrupt();
			socket.close();
			inputListener.join();
			checker.join();
			return true;
		} catch (IOException | InterruptedException e) {
			return false;
		}
	}

	@Override
	public boolean sendMessage(ResponseListener listener, int messageType) {
		responses.addReponseListener(messageType, listener);
		return this.sendMessage(messageType, "");
	}

	@Override
	public boolean sendMessage(ResponseListener listener, int messageType,
		String messageArgument) {
		responses.addReponseListener(messageType, listener);
		return this.sendMessage(messageType, messageArgument);
	}

	@Override
	public boolean sendMessage(int messageType) {
		return this.sendMessage(messageType, "");
	}

	@Override
	public boolean sendMessage(int messageType, String messageArgument) {
		try {
			output.write(""+messageType+MessageType.SEPERATOR+messageArgument);
			output.newLine();
			output.flush();
			return true;
		} catch (IOException e) {
			return false;
		}
	}
	
	public class AliveChecker extends Thread implements ResponseListener{
		
		private int checkIntervall;
		private long lastResponse;
		
		public AliveChecker(int intervall) {
			this.checkIntervall = intervall;
		}
		
		public void setLastResponse(){
			lastResponse = System.currentTimeMillis();
		}

		@Override
		public void run() {
			if (checkIntervall > 0){
				setLastResponse();
				try {
					while(true){
						Thread.sleep(checkIntervall);
						if (lastResponse < System.currentTimeMillis() - checkIntervall){
							if (!sendMessage(this,MessageType.ISREADY,""))
								break;
						}
					}
				} catch (InterruptedException e) {
					System.out.println("CheckThread beendet");
				}
				close();
			}
		}

		@Override
		public void onResponse(String[] response) {
			if (Boolean.parseBoolean(response[0])){
				setLastResponse();
			}
		}
		
	}

	@Override
	public String getIPAddress() {
		if (socket != null)
			return socket.getInetAddress().getHostAddress();
		else return null;
	}

	@Override
	public int getPort() {
		if (socket != null)
			return socket.getPort();
		else return 0;
	}

}
