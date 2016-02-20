package start;

import connection.Collector;

/**
 * <p>Starts the Client.</p>
 * <h3>Provided Methods:</h3>
 * <ul> 
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #main(String[])}:
 * 				The Main-Method, that starts a Client by creating a {@link Collector} and 
 * 				calling it's Method {@link Collector#startUp()}</li>
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
 * 		<p style="margin-left: 25px">
 * 			None
 * 		</p>
 * 	</li>
 * </ul>
 * @author Haeldeus
 * @version 1.0
 */
public class ClientStarter {

	/**
	 * <p style="margin-left: 10px"><em><b>main</b></em></p>
	 * <p style="margin-left: 20px">{@code public static void main(String[])}</p>
	 * <p style="margin-left: 20px">The Main-Method to start the Client.</p>
	 * @param args	Not used.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		Collector c = new Collector();
		c.startUp();
	}
}
