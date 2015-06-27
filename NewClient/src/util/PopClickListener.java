package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

public class PopClickListener extends MouseAdapter {
	
	JTextField txtLink;
	public PopClickListener(JTextField txtLink) {
		this.txtLink = txtLink;
	}
	
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

    private void doPop(MouseEvent e){
        PopUpMenu menu = new PopUpMenu(txtLink);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}