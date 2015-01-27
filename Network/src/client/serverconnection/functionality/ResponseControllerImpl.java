package client.serverconnection.functionality;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import client.listener.ResponseListener;

public class ResponseControllerImpl implements ResponseController {
	
	private HashMap<Integer,Deque<ResponseListener>> data = new HashMap<Integer,Deque<ResponseListener>>();

	@Override
	public synchronized void addReponseListener(int messageType,
			ResponseListener responseListener) {
		if (responseListener == null)
			responseListener = (String[] s) -> {};
		if (data.containsKey(messageType)){
			Deque<ResponseListener> temp = data.get(messageType);
			temp.addLast(responseListener);
		}
		else{
			ArrayDeque<ResponseListener> temp = new ArrayDeque<ResponseListener>();
			temp.addLast(responseListener);
			data.put(messageType, temp);
		}
	}

	@Override
	public synchronized ResponseListener getResponseListener(int messageType) {
		if (data.containsKey(messageType)){
			Deque<ResponseListener> temp = data.get(messageType);
			if (!temp.isEmpty())
				return temp.poll();
			else return null;
		}
		else {
			System.out.println("MessageType nicht gefunden!");
			return null;
		}
	}

}
