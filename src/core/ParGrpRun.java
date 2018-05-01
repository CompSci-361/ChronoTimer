package core;

import java.util.ArrayDeque;
import java.util.Deque;

import core.Run.RunQueueUpdatedEventType;

public class ParGrpRun extends Run {
	
	double groupStart;
	protected Deque<Racer> waitQueue;
	protected Racer[] waitArray;
	protected Deque<Racer> runningQueue;
	boolean raceStarted;
	
	public ParGrpRun(int runNum) {
		this.runNumber = runNum;
		this.groupStart = -1;
		this.waitQueue = new ArrayDeque<Racer>();
		this.runningQueue = new ArrayDeque<Racer>();
		waitArray = new Racer[8];
		raceStarted = false;
	}
	/**
	 * Adds a racer with the param as the attribute
	 * Cannot add a racer if a racer with the same bibNumber already Exists
	 * @param bibNumber
	 */
	@Override
	public void addRacer(int bibNumber){
		Racer racer = new Racer(bibNumber);
		if(waitArray.length < 8)
			waitArray[waitArray.length] = racer;
		else
			Printer.printMessage("ParGrp race is full. Wait until next round.");
		return;
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
		//not used?
		return;
	}
	/**
	 * Takes the first Racer out of the running queue and places them at the front of the waitQueue
	 * Clears that Racers start time 
	 */
	@Override
	public void cancel() {
		//does not apply to parallel group races
		Printer.printMessage("Cancel does not apply to group races");
		return;
	}
	public void cancel(int bibNumber) {
		//does not apply to parallel group races
		Printer.printMessage("Cancel does not apply to group races");
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
		//does not apply to pargrp races
		
	}
	@Override
	public void triggerChannel(int channelNumber){
		if(!raceStarted){
			if(channelNumber == 1){
				groupStart = Chronotimer.ourTimer.getSystemTime();
			}
			else{
				Printer.printMessage("Channel 1 trigger starts the race");
			}
		}
		else{
			
		}
		
	}
}
