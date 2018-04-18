package core;
import java.util.ArrayDeque;
import java.util.Deque;
//import java.util.LinkedList;
//import java.util.Queue;

public abstract class Run {
	protected Deque<Racer> endQueue;
	protected int runNumber;
	
	public Run(){
			this.endQueue = new ArrayDeque<Racer>();
	}
	
	public void setRunNumber(int number) {
		runNumber = number;
	}
	
	public int getRunNumber() {
		return runNumber;
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
	 * @param triggerNumber TODO
	 */
	public abstract void setRacerStartTime(int triggerNumber);
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 * @param triggerNumber TODO
	 */
	public abstract void setRacerEndTime(int triggerNumber);
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	public abstract void giveDnf();
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	public abstract void cancel();
	
	public abstract void cancel(int bibNumber);
	
	public abstract void clear(int bibNumber);
	
	/**
	 * Gets an array of all the racers that have finished.
	 * @return an array of all the racers that have finished.
	 */
	public abstract Racer[] getFinishedRacers();
	
	/**
	 * Gets the current racer who is running.
	 * @return the current racer who is running.
	 */
	public abstract Racer[] getCurrentRunningRacers();
	/**
	 * Creates an array of all the current running racers
	 * @return array of the waiting racers
	 */
	public abstract Racer[] getCurrentWaitingRacers();
	/**
	 * 
	 * @param bibNumber
	 * @return true if racer is waiting
	 */
	public abstract boolean containsRacerBibNumberInWaitQueue(int bibNumber);
	/**
	 * 
	 * @param bibNumber
	 * @return true if racer is running
	 */
	public abstract boolean containsRacerBibNumberInRunningQueue(int bibNumber);
	/**
	 * 
	 * @param bibNumber
	 * @return true if racer has finished already
	 */
	public abstract boolean containsRacerBibNumberInEndQueue(int bibNumber);
		
	public String toString() {
		String str= "";
		Object[] printArray = getFinishedRacers();
		for(int i = 0; i < printArray.length; i++){
			str += printArray[i].toString() + "\n";
		}
		return str;
	}

	public void triggerChannel(int channelNumber) {
		if(channelNumber % 2 == 0) {
			//if Even Channel
			this.setRacerEndTime(channelNumber);
		}
		else {
			//if an odd channel
			this.setRacerStartTime(channelNumber);
		}
		
	}

}
