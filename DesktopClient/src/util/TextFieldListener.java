package util;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JTextField;

/**
 * <p>The Listener for all TextFields, that will Clear the Field, if one of the given Strings 
 * is contained in it.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #TextFieldListener(String[], JTextField)}:
 * 				The Constructor for the Listener. Will set the given String-Array as Quotes, 
 * 				for which this Listener will empty the Textfield, when they are part of the 
 * 				String, that is the Text of the given {@link JTextField}.</li>
 * 		
 * 			<li>{@link #mouseClicked(MouseEvent)}:
 * 				Will empty the {@link JTextField}, when the given Part-Strings are somewhere 
 * 				in the Text of the TextField, when it is clicked.</li>
 * 
 * 			<li>{@link #mouseEntered(MouseEvent)}:
 * 				Won't do anything, as there is nothing to do in this case.</li>
 * 
 * 			<li>{@link #mouseExited(MouseEvent)}:
 * 				Won't do anything, as there is nothing to do in this case.</li>
 * 
 * 			<li>{@link #mousePressed(MouseEvent)}:
 * 				Won't do anything, as there is nothing to do in this case.</li>
 * 
 * 			<li>{@link #mouseReleased(MouseEvent)}:
 * 				Won't do anything, as there is nothing to do in this case.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
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
 * 			<li>{@link #delete}:
 * 				The Array, that saves the Strings, in which cases, the {@link JTextField} will 
 * 				be emptied, if the displayed Text contains one of these Strings.</li>
 * 
 * 			<li>{@link #tf}:
 * 				The {@link JTextField}, this Listener is added to.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class TextFieldListener implements MouseListener{

	/**
	 * <p style="margin-left: 10px"><em><b>delete</b></em></p>
	 * <p style="margin-left: 20px">{@code private final String[] delete}</p>
	 * <p style="margin-left: 20px">The Array, that will save the Strings, that will be 
	 * deleted, when selecting the TextField.</p>
	 */
	private final String[] delete;
	
	/**
	 * <p style="margin-left: 10px"><em><b>tf</b></em></p>
	 * <p style="margin-left: 20px">{@code private final JTextField tf}</p>
	 * <p style="margin-left: 20px">The TextField, this Listener will be added to.</p>
	 * @see JTextField
	 */
	private final JTextField tf;
	
	/**
	 * <p style="margin-left: 10px"><em><b>TextFieldListener</b></em></p>
	 * <p style="margin-left: 20px">{@code public TextFieldListener(String[], JTextField)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Listener.</p>
	 * @param delete	The Array, that will contain the Strings to be deleted.
	 * @param tf	The TextField, this Listener will be added to.
	 * @since 1.0
	 */
	public TextFieldListener(String[] delete, JTextField tf) {
		this.delete = delete;
		this.tf = tf;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>mouseClicked</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mouseClicked(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Performs the Action, when the Mouse is clicked.</p>
	 * @param arg0	Unused.
	 * @since 1.0
	 */
	public void mouseClicked(MouseEvent arg0) {
		for (String i : delete) {
			if (tf.getText().contains(i)) {
				tf.setText("");
				return;
			}
		}
	}

	/**
	 * <p style="margin-left: 10px"><em><b>mouseEntered</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mouseEntered(MouseEvent()}</p>
	 * <p style="margin-left: 20px">Unused.</p>
	 * @param arg0 Unused.
	 * @since 1.0
	 */
	public void mouseEntered(MouseEvent arg0) {}

	/**
	 * <p style="margin-left: 10px"><em><b>mouseExited</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mouseExited(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Unused.</p>
	 * @param arg0 Unused.
	 * @since 1.0
	 */
	public void mouseExited(MouseEvent arg0) {}

	/**
	 * <p style="margin-left: 10px"><em><b>mousePressed</b></em></p>
	 * <p style="margin-left: 20px">{@code public void mousePressed(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Unused.</p>
	 * @param arg0 Unused.
	 * @since 1.0
	 */
	public void mousePressed(MouseEvent arg0) {}

	/**
	 * <p style="margin-left: 10px"><em><b>mouseReleased</b></em></p>
	 * <p style="margin-left: 20px">{@code public void MouseReleased(MouseEvent)}</p>
	 * <p style="margin-left: 20px">Unused.</p>
	 * @param arg0 Unused.
	 * @since 1.0
	 */
	public void mouseReleased(MouseEvent arg0) {}

}