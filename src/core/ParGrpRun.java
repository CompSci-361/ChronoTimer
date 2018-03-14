package core;

public class ParGrpRun extends Run {
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	@Override
	public void addRacer(int bibNumber){
		//todo
		return;
	}
	/**
	 * Gives start time to the first Racer in the queue
	 * Adds them to the running queue indicating that the racer still needs an endtime
	 */
	@Override
	public void setRacerStartTime(int triggerNumber){
		//todo
		return;
	}
	/**
	 * Gives end time  to the first Racer in the running queue
	 * Adds the Racer to the endQueue indicating that the Racer is complete and finished
	 */
	@Override
	public void setRacerEndTime(int triggerNumber) {
		//todo
		return;
	}
	/**
	 * Sets the end time of a Racer that is running to DNF(-1)
	 */
	@Override
	public void giveDnf() {
		//todo
		return;
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		//todo
		return;
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
	@Override
	public Racer[] getCurrentWaitingRacers() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void clear(int bibNumber) {
		// TODO Auto-generated method stub
		
	}
}
