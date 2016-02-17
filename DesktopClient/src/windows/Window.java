package windows;

/**
 * <p>An abstract Class, that is the parent Class of all Windows for the Client UI and 
 * provides basic Methods.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #close()}: 
 * 				Closes The Window and sets it's Frame invisible ({@link 
 * 				javax.swing.JFrame#setVisible(boolean)}) and disabled ({@link 
 * 				javax.swing.JFrame#setEnabled(boolean)}). </li>
 * 	
 * 			<li>{@link #setActive(boolean)}: 
 * 				Sets the Window to the given state ({@link 
 * 				javax.swing.JFrame#setEnabled(boolean)}), either active, if {@code true} was 
 * 				given as parameter, or inactive if {@code false} was the Parameter. </li>
 * 	
 * 			<li>{@link #show()}: 
 * 				Opens the Window and sets it's Frame visible ({@link 
 * 				javax.swing.JFrame#setVisible(boolean)}). </li>
 * 	
 * 			<li>{@link #showFail(String)}: 
 * 				Shows a Message on the Frame with the given String as Text. The Position of 
 * 				this Text varies and on some Windows, it's not shown at all, since there will 
 * 				never be any Messages to be shown on these Windows. </li>
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
 * <h4>Windows, that extends this Class: </h4>
 * <ul>
 * 	<li>{@link AckWindow}: 	
 * 		The Window, that will acknowledge the User, that the Gaplist wasn't yet saved, when he 
 * 		tries to Shut down the Server or change to a new Gaplist. </li>
 * 	
 * 	<li>{@link DebugWindow}: 
 * 		The Window, that displays the Message the Server sent to the Client, using the 
 * 		Network-Interface. </li>
 * 		
 * 	<li>{@link DisplayGaplistsWindow}: 
 * 		The Window, that displays the Gaplists, saved on the Server. Here, the User can load a 
 * 		new Gaplist and remove unwanted Lists. </li>
 * 
 * 	<li>{@link LoginWindow}: 
 * 		The Window, that will be seen first by the User. Here he can choose to connect to an 
 * 		existing Server or create a new Server. </li>
 * 	
 * 	<li>{@link LowClientWindow}: 
 * 		The Window, that will be used, when connected to a Server as a normal Client, without 
 * 		Admin-Permissions. </li> 
 * 	
 * 	<li>{@link MainWindow}:	
 * 		The Window, that will be used by the Admins. This Client has all Permission to 
 * 		manipulate the Server. </li>
 * 		
 * 	<li>{@link NewListWindow}: 	
 * 		This Window is used to create a new Gaplist on the Server. </li>
 * 		
 * 	<li>{@link OptionsWindow}: 
 * 		The Window, where the User can change the Preferences of the Server, e.g. The Language 
 * 		and the Blacklist. </li>
 * 		
 * 	<li>{@link PasswordWindow}: 
 * 		The Window, where the User can enter the Password, to get Admin-Permissions on the 
 * 		Server, he tries to connect to. If he enters the right Password, he will be connected 
 * 		as an Admin-Client ({@link MainWindow}) and if he skips the Entry or fails to enter 
 * 		the right Password multiple times, he will be connected as a standard Client ({@link 
 * 		LowClientWindow}). </li>
 * 		
 * 	<li>{@link SetPasswordWindow}: 
 * 		This Window will be opened, when the User created a new Server. Here he can enter the 
 * 		Passwords needed to connect to the Server as an Admin or as a Player. </li>
 * 	<li>{@link VersionWindow}:
 * 		This Window will be opened, when the User clicks on the MenuItem "About -&gt; Version". It 
 * 		shows the current Version Number and the latest Changes.</li>
 * </ul>
 * @author Haeldeus
 * @version 1.1
 * @see javax.swing.JFrame
 */
public abstract class Window {
	
	/**
	 * <p style="margin-left: 10px"><em><b>showFail</b></em></p>
	 * <p style="margin-left: 20px">{@code public void showFail(String)}</p>
	 * <p style="margin-left: 20px">Shows the Fail-Label with the given Text on the Frame. If 
	 * an empty String or {@code null} is given as Parameter, the Label will be displayed, but 
	 * without any Text.</p>
	 * @param text	The text, that will be displayed.
	 * @since 1.0
	 */
	public abstract void showFail(String text);
	
	/**
	 * <p style="margin-left: 10px"><em><b>show</b></em></p>
	 * <p style="margin-left: 20px">{@code public void show()}</p>
	 * <p style="margin-left: 20px">Sets the Frame visible and enabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	public abstract void show();
	
	/**
	 * <p style="margin-left: 10px"><em><b>close</b></em></p>
	 * <p style="margin-left: 20px">{@code public void close()}</p>
	 * <p style="margin-left: 20px">Sets the Frame invisible and disabled.</p>
	 * @since 1.0
	 * @see javax.swing.JFrame#setEnabled
	 * @see javax.swing.JFrame#setVisible
	 */
	public abstract void close();
	
	/**
	 * <p style="margin-left: 10px"><em><b>setActive</b></em></p>
	 * <p style="margin-left: 20px">{@code public void setActive(boolean)}</p>
	 * <p style="margin-left: 20px">Sets the State of the Window to the given State. Active, 
	 * if {@code state} is {@code true}, inactive if it is {@code false}.</p>
	 * @param state	The new State of the Window; active, if {@code true}, inactive else.
	 * @since 1.1
	 * @see javax.swing.JFrame#setEnabled
	 */
	public abstract void setActive(boolean state);
	
}
