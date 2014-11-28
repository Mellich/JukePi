package connection.responseListener;

import javax.swing.JLabel;

import threads.ShowLabelThread;
import clientinterface.listener.ResponseListener;

public class PlayPauseListener implements ResponseListener{

	private JLabel fail;
	private ShowLabelThread slt;
	private boolean success;
	
	public PlayPauseListener(JLabel fail, ShowLabelThread ct) {
		this.fail = fail;
		this.slt = ct;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			success = true;
		else
			success = false;
	}

	
	public void setMessage(boolean play) {
		if (play)
			if (success)
				fail.setText("Track resumed successfully.");
			else
				fail.setText("Track couldn't be paused.");
		else
			if (success)
				fail.setText("Track paused successfully.");
			else
				fail.setText("Track coudln't be resumed");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		slt.start();
	}
}
