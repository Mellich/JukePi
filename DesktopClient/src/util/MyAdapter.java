package util;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import windows.AckWindow;
import windows.MainWindow;
import client.serverconnection.ServerConnection;

/**
 * <p>The {@link WindowAdapter}, that will let the User save the Changes, before he closes the 
 * Client.</p>
 * 
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #MyAdapter(MainWindow, ServerConnection)}:
 * 				The Constructor for this Adapter. Will set the given Parameter to their 
 * 				belonging Instance-Variables.</li>
 * 			
 * 			<li>{@link #windowClosing(WindowEvent)}:
 * 				This Method will be called, whenever the Window should be closed by pressing 
 * 				the X-Button. If a change to the Gaplist was made and not yet saved, it will 
 * 				open a new {@link AckWindow}.</li>
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
 * 			<li>{@link #mainWindow}:
 * 				The {@link MainWindow}, this Adapter was added to.</li>
 * 
 * 			<li>{@link #wrapper}:
 * 				The {@link ServerConnection}, that will be given as Parameter to the new 
 * 				{@link AckWindow} to send the Messages to the Server.</li>
 * 		</ul>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class MyAdapter extends WindowAdapter {
	
	/**
	 * <p style="margin-left: 10px"><em><b>wrapper</b></em></p>
	 * <p style="margin-left: 20px">{@code private ServerConnection wrapper}</p>
	 * <p style="margin-left: 20px">The {@link ServerConnection} to the Server. This will send 
	 * the Save-Message to the Server, if the User clicks "Save" in the {@link AckWindow}.</p>
	 */
	private ServerConnection wrapper;
	
	/**
	 * <p style="margin-left: 10px"><em><b>mainWindow</b></em></p>
	 * <p style="margin-left: 20px">{@code private MainWindow mainWindow}</p>
	 * <p style="margin-left: 20px">The {@link MainWindow}, this Adapter is connected to. It 
	 * will be closed, after calling this Adapter either way, except when the User clicks 
	 * "Cancel" on the {@link AckWindow}.</p>
	 */
	private MainWindow mainWindow;
	
	/**
	 * <p style="margin-left: 10px"><em><b>MyAdapter</b></em></p>
	 * <p style="margin-left: 20px">{@code public MyAdapter(MainWindow, ServerConnection)}</p>
	 * <p style="margin-left: 20px">The Constructor for the Adapter. Here, the given values 
	 * for the {@link MainWindow} and {@link ServerConnection} will be set to the 
	 * Class-Variables.</p>
	 * @param mw	The {@link MainWindow}, this Adapter will be added to.
	 * @param wrapper	The {@link ServerConnection} to the Server.
	 * @since 1.0
	 */
	public MyAdapter(MainWindow mw, ServerConnection wrapper) {
		this.mainWindow = mw;
		this.wrapper = wrapper;
	}
	
	/**
	 * <p style="margin-left: 10px"><em><b>windowClosing</b></em></p>
	 * <p style="margin-left: 20px">{@code public void windowClosing(WindowEvent)}</p>
	 * <p style="margin-left: 20px">This Method will be called, whenever the Window should be 
	 * closed by pressing the X-Button. If a change to the Gaplist was made and not yet saved, 
	 * it will open a new {@link AckWindow}. Else, the Window will be closed as usually.</p>
	 * @param windowEvent	Unused.
	 * @since 1.0
	 */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
    	if (mainWindow.getChanged())
    		new AckWindow(wrapper, mainWindow, "CLOSE", mainWindow).show();
    	else
    		System.exit(0);
    }
}