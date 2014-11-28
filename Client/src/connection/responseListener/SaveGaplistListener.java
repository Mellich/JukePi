package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class SaveGaplistListener implements ResponseListener{

	private boolean saved;
	
	public SaveGaplistListener() {
		saved = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			saved = true;
		else
			saved = false;
	}

	public boolean getSaved() {
		return saved;
	}
}
