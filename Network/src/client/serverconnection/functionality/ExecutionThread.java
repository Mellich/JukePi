package client.serverconnection.functionality;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

import client.serverconnection.exceptions.InputExecutionFailedException;
import client.serverconnection.functionality.handler.InputHandler;

public class ExecutionThread extends Thread {

	
	private Queue<InputHandler> inputs = new ConcurrentLinkedQueue<InputHandler>();
	private Semaphore availableInput = new Semaphore(0);
	
	public ExecutionThread() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * adds a new InputHandler, that will be executed later by the ExecutionThread
	 *  
	 * @param handler the InputHandler, that should be executed
	 */
	public void add(InputHandler handler){
		inputs.add(handler);
		availableInput.release();
	}
	
	@Override
	public void run() {
		while(!this.isInterrupted()){
			InputHandler nextInput = null;
			try {
				availableInput.acquire();
				nextInput = inputs.poll();
				nextInput.execute();
			} catch (InterruptedException e) {
				System.out.println("Interrupted");
				//thread was interrupted because connection will be closed
			} catch (Exception e){
				throw new InputExecutionFailedException(nextInput,e);
			}
		}
	}
}
