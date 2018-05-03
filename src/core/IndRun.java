package core;

import java.util.ArrayDeque;
import java.util.Deque;

public class IndRun extends Run {
	
	protected Deque<Racer> waitQueue;
	protected Deque<Racer> runningQueue;
	
	public IndRun(Chronotimer timer){
		super(timer);
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
	}
	public IndRun(Chronotimer timer,int runNum){
		super(timer);
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
		this.runNumber = runNum;
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
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.WaitQueue);
	}
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	@Override
	public void setRacerStartTime(int triggerNumber){
		Racer headWait = waitQueue.poll();
		if(headWait == null) {
			System.out.println("No waiting racers");
			return;
		}
		headWait.setStartTime();
		runningQueue.add(headWait);
		headWait.onBeginRacing();
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.RunningQueue);
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		Racer headRunning = runningQueue.poll();
		if(headRunning == null) {
			System.out.println("No currently running racers");
			return;
		}
		headRunning.setEndTime();
		endQueue.add(headRunning);
		headRunning.onFinishRacing();
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		Racer headRunning = runningQueue.poll();
		if(headRunning == null) {
			System.out.println("No currently running racers");
			return;
		}
		headRunning.setDnf();
		endQueue.add(headRunning);
		headRunning.onFinishRacing();
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		Racer headRunning = runningQueue.poll();
		if(headRunning == null) {
			System.out.println("No currently running racers");
			return;
		}
		headRunning.clearStartTime();
		waitQueue.addFirst(headRunning);
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.WaitQueue);
	}
	/**
	 * clears the specific racer
	 */
	public void cancel(int bibNumber) {
		if(runningQueue.isEmpty())	System.out.println("No currently running racers");
		
		Deque<Racer> reversed = new ArrayDeque<Racer>(); 
		Racer notFullRacer = new Racer(bibNumber);
		Racer fullRacer;
		while(!runningQueue.isEmpty()) {
			fullRacer = runningQueue.poll();
			//if we found racer, clear that racer
			if(fullRacer.equals(notFullRacer)) {
				fullRacer.clearStartTime();
				waitQueue.addFirst(fullRacer);
				fullRacer.onFinishRacing();
				break;
			}
			//put the non correct racer into the reversed queue to hold
			reversed.add(fullRacer);
		}
		//put all the racers back into the running queue in the correct order
		while(!reversed.isEmpty()) {
			runningQueue.add(reversed.pop());
		}
		
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.RunningQueue);
	}
	/**
	 * Changes the first two runners in the running queue
	 */
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
		
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.RunningQueue);
	}
	/**
	 * Clears the race with bibNumber. Takes them out of the waitQueue
	 */
	@Override
	public void clear(int bibNumber) {
		Racer racer = new Racer(bibNumber);
		waitQueue.remove(racer);
		
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.WaitQueue);
	}
	
	/**
	 * Gets an array of all the racers that have finished.
	 * @return an array of all the racers that have finished.
	 */
	public Racer[] getFinishedRacers() {
		return endQueue.toArray(new Racer[0]);
	}
	
	/**
	 * Gets the current racers who is running.
	 * @return the current racers who is running.
	 */
	public Racer[] getCurrentRunningRacers() {
		return runningQueue.toArray(new Racer[0]);
	}
	/**
	 * @return the waiting racers array
	 */
	@Override
	public Racer[] getCurrentWaitingRacers() {
		return waitQueue.toArray(new Racer[0]);
	}
	/**
	 * @return true if the racer is in wait queue
	 */
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		//a long name, i know.
		
		//there is a better way to do this but don't have enough time.
		for(Racer racer : waitQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	/**
	 * @return true if the racer is in the runnng queue
	 */
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		//a long name, i know.
		
		//there is a better way to do this but don't have enough time.
		for(Racer racer : runningQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	
	/**
	 * @return true if racer is in the end queue
	 */
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
