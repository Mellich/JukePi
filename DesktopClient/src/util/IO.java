package util;

import java.sql.Timestamp;

/**
 * <p>This class will print out Messages, the Server and the Client will add to it, as well 
 * with the Object, that called this Object and a Timestamp, when it was called.</p>
 *
 * <h3>Provided Methods:</h3>
 * <ul>
 * 	<li><b>Public:</b>
 * 		<ul>
 * 			<li>{@link #println(Object, String)}:
 * 				This Method prints the Message, given as a {@code String} Value, together with 
 * 				a Timestamp, the Threadnumber, that called this Method and the Classname of the 
 * 				given {@code Object} into the Console.</li>
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
public class IO {

	/**
	 * <p style="margin-left: 10px"><em><b>println</b></em></p>
	 * <p style="margin-left: 20px">{@code public static void println(Object, String)}</p>
	 * <p style="margin-left: 20px">This Method will add the given input with all other 
	 * Information to the Console</p>
	 * @param speaker	The Object, that called this Method.
	 * @param input	The Message to be printed.
	 * @since 1.0
	 */
	public static void println(Object speaker, String input) {
		String name ;
		if (speaker != null)
			name = speaker.getClass().getName();
		else name = "STATIC";
		long n = Thread.currentThread().getId();
		Timestamp t = new Timestamp(System.currentTimeMillis());
		System.out.println(t.toString()+" Thread-"+n+"="+name+": "+input);
	}
}
