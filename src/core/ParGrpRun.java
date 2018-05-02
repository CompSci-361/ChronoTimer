
package core;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;

import core.Run.RunQueueUpdatedEventType;

public class ParGrpRun extends Run {
	
	double groupStart;
	protected Racer[] waitArray;
	protected Racer[] runningArray;
	boolean raceStarted;
	
	public ParGrpRun(int runNum) {
		this.runNumber = runNum;
		this.groupStart = -1;
		this.runningArray = new Racer[8];
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
		if(waitSize() < 8)
			waitArray[waitSize()] = racer;
		else
			Printer.printMessage("ParGrp race is full. Wait until next round.");
		return;
	}
	
	public int waitSize(){
		int size = 0;
		for(int i = 0; i < 8; i++){
			if(waitArray[i] != null) ++size;
		}
		return size;
	}
	
	public int runSize(){
		int size = 0;
		for(int i = 0; i < 8; i++){
			if(runningArray[i] != null) ++size;
		}
		return size;
	}
	
	public int endSize(){
		return endQueue.size();
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
	
	public void giveDnf(int channelNumber) {
		//not used
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
		return endQueue.toArray(new Racer[0]);
	}
	@Override
	public Racer[] getCurrentRunningRacers() {
		return runningArray.clone();
	}
	@Override
	public boolean containsRacerBibNumberInWaitQueue(int bibNumber) {
		for(int i = 0; i < waitArray.length; i++){
			if(waitArray[i].getBibNumber() == bibNumber)
				return true;
		}
		return false;
	}
	@Override
	public boolean containsRacerBibNumberInRunningQueue(int bibNumber) {
		for(int i = 0; i < runningArray.length; i++){
			if(runningArray[i].getBibNumber() == bibNumber)
				return true;
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
			return waitArray;
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
				for(int i = 0; i < waitArray.length; i++){
					if(waitArray[i] != null){
						waitArray[i].setStartTime(groupStart);
//						if(!chronotimer.isSensorTypeConnected(i))
//							waitArray[i].setDnf();
						runningArray[i] = waitArray[i];
						waitArray[i] = null;
					}
				}
				raceStarted = true;
			}
			else{
				Printer.printMessage("Channel 1 trigger starts the race");
			}
		}
		else{
			if(runningArray[channelNumber-1] != null){
				runningArray[channelNumber-1].setEndTime();
				endQueue.add(runningArray[channelNumber-1]);
				runningArray[channelNumber-1] = null;
			}
			else{
				Printer.printMessage("No active running racer");
			}
		}
		
	}
}
