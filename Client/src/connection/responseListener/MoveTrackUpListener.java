package connection.responseListener;

import clientinterface.listener.ResponseListener;

public class MoveTrackUpListener implements ResponseListener{

	private boolean moved;
	
	public MoveTrackUpListener() {
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
