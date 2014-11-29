package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class SkipListener implements ResponseListener{

	private boolean skipped;
	
	public SkipListener() {
		skipped = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			skipped = true;
		else
			skipped = false;
	}

	public boolean getSkipped() {
		return skipped;
	}
}
