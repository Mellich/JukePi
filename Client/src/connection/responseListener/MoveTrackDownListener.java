package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class MoveTrackDownListener implements ResponseListener{

	private boolean moved;
	
	public MoveTrackDownListener() {
		moved = false;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			moved = true;
		else
			moved = false;
	}
	
	public boolean getMoved() {
		return moved;
	}

}
