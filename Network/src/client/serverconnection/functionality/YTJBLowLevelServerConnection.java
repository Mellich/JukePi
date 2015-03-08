package client.serverconnection.functionality;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.Semaphore;
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
	
	private long getMACAddress(){
		InetAddress ip;
		try {
			ip = this.getLocalIPAddress();			 
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress(); 
			long value = 0;
			for (int i = 0; i < mac.length; i++)
			{
			   value += ((long) mac[i] & 0xffL) << (8 * i);
			}
			return value;
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1L;
	}
	
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
			this.sendMessage(MessageType.REGISTERCLIENT,""+getMACAddress());
			return true;
		} catch (IOException | NullPointerException e) {
			System.out.println("Fehler beim Verbinden!");
			e.printStackTrace();
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
		} catch (IOException | NullPointerException e) {
			return false;
		}
	}
	
	@Override
	public String[] sendBlockingMessage(int messageType) {
		return this.sendBlockingMessage(messageType, "");
	}

	@Override
	public String[] sendBlockingMessage(int messageType, String messageArgument) {
		Semaphore blockMutex = new Semaphore(0);
		RequestResult result = new RequestResult();
		responses.addReponseListener(messageType, (String[] s) -> {result.setResult(s);blockMutex.release(); });
		this.sendMessage(messageType, messageArgument);
		try {
			blockMutex.acquire();
			return result.getResult();
		} catch (InterruptedException e) {
			return null;
		}
	}

	@Override
	public String getIPAddress() {
		if (socket != null)
			return socket.getInetAddress().getHostAddress();
		else return null;
	}
	
	private InetAddress getLocalIPAddress() { 
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()&&inetAddress instanceof Inet4Address) {
                        return inetAddress;
                    }
                }
            }
        } catch (SocketException ex) {
        }
        return null; 
	}

	@Override
	public int getPort() {
		if (socket != null)
			return socket.getPort();
		else return 0;
	}
	
	protected class AliveChecker extends Thread implements ResponseListener{
		
		private int checkIntervall;
		private long lastResponse;
		
		private volatile boolean answerFromIsReady = true;
		
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
				answerFromIsReady = true;
				try {
					while(true){
						Thread.sleep(checkIntervall);
						if (!answerFromIsReady)
							break;
						answerFromIsReady = false;
						if (lastResponse < System.currentTimeMillis() - checkIntervall){
							if (!sendMessage(this,MessageType.ISREADY)){
								break;
							}
						}else{
							answerFromIsReady = true;
						}
					}
				} catch (InterruptedException e) {
				}
				close();
			}
		}

		@Override
		public void onResponse(String[] response) {
			if (Boolean.parseBoolean(response[0])){
				setLastResponse();
				answerFromIsReady = true;
			}
		}
		
	}
	
	private class RequestResult{
		private volatile String[] result = null;
		public void setResult(String[] s){
			result = s;
		}
		public String[] getResult(){
			return result;
		}
	}

}
