package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class RemoveGaplistListener implements ResponseListener{

	private boolean success;
	
	public RemoveGaplistListener() {
		success = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			success = true;
		else
			success = false;
	}
	
	public boolean getSuccess() {
		return success;
	}

}
