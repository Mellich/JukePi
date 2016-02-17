package util;

import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * <p>A {@link Thread} to display Messages.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #ShowLabelThread(JLabel, JFrame, String)}:
 * 				The Constructor for this Thread. Will set each Field to their Parameter Value.
 * 				</li>
 * 
 * 			<li>{@link #run()}:
 * 				The overridden run-Method for this Thread. Will set the Text of the 
 * 				{@link #fail}-Label to the given {@link #text} and adds it to the 
 * 				{@link #frame}. After a few seconds, the Label will be set invisible, if the 
 * 				Message is still the same on it.</li>
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
 * 			<li>{@link #fail}:
 * 				The {@link JLabel}, that will display the Message.</li>
 * 
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, on which the {@link #fail}-Label will be displayed.</li>
 * 
 * 			<li>{@link #text}:
 * 				The Message, that will be displayed.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class ShowLabelThread extends Thread{

	/**
	 * <p style="margin-left: 10px"><em><b>fail</b></em></p>
	 * <p style="margin-left: 20px">{@code private final JLabel fail}</p>
	 * <p style="margin-left: 20px">The Label, that displays the Message.</p>
	 * @see JLabel
	 */
	private final JLabel fail;
	
	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private final JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame, that contains the Fail-Label.</p>
	 * @see JFrame
	 */
	private final JFrame frame;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>text</b></em></p>
	 * <p style="margin-left: 20px">{@code private final String text}</p>
	 * <p style="margin-left: 20px">The Message to be shown.</p>
	 */
	private final String text;
	
	/**
 	 * <p style="margin-left: 10px"><em><b>ShowLabelThread</b></em></p>
	 * <p style="margin-left: 20px">{@code public ShowLabelThread(JLabel, JFrame, String)}</p>
	 * <p style="margin-left: 20px">The Constructor for the {@link Thread}.</p>
	 * @param fail	The Label, that displays the Message.
	 * @param frame	The Frame, that contains the Fail-Label.
	 * @param text	The Text, that will be displayed in the Label.
	 * @since 1.0
	 */
	public ShowLabelThread(JLabel fail, JFrame frame, String text) {
		this.fail = fail;
		this.frame = frame;
		this.text = text;
		fail.setText(text);
	}
	
	/**
 	 * <p style="margin-left: 10px"><em><b>run</b></em></p>
	 * <p style="margin-left: 20px">{@code public void run()}</p>
	 * <p style="margin-left: 20px">The Method, that will be executed, when starting the 
	 * Thread by calling {@link Thread#start()}. Will show {@link #fail} and makes it 
	 * invisible again after 2 seconds, if the Message, it displays is still the same.</p>
	 * @since 1.0
	 */
	@Override
	public void run() {
		fail.setVerticalAlignment(JLabel.CENTER);
		fail.setHorizontalAlignment(JLabel.CENTER);
		fail.setVisible(true);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
		}
		if (fail.getText().equals(text))
			fail.setVisible(false);
		if (frame != null)
			frame.repaint();
	}
}
