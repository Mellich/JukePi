package client.serverconnection;

import client.serverconnection.functionality.handler.InputHandler;

public class InputExecutionFailedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8368062430035312604L;
	private InputHandler handler;
	private Exception ex;
	
	public InputExecutionFailedException(InputHandler input, Exception e) {
		handler = input;
		ex = e;
	}
	
	@Override
	public String getMessage() {
		return "An uncatched error occured while executing a server input: "+handler.toString()+", Exception: "+ex.getMessage();
	}

}
