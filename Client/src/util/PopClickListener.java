package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

/**
 * A {@link MouseAdapter}, that will open the Rightclick-Menu on the TextFields.
 * @author Haeldeus
 * @version 1.0
 */
public class PopClickListener extends MouseAdapter {
	
	/**
	 * The TextField, that is connected to this Adapter.
	 */
	private JTextField txtLink;
	
	/**
	 * The Constructor for all Adapters.
	 * @param txtLink	The TextField for this Adapter.
	 * @since 1.0
	 */
	public PopClickListener(JTextField txtLink) {
		this.txtLink = txtLink;
	}
	
	@Override
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

	@Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }
    
    /**
     * Pops the Menu at the Position of the Mouse, if the Result of 
     * {@link MouseEvent#getComponent()} is the TextField for this Adapter.
     * @param e		The {@link MouseEvent}, that triggered this Adapter. Used to retrieve the
     * 				Component, this Event is fired on and the Position of the Mouse.
     * @since 1.0
     */
    private void doPop(MouseEvent e){
        PopUpMenu menu = new PopUpMenu(txtLink);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}