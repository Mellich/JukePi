package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

import connection.MessageType;
import connection.Sender;

public class DeleteButtonListener implements ActionListener{

	Sender s = new Sender();
	JList<String> list;
	int buttonNo;
	
	public DeleteButtonListener(JList<String> list, int buttonNo) {
		this.list = list;
		this.buttonNo = buttonNo;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (buttonNo == 1)
			s.sendMessage(MessageType.DELETEFROMGAPLIST, ""+list.getSelectedIndex());
		else if (buttonNo == 2)
			//DELETEFROMWISHLIST needed
			s.sendMessage(1,""+list.getSelectedIndex());
		else
			//have to talk about what happens, if you press up on the tracklist column
			return;
	}

}
