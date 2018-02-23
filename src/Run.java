import java.util.LinkedList;
import java.util.Queue;

public class Run {
	private Queue<Racer> waitQueue;
	private Queue<Racer> runningQueue;
	private Queue<Racer> finishQueue;
	
	public Run(){
		this.waitQueue = new LinkedList<Racer>();
		this.runningQueue = new LinkedList<Racer>();
		this.finishQueue = new LinkedList<Racer>();
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
	 * @param start
	 */
	public void setRacerStartTime(){
		Racer headWait = waitQueue.poll();
		headWait.setStartTime();
	}
	
	
	

}
