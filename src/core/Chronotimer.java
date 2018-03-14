package core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Iterator;

import com.google.gson.Gson;

public class Chronotimer {
	
	private Run currentRun;
	private RaceType raceType;
	private boolean isPower;
	private Channel[] channels;
	private int runNumber;
	private ArrayList<Run> runHistory;
	
	public static Timer ourTimer;

	public Chronotimer(){
		isPower = false;
		currentRun = null;
		channels = new Channel[8];
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
		raceType = RaceType.IND;
		runNumber = 0;
		runHistory = new ArrayList<Run>();
	}
	
	/**
	 * Puts the Chronotimer back to a default and cleared state
	 * except it doesn't  hange the information needed for the Gson files
	 */
	public void reset(){
		currentRun = null;
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
		raceType = RaceType.IND;
		//runNumber = 0;
	}
	
	/**
	 * Set the state of Power to ON/OFF
	 * Resets Everything
	 */
	public void togglePower(){
		if(isPower == true) {
			reset();
			runNumber = 0;
		}
		this.isPower = !isPower;
	}
	
	/**
	 * Get the state of Power 
	 * @return return the state of Power: ON/OFF
	 */
	public boolean getIsPoweredOn(){
		return this.isPower;
	}
	
	/**
	 * Toggles the state of the param channelNumber
	 * Enabled->Disabled
	 * Disabled->Enabled
	 * @param channelNumber Which channel is being changed
	 */
	public void toggleChannel(int channelNumber){
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		channels[channelNumber-1].toggle();
	}
	
	/**
	 * Gets the param's isEnabled Value and returns it
	 * @param channelNumber 
	 * @return The boolean value of the given channel
	 */
	public boolean getChannelIsEnabled(int channelNumber){
		return channels[channelNumber-1].isEnabled();
	}

	/**
	 * channelNumber==even then end time
	 * channelNumber==odd then start time
	 * @param channelNumber
	 */
	public void triggerChannel(int channelNumber) {
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		if(channels[channelNumber-1].isEnabled()!=true) {
			System.out.println("Channel "+ channelNumber + " not enabled");
			return;
		}
		if(currentRun == null){
			System.out.println("Current Run must not be null");
			return;
		}
		if(channelNumber % 2 == 0) {
			//if Even Channel
			currentRun.setRacerEndTime(channelNumber);
		}
		else {
			//if an odd channel
			currentRun.setRacerStartTime(channelNumber);
		}
	}
	
	/**
	 * Connects a sensor to a given channel
	 * @param channelNumber Which channel is being connected to
	 * @param sensorType <GATE,EYE,TRIP>
	 */
	public void setConnect(int channelNumber, SensorType sensorType){
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		channels[channelNumber-1].setConnect(sensorType);
	}
	
	/**
	 * Disconnects the current sensor of a given channel
	 * @param channelNumber, which channel is being disconnected
	 */
	public void setDisconnect(int channelNumber){
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		channels[channelNumber-1].setDisconnect();
	}
	/**
	 * Gets the SensoreType of the channel
	 * @param channelNumber
	 * @return <GATE,EYE,TRIP>
	 */
	public SensorType getSensorType(int channelNumber){
		return channels[channelNumber-1].getSensorType();
	}
	
	public int getRunNumber() {
		return runNumber;
	}
	
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(){
		if(currentRun != null) System.out.println("Must be starting a new run by ending one first or after initial power on");
		setRunBasedOnRaceType(raceType);
		++runNumber;		
		currentRun.setRunNumber(runNumber);
	}
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(RaceType selectedType){
		if(currentRun == null) System.out.println("Must be starting a new run by ending one first or after initial power on");
		++runNumber;
		setRunBasedOnRaceType(selectedType);	
		//currentRun.setRunNumber(runNumber);
	}
	
	/**
	 * Sets the raceType
	 * Power needs to be on
	 * @param event So far can only be "IND"
	 */
	public void setRaceType(RaceType event){
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		this.raceType = event;
	}
	
	/**
	 * Gets the current Race Type.
	 * @return the race type.
	 */
	
	public RaceType getRaceType() {
		return this.raceType;
	}
	
	private void setRunBasedOnRaceType(RaceType selectedType) {
		switch(selectedType) {
		case None:
			//throw an exception?
			System.out.println("A race type must be selected first!");
			return;
		case IND:
			currentRun = new IndRun(runNumber);
			break;
		case PARIND:
			currentRun = new ParIndRun(runNumber);
			break;
		case GRP:
			currentRun = new GrpRun(runNumber);
			break;
		case PARGRP:
			currentRun = new ParGrpRun(runNumber);
			break;
		}
	}
	
	/**
	 * Returns the current run (if any). Otherwise, returns null.
	 * @return the current run or null.
	 */
	public Run getCurrentRun() {
		return currentRun;
	}
	
	/**
	 * Clears the current run, newRun() can now be called
	 */
	public void endRun(){
		//should this call currentRun.cancel
		if (currentRun != null) {
			if (!runHistory.contains(currentRun)) {
				runHistory.add(currentRun);
			}
		}
		currentRun = null;
	}
	
	public void exportRun(int runNumber) {
		Iterator<Run> runIter = runHistory.iterator();
		while (runIter.hasNext()) {
			Run next = runIter.next();
			if (next.getRunNumber() == runNumber) {
				exportRun(next);
				break;
			}
		}
	}
	
	public void exportRun(Run specifiedRun) {
		if (specifiedRun != null) {
			
			//make the "exports" folder
			try {
				Files.createDirectories(Paths.get("exports"));
			} catch (Exception ex) {
				
			}
			
			//serialize the current run to json
			Gson g = new Gson();
			String json = g.toJson(specifiedRun);
			
			//write the json to "/exports/RUN###.json" (relative path)
			FileWriter fileWriter;
			try {
				fileWriter = new FileWriter("exports/RUN" + specifiedRun.getRunNumber() + ".json"); //can be changed to .txt but .json is technically correct
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				bufferedWriter.append(json);
				
				bufferedWriter.flush();
				bufferedWriter.close();
				fileWriter.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Adds a Racer to the current race
	 * Power must be on
	 * There must be an active Run
	 * @param bibNumber - Number given to the Racer
	 */
	public void addRacer(int bibNumber){
		if(!getIsPoweredOn()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		if(currentRun == null){
			System.out.println("Current run must not be null");
			return;
		}
		currentRun.addRacer(bibNumber);
	}
	
	
	/**
	 * Sets the time of the timer system
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public void setTime(int hours, int minutes, double seconds) {
		ourTimer.setTime(hours, minutes, seconds);
	}
	
	/**
	 * Gets the current system time from ourTimer
	 * @return
	 */
	public String getTime(){
		return ourTimer.formatTime(ourTimer.getSystemTime());
	}
	
	
	/**
	 * Gives a runner from our currentRun a DNF
	 */
	public void dnf() {
		currentRun.giveDnf();
	}
	
	/**
	 * Cancels a race from the run and returns them to the queue
	 */
	public void cancel() {
		currentRun.cancel();
	}
	
	/**
	 * Since the raceType is only IND, Start is always just the same as Trig channel 1
	 */
	public void start(){
		triggerChannel(1);
	}
	
	/**
	 * Since the raceType is only IND, Finish is always just the smae as Trig channel 2
	 */
	public void finish(){
		triggerChannel(2);
	}
	
	/**
	 * Swaps the two racers at the start of the running queue
	 */
	public void swap(){
		if(currentRun instanceof IndRun)
			((IndRun) currentRun).swap();
		else
			System.out.println("Swap is only used for IndRun");
	}
	
	/**
	 * Prints the entire run
	 * Will only print Racers that have a finish time
	 * --Might want to change it to print all Racers in the system
	 */
	public void print(){
		if (currentRun != null) {
			Printer.printRun(currentRun);
		}
		//if the current run has ended
		//can't print if there are no runs in the history
		else {
			if(runHistory.size()==0) return;
			Printer.printRun(runHistory.get(runHistory.size()-1));
		}
	}

	public void clear(int bibNumber) {
		currentRun.clear(bibNumber);
	}
	
}
