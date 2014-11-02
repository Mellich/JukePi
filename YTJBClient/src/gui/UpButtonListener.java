package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JList;

public class UpButtonListener implements ActionListener{

	JList<String> list;
	
	public UpButtonListener(JList<String> list, int buttonNo) {
		this.list = list;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	//	int i = list.getSelectedIndex();
		// ...
	}

}
