package start;

import connection.Collector;

/**
 * Starts the Client.
 * @author Haeldeus
 * @version 1.0
 */
public class ClientStarter {

	/**
	 * The Main-Method to start the Client.
	 * @param args	Just a stub.
	 * @since 1.0
	 */
	public static void main(String[] args) {
		Collector c = new Collector();
		c.startUp();
	}
}
