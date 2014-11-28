package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class DeleteTrackListener implements ResponseListener{

	private boolean deleted;
	
	public DeleteTrackListener() {
		deleted = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			deleted = true;
		else
			deleted = false;
	}
	
	public boolean getDeleted() {
		return deleted;
	}
}
