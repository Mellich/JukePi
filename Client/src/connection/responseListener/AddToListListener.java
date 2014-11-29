package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class AddToListListener implements ResponseListener{

	private boolean added;
	
	public AddToListListener() {
		added = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			added = true;
		else
			added = false;
	}
	
	public boolean getAdded() {
		return added;
	}
}
