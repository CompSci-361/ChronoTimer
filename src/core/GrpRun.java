package core;

import java.util.ArrayDeque;
import java.util.Deque;

public class GrpRun extends Run {
	
	//order of finishing
	int placeholder;
	
	//start time for all racers in this group run
	double groupStart;
	
	protected Deque<Racer> waitQueue;
	protected Deque<Racer> runningQueue;
	
	public GrpRun(int runNum) {
		this.runNumber = runNum;
		this.placeholder = 0;
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
	}
	
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	@Override
	public void addRacer(int bibNumber){
		Racer racer = new Racer(bibNumber);
		if(waitQueue.contains(racer)||runningQueue.contains(racer)||endQueue.contains(racer)){
			System.out.println("Cannot have more than one racer with the same bib number");
			return;
		}
		waitQueue.add(racer);
	}
	
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	@Override
	public void setRacerStartTime(int triggerNumber){
		//start time is the same for all group racers
		
		Racer headWait = waitQueue.poll();
		if(headWait == null) {
			System.out.println("No waiting racers");
			return;
		}
		headWait.setStartTime();
		runningQueue.add(headWait);
		
		return;
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		//todo
		return;
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		//does not apply to group races
		return;
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		//todo
		return;
	}
	
	@Override
	public Racer[] getFinishedRacers() {
		return endQueue.toArray(new Racer[0]);
	}
	
	@Override
	public Racer[] getCurrentRunningRacers() {
		if(runningQueue.size() != 0){
			return runningQueue.toArray(new Racer[0]);
		}
		return null;
	}
	@Override
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public boolean containsRacerBibNumberInEndQueue(int bibNumber) {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public String toString() {
		String str= "";
		Object[] printArray = endQueue.toArray();
		for(int i = 0; i < printArray.length; i++){
			str += printArray[i].toString() + "\n";
		}
		return str;
	}
	
	@Override
	public Racer[] getCurrentWaitingRacers() {
		Racer [] first = run1.waitQueue.toArray(new Racer[0]);
		Racer [] second = run2.waitQueue.toArray(new Racer[0]);
		
		Racer [] newArray = new Racer[first.length+second.length];
		System.arraycopy(first, 0, newArray, 0, first.length);
		System.arraycopy(second, 0, newArray, first.length, second.length );
		
		return newArray;
	}
	@Override
	public void clear(int bibNumber) {
		// TODO Auto-generated method stub
		
	}
}
