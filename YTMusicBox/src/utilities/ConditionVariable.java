package utilities;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionVariable {
	
	private Lock l;
	private Condition playerAvailable;
	private Condition playableTrackAvailable;
	private Condition notParsedTrackAvailable;
	
	public ConditionVariable() {
		l = new ReentrantLock();
		playerAvailable = l.newCondition();
		this.playableTrackAvailable = l.newCondition();
		this.notParsedTrackAvailable = l.newCondition();
	}
	
	public Condition getNotParsedTrackAvailable(){
		return this.notParsedTrackAvailable;
	}
	
	public Condition getPlayerAvailable(){
		return playerAvailable;
	}
	
	public Condition getPlayableTrackAvailable() {
		return playableTrackAvailable;
	}

	public void lock(){
		l.lock();
	}
	
	public void unlock(){
		l.unlock();
	}
	


}
