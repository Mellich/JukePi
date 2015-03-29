package client.serverconnection.functionality;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;

import client.listener.ResponseListener;

/**
 * The Implementation of {@link ResponseController}.
 * @author Mellich
 * @version 1.0
 */
public class ResponseControllerImpl implements ResponseController {
	
	/**
	 * A Hashmap of the Message Type and a {@link Deque} of {@link ResponseListener}, that are 
	 * listening to that Message Type.
	 */
	private HashMap<Integer,Deque<ResponseListener>> data = new HashMap<Integer,Deque<ResponseListener>>();

	@Override
	public synchronized void addReponseListener(int messageType,
			ResponseListener responseListener) {
		if (responseListener == null)
			responseListener = new ResponseListener(){

				@Override
				public void onResponse(String[] response) {				
				}
			
		};
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
