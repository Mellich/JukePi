package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class GetCurrentTrackListener implements ResponseListener{

	private String name;
	
	public GetCurrentTrackListener() {
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
