package connection.responseListener;

import java.util.LinkedList;

import clientinterface.listener.ResponseListener;

public class AvailableGaplistListener implements ResponseListener{

	private LinkedList<String> gaplists;
	
	public AvailableGaplistListener() {
		gaplists = new LinkedList<String>();
	}
	
	@Override
	public void onResponse(String[] response) {
		gaplists.clear();
		for (String s : response)
			gaplists.add(s);
	}

	public LinkedList<String> getGaplists() {
		return gaplists;
	}
}
