package start;

import connection.Collector;

public class ClientStarter {

	public static void main(String[] args) {
		Collector c = new Collector();
		c.startUp();
	}
}
