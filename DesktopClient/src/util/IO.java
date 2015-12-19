package util;

import java.sql.Timestamp;

/**
 * This class will print out Messages, the Server and the Client will add to it, as well with 
 * the Object, that called this Object and a Timestamp, when it was called.
 * @author Haeldeus
 * @version 1.0
 */
public class IO {

	/**
	 * This Method will add the given input with all other Information to the Console.
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
