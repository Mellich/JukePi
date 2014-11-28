package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class GaplistSwitchListener implements ResponseListener{

	private boolean switched;
	
	public GaplistSwitchListener() {
		this.switched = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
				switched = true;
		else 
			switched = false;
	}
	
	public boolean getSwitched() {
		return switched;
	}

}
