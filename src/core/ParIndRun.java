package core;

public class ParIndRun extends Run {
	
	private IndRun run1;
	private IndRun run2;
	
	public ParIndRun(Chronotimer timer){
		super(timer);
		run1 = new IndRun(timer);
		run2 = new IndRun(timer);
	}
	public ParIndRun(Chronotimer timer,int runNum){
		super(timer);
		run1 = new IndRun(timer);
		run2 = new IndRun(timer);
		this.runNumber = runNum;
	}
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	@Override
	public void addRacer(int bibNumber){
		if(run1.waitQueue.size() < run2.waitQueue.size()){
			run1.addRacer(bibNumber);
		}
		else{
			run2.addRacer(bibNumber);
		}
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.WaitQueue);
	}
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	@Override
	public void setRacerStartTime(int triggerNumber){
		if(triggerNumber == 1){
			run1.setRacerStartTime(triggerNumber);
		}
		else if(triggerNumber == 3){
			run2.setRacerStartTime(triggerNumber);
		}
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.RunningQueue);
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		if(triggerNumber == 2){
			run1.setRacerEndTime(triggerNumber);
		}
		else if(triggerNumber == 4){
			run2.setRacerEndTime(triggerNumber);
		}
		raiseQueueUpdatedEvent(RunQueueUpdatedEventType.FinishedQueue);
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		compareRun(run1, run2).cancel();
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		compareRun(run1, run2).cancel();
	}
	public void cancel(int bibNumber) {
		compareRun(run1, run2).cancel(bibNumber);
	}
  @Override
	public void clear(int bibNumber) {
		Racer racer = new Racer(bibNumber);
		run1.waitQueue.remove(racer);
		run2.waitQueue.remove(racer);
	}
  
	/**
	 * Returns whichever run started first
	 * This is used when deciding which racer should get canceled or dnf'd
	 * @param run1 
	 * @param run2
	 * @return
	 */

	private Run compareRun(Run run1, Run run2){
		if(run1.getCurrentRunningRacers().length == 0 && run2.getCurrentRunningRacers().length == 0)
			return null;
		if(run1.getCurrentRunningRacers().length == 0)
			return run2;
		else if(run2.getCurrentRunningRacers().length == 0)
			return run1;
		
		if(run1.getCurrentRunningRacers()[0].getStartTime() < run2.getCurrentRunningRacers()[0].getStartTime())
			return run1;
		else
			return run2;
	}
	/**
	 * merges the finished queue of run1 and run2 into what this.endQueue should be equal to
	 * Currently we have to calculate this queue everytime it is called
	 * Instead of calling this.endQueue calcEndQueue() needs to be called instead
	 * @return
	 */
	private Racer[] calcEndQueue() {
		Racer [] first = run1.getFinishedRacers();
		Racer [] second = run2.getFinishedRacers();
		Racer [] newArray = new Racer[first.length+second.length];
		System.arraycopy(first, 0, newArray, 0, first.length);
		System.arraycopy(second, 0, newArray, first.length, second.length );
		
		return newArray;
	}
	
	/**
	 * run1 waiting racers with run2 waiting racers concatenated onto the end
	 * this essentially represents the correct endQueue
	 * @return a Racer[] array which is the concatenation of
	 * 	run1 and run2
	 */
	@Override
	public Racer[] getCurrentWaitingRacers() {
		Racer [] first = run1.waitQueue.toArray(new Racer[0]);
		Racer [] second = run2.waitQueue.toArray(new Racer[0]);
		
		Racer [] newArray = new Racer[first.length+second.length];
		System.arraycopy(first, 0, newArray, 0, first.length);
		System.arraycopy(second, 0, newArray, first.length, second.length );
		
		return newArray;
	}
	
	
	/**run1 running racers with run2 running racers concatenated onto the end
	 * this essentially represents the correct endQueue
	 * @return a Racer[] array which is the concatenation of
	 * 	run1 and run2
	 */
	@Override
	public Racer[] getCurrentRunningRacers() {
		Racer [] first = run1.runningQueue.toArray(new Racer[0]);
		Racer [] second = run2.runningQueue.toArray(new Racer[0]);
		Racer [] newArray = new Racer[first.length+second.length];
		System.arraycopy(first, 0, newArray, 0, first.length);
		System.arraycopy(second, 0, newArray, first.length, second.length );
		
		return newArray;
	}
	
	@Override
	/**run1 finished racers with run2 finished racers concatenated onto the end
	 * this essentially represents the correct endQueue
	 * @return a Racer[] array which is the concatenation of
	 * 	run1 and run2
	 */
	public Racer[] getFinishedRacers() {
		return calcEndQueue();
	}
	/**
	 * True if the racer is in the wait queue of either run
	 */
	@Override
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		for(Racer racer : run1.waitQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		for(Racer racer : run2.waitQueue.toArray(new Racer[0])){
			if (racer.getBibNumber() == bibNumber) return true;
		}
		return false;
	}
	/**
	 * True if the racer is in the running queue 
	 */
	@Override
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		for(Racer racer : run1.runningQueue.toArray(new Racer[0])) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		for(Racer racer : run2.runningQueue.toArray(new Racer[0])){
			if (racer.getBibNumber() == bibNumber) return true;
		}
		return false;
	}
	/**
	 * True if racer is in the end queue
	 */
	@Override
	public boolean containsRacerBibNumberInEndQueue(int bibNumber) {
		for(Racer racer : calcEndQueue()) {
			if (racer.getBibNumber() == bibNumber) return true;
		}
		
		return false;
	}
	@Override
	public String toString() {
		String str= "";
		Racer[] printArray = getFinishedRacers();
		for(int i = 0; i < printArray.length; i++){
			str += printArray[i].toString() + "\n";
		}
		return str;
	}
}
