package core;
import java.util.ArrayDeque;
import java.util.Deque;
//import java.util.LinkedList;
//import java.util.Queue;

public class Run {
	private Deque<Racer> waitQueue;
	private Deque<Racer> runningQueue;
	private Deque<Racer> endQueue;
	
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
	public void setRacerStartTime(){
		Racer headWait = waitQueue.poll();
		headWait.setStartTime();
		runningQueue.add(headWait);
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	public void setRacerEndTime() {
		Racer headRunning = runningQueue.poll();
		headRunning.setEndTime();
		endQueue.add(headRunning);
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	public void giveDnf() {
		Racer headRunning = runningQueue.poll();
		headRunning.setDnf();
		endQueue.add(headRunning);
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	public void cancel() {
		Racer headRunning = runningQueue.poll();
		headRunning.clearStartTime();
		waitQueue.addFirst(headRunning);
	}
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
	

	public Racer[] getFinishedRacers() {
		return endQueue.toArray(new Racer[0]);
	}
}
