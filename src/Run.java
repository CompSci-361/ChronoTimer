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
	 * 
	 * @param bibNumber
	 */
	public void addRacer(int bibNumber){
		Racer racer = new Racer(bibNumber);
		if(waitQueue.contains(racer)) throw new IllegalArgumentException("Cannot have more than one racer with the same bib number");
		waitQueue.add(racer);
	}
	
	/**
	 * 
	 * 
	 */
	public void setRacerStartTime(){
		Racer headWait = waitQueue.poll();
		headWait.setStartTime();
		runningQueue.add(headWait);
	}
	/**
	 * 
	 */
	public void setRacerEndTime() {
		Racer headRunning = runningQueue.poll();
		headRunning.setEndTime();
		endQueue.add(headRunning);
	}
	/**
	 * 
	 */
	public void giveDnf() {
		Racer headRunning = runningQueue.poll();
		headRunning.setDnf();
		endQueue.add(headRunning);
	}
	public void cancel() {
		Racer headRunning = runningQueue.poll();
		waitQueue.addFirst(headRunning);
	}
	
	

}
