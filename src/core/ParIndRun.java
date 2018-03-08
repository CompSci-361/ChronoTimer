package core;

public class ParIndRun extends Run {
	
	private IndRun run1;
	private IndRun run2;
	
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
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		if(triggerNumber == 1){
			run1.setRacerEndTime(triggerNumber);
		}
		else if(triggerNumber == 3){
			run2.setRacerEndTime(triggerNumber);
		}
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
	
	private Run compareRun(Run run1, Run run2){
		if(run1.getCurrentRunningRacers()[0].getStartTime() < run2.getCurrentRunningRacers()[0].getStartTime())
			return run1;
		else
			return run2;
	}
	
	@Override
	public Racer[] getFinishedRacers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Racer[] getCurrentRunningRacers() {
		// TODO Auto-generated method stub
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
		// TODO Auto-generated method stub
		return null;
	}
}
