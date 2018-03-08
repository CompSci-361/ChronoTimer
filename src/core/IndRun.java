package core;

import java.util.ArrayDeque;
import java.util.Deque;

public class IndRun extends Run {
	
	protected Deque<Racer> waitQueue;
	protected Deque<Racer> runningQueue;
	
	public IndRun(){
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
		Racer headWait = waitQueue.poll();
		headWait.setStartTime();
		runningQueue.add(headWait);
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		Racer headRunning = runningQueue.poll();
		headRunning.setEndTime();
		endQueue.add(headRunning);
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		Racer headRunning = runningQueue.poll();
		headRunning.setDnf();
		endQueue.add(headRunning);
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		Racer headRunning = runningQueue.poll();
		headRunning.clearStartTime();
		waitQueue.addFirst(headRunning);
	}
	
	public void swap(){
		if(runningQueue.size() < 2)
			System.out.println("Running queue does not have enough racers to swap them.");
		else{
			Racer oldFirst = runningQueue.pop();
			Racer newFirst = runningQueue.pop();
			
			//switch the current racer in first with the second racer 
			//but make sure they're still at the head of the queue
			runningQueue.addFirst(oldFirst);
			runningQueue.addFirst(newFirst);
		}
	}
	
	/**
	 * Gets an array of all the racers that have finished.
	 * @return an array of all the racers that have finished.
	 */
	public Racer[] getFinishedRacers() {
		return endQueue.toArray(new Racer[0]);
	}
	
	/**
	 * Gets the current racer who is running.
	 * @return the current racer who is running.
	 */
	public Racer[] getCurrentRunningRacers() {
		return runningQueue.toArray(new Racer[0]);
	}
	
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		//a long name, i know.
		
		//there is a better way to do this but don't have enough time.
		for(Racer racer : waitQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		//a long name, i know.
		
		//there is a better way to do this but don't have enough time.
		for(Racer racer : runningQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	
	public boolean containsRacerBibNumberInEndQueue(int bibNumber) {
		//a long name, i know.
		
		//there is a better way to do this but don't have enough time.
		for(Racer racer : endQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	
	
	@Override
	public String toString(){
		String str= "";
		Object[] printArray = endQueue.toArray();
		for(int i = 0; i < printArray.length; i++){
			str += printArray[i].toString() + "\n";
		}
		return str;
	}
}
