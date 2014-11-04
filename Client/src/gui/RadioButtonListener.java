package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

public class RadioButtonListener implements ActionListener{

	JRadioButton b1;
	JRadioButton b2;
	
	public RadioButtonListener(JRadioButton thisButton, JRadioButton OtherButton) {
		this.b1 = thisButton;
		this.b2 = OtherButton;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		b2.setSelected(false);
		if(!b1.isSelected()) {
			b1.setSelected(true);
		}
	}

}
