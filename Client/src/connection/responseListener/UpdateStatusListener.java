package connection.responseListener;

import javax.swing.JButton;

import clientinterface.listener.ResponseListener;

public class UpdateStatusListener implements ResponseListener{

	private JButton playButton;
	private boolean playing;
	
	public UpdateStatusListener(JButton playButton) {
		this.playButton = playButton;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true")) {
			playButton.setText("Pause");
			playButton.setToolTipText("Click here to pause the current track");
			playing = true;
		}
		else {
			playButton.setText("Play");
			playButton.setToolTipText("Click here to play the current track");
			playing = false;
		}
	}
	
	public boolean getPlaying() {
		return playing;
	}
}
