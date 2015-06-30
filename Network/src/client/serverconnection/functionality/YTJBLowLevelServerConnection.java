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

/**
 * Implements the {@link LowLevelServerConnection} for the YouTubeJukeBox.
 * @author Mellich
 * @version 1.0
 */
public class YTJBLowLevelServerConnection implements LowLevelServerConnection {
	
	/**
	 * The Notifier, that will be added to the {@link #inputListener}.
	 */
	private ServerConnectionNotifier notifyWrapper;
	
	private long serverVersion = 0L;
	
	private long version = 815L;
	
	/**
	 * The {@link Socket} to the Server.
	 */
	private Socket socket;
	
	/**
	 * The IP of the Server.
	 */
	private String ipAddress;
	
	/**
	 * The Port of the Server.
	 */
	private int port;
	
	/**
	 * The Controller for the Responses.
	 */
	private ResponseController responses;
	
	/**
	 * The Listener for the Input.
	 */
	private Thread inputListener;
	
	/**
	 * The {@link BufferedWriter} for the Output.
	 */
	private BufferedWriter output;
	
	/**
	 * The Checker, that will check, if the Connection is still alive.
	 */
	private AliveChecker checker;
	
	/**
	 * Determines, if the Client is an Android Application.
	 */
	private boolean isAndroid;
	
	/**
	 * Gets the MAC-Address of the Client.
	 * @return	The MAC-Address of the Client.
	 * @since 1.0
	 */
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
	
	/**
	 * Creates a new ServerConnection.
	 * @param notifyWrapper	The Notifier.
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @since 1.0
	 */
	public YTJBLowLevelServerConnection(ServerConnectionNotifier notifyWrapper,String ip, int port) {
		this(notifyWrapper,ip,port,0,false);
	}
	
	/**
	 * Creates a new ServerConnection.
	 * @param notifyWrapper	The Notifier.
	 * @param ip	The IP of the Server.
	 * @param port	The Port of the Server.
	 * @param checkInterval	The Interval, to check, if the Connection is still alive.
	 * @param isAndroid	Determines, if the Client is an Android Application.
	 * @since 1.0
	 */
	public YTJBLowLevelServerConnection(ServerConnectionNotifier notifyWrapper,String ip, int port,int checkInterval, boolean isAndroid) {
		this.port = port;
		this.ipAddress = ip;
		this.isAndroid =isAndroid;
		this.notifyWrapper = notifyWrapper;
		responses = new ResponseControllerImpl();
		checker = new AliveChecker(checkInterval);
	}

	@Override
	public boolean connect() {
		try {
			socket = new Socket(ipAddress,port);
			output = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			this.inputListener = new Thread(new InputListener(new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8")),notifyWrapper,responses,checker));
			inputListener.start();
			checker.start();
			String[] response = this.sendBlockingMessage(MessageType.REGISTERCLIENT,""+getMACAddress()+MessageType.SEPERATOR+version);
			serverVersion = Long.parseLong(response[0]);
			return true;
		} catch (IOException | NumberFormatException | NullPointerException e) {
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
	public boolean sendMessage(final int messageType, final String messageArgument) {
		if (isAndroid){
			new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						output.write(""+messageType+MessageType.SEPERATOR+messageArgument);
						output.newLine();
						output.flush();
					} catch (IOException | NullPointerException e) {
						e.printStackTrace();
					}
				}
				
			}).start();
			return true;
		}else{
			try {
				output.write(""+messageType+MessageType.SEPERATOR+messageArgument);
				output.newLine();
				output.flush();
				return true;
			} catch (IOException | NullPointerException e) {
				return false;
			}	
		}
	}
	
	@Override
	public String[] sendBlockingMessage(int messageType) {
		return this.sendBlockingMessage(messageType, "");
	}

	@Override
	public String[] sendBlockingMessage(int messageType, String messageArgument) {
		final Semaphore blockMutex = new Semaphore(0);
		final RequestResult result = new RequestResult();
		responses.addReponseListener(messageType, new ResponseListener(){
										@Override
										public void onResponse(String[] s){
											result.setResult(s);
											blockMutex.release(); 
											}
										});
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

	@Override
	public int getPort() {
		if (socket != null)
			return socket.getPort();
		else return 0;
	}
	
	/**
	 * A Checker, that will check, if the Connection is still alive.
	 * @author Mellich
	 * @version 1.0
	 */
	protected class AliveChecker extends Thread implements ResponseListener{
		
		/**
		 * The time between each check, if the Connection is still alive.
		 */
		private int checkInterval;
		
		/**
		 * The moment, when the last response was sent by the Server.
		 */
		private long lastResponse;
		
		/**
		 * Determines, if an answer was sent in time.
		 */
		private volatile boolean answerFromIsReady = true;
		
		/**
		 * Creates a new Checker with the given interval.
		 * @param interval The interval between each check.
		 * @since 1.0
		 */
		public AliveChecker(int interval) {
			this.checkInterval = interval;
		}
		
		/**
		 * Sets {@link #lastResponse} to the current System-Time.
		 * @since 1.0
		 */
		public void setLastResponse(){
			lastResponse = System.currentTimeMillis();
		}

		@Override
		public void run() {
			if (checkInterval > 0){
				setLastResponse();
				answerFromIsReady = true;
				try {
					while(true){
						Thread.sleep(checkInterval);
						if (!answerFromIsReady)
							break;
						answerFromIsReady = false;
						if (lastResponse < System.currentTimeMillis() - checkInterval){
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
	
	/**
	 * A Class, that saves all results of requests.
	 * @author Mellich
	 * @version 1.0
	 */
	private class RequestResult{
		/**
		 * An Array of Strings with all results.
		 */
		private volatile String[] result = null;
		
		/**
		 * Sets the {@link #result} to the given Array.
		 * @param s	The Array of results.
		 * @since 1.0
		 */
		public void setResult(String[] s){
			result = s;
		}
		
		/**
		 * Returns the {@link #result}.
		 * @return	The result.
		 * @since 1.0
		 */
		public String[] getResult(){
			return result;
		}
	}

	/**
	 * Returns the local IP-Address.
	 * @return	The local IP-Address.
	 * @since 1.0
	 */
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
	public long getServerVersion() {
		return serverVersion;
	}

	@Override
	public long getVersion() {
		return version;
	}

}
