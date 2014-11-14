package threads;

import connection.Collector;

public class CreateThread extends Thread{

	private Collector c;
	private String name;
	
	public CreateThread(Collector c, String name) {
		this.c = c;
		this.name = name;
	}
	
	public void run() {
		c.createNewList(name);
	}
}
