package client.serverconnection;

public interface ServerConnectionNotifier {
	public void onNotify(int notifyType,String[] args);
}
