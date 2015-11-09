package util;

import java.sql.Timestamp;

public class IO {

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
