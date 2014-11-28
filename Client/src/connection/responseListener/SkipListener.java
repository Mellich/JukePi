package connection.responseListener;

import javax.swing.JLabel;

import threads.ShowLabelThread;
import clientinterface.listener.ResponseListener;

public class SkipListener implements ResponseListener{

	private JLabel fail;
	private ShowLabelThread ct;
	
	public SkipListener(JLabel fail, ShowLabelThread ct) {
		this.fail = fail;
		this.ct = ct;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			fail.setText("Skipped Track successfully.");
		else
			fail.setText("Couldn't skip Track");
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		ct.start();
	}

}
