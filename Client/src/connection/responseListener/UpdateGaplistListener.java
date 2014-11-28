package connection.responseListener;

import java.util.LinkedList;

import clientinterface.listener.ResponseListener;

public class UpdateGaplistListener implements ResponseListener{

	private LinkedList<String> gaplist;
	
	public UpdateGaplistListener() {
		gaplist = new LinkedList<String>();
	}
	
	@Override
	public void onResponse(String[] response) {
		gaplist.clear();
		for (String s : response)
			gaplist.add(s);
	}

	public LinkedList<String> getGaplist() {
		return gaplist;
	}
}
