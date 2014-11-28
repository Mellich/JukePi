package connection.responseListener;

import javax.swing.JLabel;

import threads.ShowLabelThread;
import clientinterface.listener.ResponseListener;

public class AddToListListener implements ResponseListener{

	private JLabel fail;
	private ShowLabelThread slt;
	
	public AddToListListener(JLabel fail, ShowLabelThread ct) {
		this.fail = fail;
		this.slt = ct;
	}
	
	@Override
	public void onResponse(String[] response) {
		if (response[0].equals("true"))
			fail.setText("Track was added to the List.");
		else
			fail.setText("Track couldn't be added to the List.");
		
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVerticalAlignment(JLabel.CENTER);
		slt.start();
	}

}
