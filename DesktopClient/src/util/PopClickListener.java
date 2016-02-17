package util;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTextField;

/**
 * <p>A {@link MouseAdapter}, that will open the Context-Menu on the TextFields.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b> 
 * 		<ul>
 * 			<li>{@link #PopClickListener(JTextField)}:
 * 				The Constructor for this Adapter. It will simply put the given {@link 
 * 				JTextField} as Value for the Field {@link #txtLink}.</li>
 * 
 * 			<li>{@link #mousePressed(MouseEvent)}:
 * 				This Method will be called, when a click was performed on the TextField, this 
 * 				Adapter listens to. If it was the PopUp-Trigger, the Context menu will be 
 * 				opened.</li>
 * 
 * 			<li>{@link #mouseReleased(MouseEvent)}:
 * 				This Method will be called, when a click was released on the TextField, this
 * 				Adapter listens to. If it was the PopUp-Triger, the Context menu will be 
 * 				opened.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #doPop(MouseEvent)}:
 * 				Opens a new Menu at the Position of the Mouse.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * 
 * <h3>Fields:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #txtLink}:
 * 				The {@link JTextField}, this Adapter is added to.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class PopClickListener extends MouseAdapter {
	
	/**
	 * <p style="margin-left: 10px"><em><b>txtLink</b></em></p>
	 * <p style="margin-left: 20px">{@code private JTextField txtLink}</p>
	 * <p style="margin-left: 20px">The TextField, that is connected to this Adapter.</p>
	 */
	private JTextField txtLink;
	
	/**
	 * <p style="margin-left: 10px"><em><b>PopClickListener</b></em></p>
	 * <p style="margin-left: 20px">{@code public PopClickListener(JTextField)}</p>
	 * <p style="margin-left: 20px">The Constructor for this Adapter. Adds the Adapter to the 
	 * given {@link JTextField}</p>
	 * @param txtLink	The TextField for this Adapter.
	 * @since 1.0
	 */
	public PopClickListener(JTextField txtLink) {
		this.txtLink = txtLink;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>mousePressed</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mousePressed(MouseEvent)}</p>
	 * <p style="margin-left: 20px">This Method is called, if the Mouse was pressed on 
	 * {@link #txtLink}. If {@link MouseEvent#isPopupTrigger()} is {@code true}, this Method 
	 * will open the Context-Menu by calling {@link #doPop(MouseEvent)}.</p>
	 * @param e	The {@link MouseEvent}, that is used to check, if the 
	 * 			{@link MouseEvent#isPopupTrigger()}-Method was called.
	 * @since 1.0
	 */
	@Override
    public void mousePressed(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }

	/**
	 * <p style="margin-left: 10px"><em><b>mouseReleased</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mouseReleased(MouseEvent)}</p>
	 * <p style="margin-left: 20px">This Method is called, if the Mouse was released on 
	 * {@link #txtLink}. If {@link MouseEvent#isPopupTrigger()} is {@code true}, this Method 
	 * will open the Context-Menu by calling {@link #doPop(MouseEvent)}.</p>
	 * @param e	The {@link MouseEvent}, that is used to check, if the 
	 * 			{@link MouseEvent#isPopupTrigger()}-Method was called.
	 * @since 1.0
	 */
	@Override
    public void mouseReleased(MouseEvent e){
        if (e.isPopupTrigger())
            doPop(e);
    }
    
    /**
     * <p style="margin-left: 10px"><em><b>doPop</b></em></p>
     * <p style="margin-left: 20px">{@code private void doPop(MouseEvent}</p>
     * <p style="margin-left: 20px">Pops the Menu at the Position of the Mouse, if the Result 
     * of {@link MouseEvent#getComponent()} is the TextField for this Adapter.</p>
     * @param e		The {@link MouseEvent}, that triggered this Adapter. Used to retrieve the
     * 				Component, this Event is fired on and for the Position of the Mouse.
     * @since 1.0
     */
    private void doPop(MouseEvent e){
        PopUpMenu menu = new PopUpMenu(txtLink);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }
}