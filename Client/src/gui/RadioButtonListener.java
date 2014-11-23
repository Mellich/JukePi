package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JRadioButton;

/**
 * The ActionListener for the RadioButtons.
 * @author Haeldeus
 *
 */
public class RadioButtonListener implements ActionListener{

	/**
	 * The RadioButton, this ActionListener will work for.
	 */
	JRadioButton b1;
	
	/**
	 * The other RadioButton, that has to be controlled.
	 */
	JRadioButton b2;
	
	/**
	 * The Constructor for the ActionListener
	 * @param thisButton	The Button, this ActionListener will work for.
	 * @param OtherButton	The other Button.
	 */
	public RadioButtonListener(JRadioButton thisButton, JRadioButton OtherButton) {
		this.b1 = thisButton;
		this.b2 = OtherButton;
	}
	
	/**
	 * Performs the Action.
	 * @param arg0 Just a stub.
	 */
	public void actionPerformed(ActionEvent arg0) {
		b2.setSelected(false);
		if(!b1.isSelected()) {
			b1.setSelected(true);
		}
	}

}
