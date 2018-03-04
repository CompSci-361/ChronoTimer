package core;
import java.util.ArrayDeque;
import java.util.Deque;
//import java.util.LinkedList;
//import java.util.Queue;

public abstract class Run {
	protected Deque<Racer> waitQueue;
	protected Deque<Racer> runningQueue;
	protected Deque<Racer> endQueue;
	
	public Run(){
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
		this.endQueue = new ArrayDeque<Racer>();
	}
	
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	public abstract void addRacer(int bibNumber);
	
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	public abstract void setRacerStartTime();
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	public abstract void setRacerEndTime();
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	public abstract void giveDnf();
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	public abstract void cancel();
	/**
	 * Appends all finished Racers' times in the format:
	 * Racer XX1 : Start Time = Hours:Min:Seconds : End Time = Hours:Min:Seconds \n
	 * Racer XX2 : Start Time = Hours:Min:Seconds : End Time = Hours:Min:Seconds \n
	 */
	@Override
	public String toString(){
		String str= "";
		Object[] printArray = endQueue.toArray();
		for(int i = 0; i < printArray.length; i++){
			str += printArray[i].toString() + "\n";
		}
		return str;
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
	public Racer getCurrentRunningRacer() {
		return runningQueue.peek();
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
}
