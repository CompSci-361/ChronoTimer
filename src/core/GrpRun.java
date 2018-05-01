package core;

import java.util.ArrayDeque;
import java.util.Deque;

public class GrpRun extends Run {
	
	//order of finishing
	int placeholder;
	boolean firstTrigger;
	boolean flag;
	
	//start time for all racers in this group run
	double groupStart;
	
	protected Deque<Racer> waitQueue;
	protected Deque<Racer> runningQueue;
	protected Deque<Double> timeQueue;
	
	public GrpRun(int runNum) {
		this.runNumber = runNum;
		this.placeholder = 1;
		this.firstTrigger = true;
		this.flag = false;
		this.groupStart = -1;
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
		this.timeQueue = new ArrayDeque<Double>();
	}
	
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	@Override
	public void addRacer(int bibNumber){
		//if racers are not added prior to start of the race
		//we use -1 to represent that the first trigger has not been called (i.e. race hasn't started)
		if(groupStart != -1){
			if(flag){
				for(Racer racer : runningQueue.toArray(new Racer[0])) {
					if (racer.getBibNumber() == bibNumber){
						if(!timeQueue.isEmpty()){
							racer.setEndTime(timeQueue.poll());
							endQueue.add(racer);
							runningQueue.remove(racer);
							racer.onFinishRacing();
						}
					}
				}
				raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
			}
			else{
				Racer racerCheck = endQueue.peek();
				if(endQueue.contains(racerCheck)){
					Printer.printMessage("Cannot add duplicate racer");
					return;
				}
				Racer racer = endQueue.poll();
				racer.setBibNumber(bibNumber);
				endQueue.add(racer);
				raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
			}
			
			
		}
		else{
			//set flag to true to indicate we are keeping track of the racers
			flag = true;
			Racer racer = new Racer(bibNumber);
			if(!waitQueue.contains(racer)){
				waitQueue.add(racer);
				raiseQueueUpdatedEvent(RunQueueUpdatedEventType.WaitQueue);
			}
			else{
				Printer.printMessage("Cannot add duplicate racer");
			}
		}
	}
	
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	@Override
	public void setRacerStartTime(int triggerNumber){
		//not used
		return;
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		//not used
		return;
	}
	
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		//does not apply to group races
		Printer.printMessage("DNF does not apply to group races");
		return;
	}
	
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		//does not apply to group races
		Printer.printMessage("Cancel does not apply to group races");
		return;
	}
	public void cancel(int bibNumber) {
		//does not apply to group races
		Printer.printMessage("Cancel does not apply to group races");
		return;
	}
	@Override
	public Racer[] getFinishedRacers() {
		return endQueue.toArray(new Racer[0]);
	}
	
	@Override
	public Racer[] getCurrentRunningRacers() {
		//not used
		return runningQueue.toArray(new Racer[0]);
	}
	@Override
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		for(Racer racer : waitQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		return false;
	}
	@Override
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		for(Racer racer : runningQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		return false;
	}
	@Override
	public boolean containsRacerBibNumberInEndQueue(int bibNumber) {
		for(Racer racer : endQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
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
		return waitQueue.toArray(new Racer[0]);
	}
	
	@Override
	public void clear(int bibNumber) {
		//does not apply to group races
		Printer.printMessage("Clear does not apply to group races");
		return;
	}
	
	@Override
	public void triggerChannel(int channelNumber){
		if(flag){
			if(channelNumber == 1){
				if(firstTrigger){
					firstTrigger = false;
					groupStart = Chronotimer.ourTimer.getSystemTime();
					
					//set all the racers in the waitQueue to the runningQueue
					while(!waitQueue.isEmpty()){
						Racer r = waitQueue.poll();
						runningQueue.add(r);
						r.onBeginRacing();
					}
					
					raiseQueueUpdatedEvent(RunQueueUpdatedEventType.RunningQueue);
				}
				else{
					Printer.printMessage("There is only one start on channel 1");
				}
			}
			else if(channelNumber == 2){
				timeQueue.add(Chronotimer.ourTimer.getSystemTime());
			}
			else{
				Printer.printMessage("There is only one start on channel 1");
			}
			return;
		}
		else{
			if(channelNumber == 1){
				if(firstTrigger){
					firstTrigger = false;
					groupStart = Chronotimer.ourTimer.getSystemTime();
				}
				else{
					Printer.printMessage("There is only one start on channel 1");
					return;
				}
			}
			else if(channelNumber == 2){
				//create new racer
				Racer racer = new Racer(placeholder);
				
				//set racer start time to group start
				racer.setStartTime(groupStart);
				racer.onBeginRacing();
				
				//set racer end time to current time
				racer.setEndTime();
				racer.onFinishRacing();
				
				//add the racer to the finish queue
				endQueue.add(racer);
				
				//increment placeholder for next runner
				++placeholder;
				
				raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
			}
			else{
				Printer.printMessage("Can't use this channel");
				return;
			}
		}
	}
}
