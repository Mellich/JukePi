package windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import util.JMultilineLabel;

/**
 * <p>The {@link Window}, that will contain the Login-Screen.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #VersionWindow()}:
 * 				The Constructor for this Window.</li>
 * 
 * 			<li>{@link #close()}:
 * 				Closes the Window by setting it disabled and invisible.</li>
 * 
 * 			<li>{@link #setActive(boolean)}:
 * 				Sets the state of the Window, depending on the given {@code boolean}, either 
 * 				enabled or disabled.</li>
 * 
 * 			<li>{@link #show()}:
 * 				Sets the Window visible and enabled.</li>
 * 
 * 			<li>{@link #showFail(String)}:
 * 				As there are no Messages to be displayed, this Method does nothing.</li>
 * 		</ul>
 * 	</li>
 * 	<li><b>Protected:</b>
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * 	<li><b>Private:</b>
 * 		<ul>
 * 			<li>{@link #constructFrame()}:
 * 				Constructs the Frame and places it's Components on their belonging Spots.</li>
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
 * 			<li>{@link #frame}:
 * 				The {@link JFrame}, that displays this Window.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class VersionWindow extends Window{

	/**
	 * <p style="margin-left: 10px"><em><b>frame</b></em></p>
	 * <p style="margin-left: 20px">{@code private JFrame frame}</p>
	 * <p style="margin-left: 20px">The Frame of the Version-Screen.</p>
	 * @see JFrame
	 */
	private JFrame frame;
	
	/**
	 * <p style="margin-left: 10px"><em><b>VersionWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code public VersionWindow()}</p>
	 * <p style="margin-left: 20px">The Constructor for the VersionWindow. Will instantiate a 
	 * new {@link JFrame} as {@link #frame}.</p>
	 * @since 1.0
	 */
	public VersionWindow() {
		frame = new JFrame();
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Would show the given {@code String} on the Screen, but as 
	 * there are no Messages to be displayed by this Window, this Method does nothing.</p>
	 * @param text	The {@code String}, that would have been displayed
	 * @since 1.0
	 */
	@Override
	public void showFail(String text) {
		// Noting to do here
	}

	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Constructs the {@link #frame} by calling {@link 
	 * #constructFrame()} and setting it visible afterwards by calling {@link 
	 * JFrame#setVisible(boolean)}</p>
	 * @since 1.0
	 */
	@Override
	public void show() {
		constructFrame();
		frame.setVisible(true);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets {@link #frame} invisible and disabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	@Override
	public void close() {
		frame.setVisible(false);
		frame.setEnabled(false);
	}

	/**
	 * <p style="margin-left: 10px"><em><b>setActive</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setActive(boolean)}</p>
	 * <p style="margin-left: 20px">Sets the State of the Window to the given State. Active, 
	 * if {@code state} is {@code true}, inactive if it is {@code false}.</p>
	 * @param state	The new State of the Window; active, if {@code true}, inactive else.
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 */
	@Override
	public void setActive(boolean state) {
		frame.setEnabled(state);
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>constructFrame</b></em></p>
	 * <p style="margin-left: 20px">{@code private void constructFrame()}</p>
	 * <p style="margin-left: 20px">Constructs the Frame and it's components.</p>
	 * @since 1.0
	 */
	private void constructFrame() {
		frame.setSize(new Dimension(200,200));
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		
		JLabel lblVersion = new JLabel("v0.9.3");
		lblVersion.setHorizontalAlignment(JLabel.CENTER);
		content.add(lblVersion, BorderLayout.NORTH);
		
		String text = 	"- KeyListener added" + "\n" +
						"- GaplistsWindow reduced" + "\n" +
						"- Version Window added" +"\n" + 
						"- MenuBar added to LowClient" +"\n" +
						"- Javadoc added for missing Classes" + "\n" +
						"- Javadoc extended";
		
		JMultilineLabel lblNotes = new JMultilineLabel(text);
		JScrollPane scroll = new JScrollPane(lblNotes);
		
		content.add(scroll, BorderLayout.CENTER);
	}
}
