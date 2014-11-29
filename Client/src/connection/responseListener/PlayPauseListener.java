package connection.responseListener;

import javax.swing.JLabel;

import threads.ShowLabelThread;
import clientinterface.listener.ResponseListener;

public class PlayPauseListener implements ResponseListener{

	private boolean success;
	
	public PlayPauseListener() {
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
