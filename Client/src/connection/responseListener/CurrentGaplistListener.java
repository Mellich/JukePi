package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class CurrentGaplistListener implements ResponseListener{

	private String name;
	
	public CurrentGaplistListener() {
		name = new String();
	}
	
	@Override
	public void onResponse(String[] response) {
		name = response[0];
	}

	public String getName() {
		return name;
	}
}
