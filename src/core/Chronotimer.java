package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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
	private ArrayList<Sensor> sensors;
	private ArrayList<ActionListener> statusChangeListeners;
	
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
		sensors = new ArrayList<Sensor>();
		statusChangeListeners = new ArrayList<ActionListener>();
	}
	
	/**
	 * Puts the Chronotimer back to a default and cleared state
	 * except it doesn't  hange the information needed for the Gson files
	 */
	public void reset(){
		if (currentRun != null) {
			raiseRaceStatusChangedEvent(RaceStatusChangedEventType.RaceEnd);
		}
		
		currentRun = null;
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
		raceType = RaceType.IND;
		//runNumber = 0;
		
		Printer.printMessage("Chronotimer reset");
		
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
		
		Printer.printMessage("Power is " + (this.isPower == true? "ON" : "OFF"));
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
			Printer.printMessage("Power must be enabled to toggle channel");
			return;
		}
		channels[channelNumber-1].toggle();
		if(channels[channelNumber-1].isEnabled())
			Printer.printMessage("Channel " + channelNumber + " is enabled");
		else 
			Printer.printMessage("Channel " + channelNumber + " is disabled");
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
			Printer.printMessage("Power must be enabled to add racer to run");
			return;
		}
		if(channels[channelNumber-1].isEnabled()!=true) {
			Printer.printMessage("Channel "+ channelNumber + " not enabled");
			return;
		}
		if(currentRun == null){
			Printer.printMessage("Current Run must not be null");
			return;
		}
		
		if (isSensorConnected(channelNumber)) {
			Printer.printMessage("Sensor triggered on channel " + channelNumber);
			Sensor sensor = getSensorByChannelNumber(channelNumber);
			sensor.simulateSensorTriggered();
		} else {
			System.out.println("Channel triggered " + channelNumber);
			currentRun.triggerChannel(channelNumber);
		}
	}
	
	/**
	 * Checks if a sensor is connected to a specific channel.
	 * @param channelNumber The channel to check.
	 * @return <true, false>
	 */
	public boolean isSensorConnected(int channelNumber) {
		for(Sensor sensor : sensors) {
			if (sensor.getChannelNumber() == channelNumber) {
				return true;
			}
		}
		
		return false;
	}
	
	public boolean isSensorTypeConnected(int channelNumber){
		for(Sensor sensor : sensors) {
			if (sensor.getChannelNumber() == channelNumber) {
				if(sensor.getSensorType() != null && sensor.getSensorType() != SensorType.NONE)
					return true;
			}
		}
		return false;
	}
	
	/**
	 * Gets the sensor connected to a specific channel.
	 * @param channelNumber The channel of the sensor to retrieve.
	 * @return <Sensor, null>
	 */
	public Sensor getSensorByChannelNumber(int channelNumber) {
		for(Sensor sensor : sensors) {
			if (sensor.getChannelNumber() == channelNumber) {
				return sensor;
			}
		}
		
		return null;
	}
	
	/**
	 * Connects a sensor to a given channel
	 * @param channelNumber Which channel is being connected to
	 * @param sensorType <GATE,EYE,TRIP>
	 */
	public boolean setConnect(int channelNumber, SensorType sensorType){
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be enabled connect sensor");
			return false;
		}
		
		if (isSensorConnected(channelNumber)) {
			Printer.printMessage("A sensor is already connected to channel number: " + channelNumber);
			return false;
		}
		if(sensorType == SensorType.NONE) {
			Printer.printMessage("Can't connect NONE type sensor");
			return false;
		}
		Printer.printMessage(sensorType + " connected to channel " + channelNumber);
		//channels[channelNumber-1].setConnect(sensorType);
		//TODO
		//Check that channel is enabled and return true or false if it connects
		Sensor sensor = new Sensor(sensorType, channelNumber);
		sensor.addSensorFiredActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//simulate the sensor communicating with the chronotimer.
				Sensor theSensor = (Sensor)e.getSource();
				currentRun.triggerChannel(theSensor.getChannelNumber());
			}	
		});
		sensors.add(sensor);
		return true;
	}
	
	/**
	 * Disconnects the current sensor of a given channel
	 * @param channelNumber, which channel is being disconnected
	 */
	public void setDisconnect(int channelNumber){
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be enabled to disconnect sensor");
			return;
		}
		

		if (isSensorConnected(channelNumber)) {
			//channels[channelNumber-1].setDisconnect();
			Sensor sensor = getSensorByChannelNumber(channelNumber);
			Printer.printMessage("Sensor " + sensor.getSensorType() + " disconnected from channel " + channelNumber);
			sensor.close();
			sensors.remove(sensor);
		}
	}
	/**
	 * Gets the SensorType of the channel
	 * @param channelNumber
	 * @return <GATE,EYE,TRIP,NONE>
	 */
	public SensorType getSensorType(int channelNumber){
		if (isSensorConnected(channelNumber)) {
			//return channels[channelNumber-1].getSensorType();
			Sensor sensor = getSensorByChannelNumber(channelNumber);
			if (sensor != null) {
				return sensor.getSensorType();
			}
		}
		
		return SensorType.NONE;
	}
	
	public int getRunNumber() {
		return runNumber;
	}
	
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(){
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be turned on to start new run");
			return;
		}
		if(currentRun != null){
			Printer.printMessage("Must be starting a new run by ending one first or after initial power on");
			return;
		}
		setRunBasedOnRaceType(raceType);
		++runNumber;		
		currentRun.setRunNumber(runNumber);
		Printer.printMessage("New run started");
		raiseRaceStatusChangedEvent(RaceStatusChangedEventType.RaceNew);
	}
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(RaceType selectedType){
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be turned on to start new run");
			return;
		}
		if(currentRun != null){
			Printer.printMessage("Must be starting a new run by ending one first or after initial power on");
			return;
		}
		++runNumber;
		setRunBasedOnRaceType(selectedType);	
		currentRun.setRunNumber(runNumber);
		Printer.printMessage("New run started");
		raiseRaceStatusChangedEvent(RaceStatusChangedEventType.RaceNew);
	}
	
	/**
	 * Sets the raceType
	 * Power needs to be on
	 * @param event So far can only be "IND"
	 */
	public void setRaceType(RaceType event){
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be enabled to add racer to run");
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
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be turned on to select race type");
			return;
		}
		switch(selectedType) {
		case None:
			//throw an exception?
			Printer.printMessage("A race type must be selected first!");
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
	public boolean endRun(){
		//should this call currentRun.cancel
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be turned on to end run");
			return false;
		}
		if (currentRun != null) {
			if (!runHistory.contains(currentRun)) {
				runHistory.add(currentRun);
			}
			raiseRaceStatusChangedEvent(RaceStatusChangedEventType.RaceEnd);
			currentRun = null;
			Printer.printMessage("Run ended");
			return true;
		}
		return false;
	}
	
	public void exportRun(int requestedRunNumber) {
		Iterator<Run> runIter = runHistory.iterator();
		while (runIter.hasNext()) {
			Run next = runIter.next();
			if (next.getRunNumber() == requestedRunNumber) {
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
			Printer.printMessage("Power must be enabled to add racer to run");
			return;
		}
		if(currentRun == null){
			Printer.printMessage("Must start new run before adding racer");
			return;
		}
		currentRun.addRacer(bibNumber);
		Printer.printMessage(bibNumber+"");
	}
	
	
	/**
	 * Sets the time of the timer system
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public void setTime(int hours, int minutes, double seconds) {
		if(!getIsPoweredOn()){
			Printer.printMessage("Power must be enabled to set time");
			return;
		}
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
	 * Cancels a racer from the run and returns them to the queue
	 */
	public void cancel() {
		currentRun.cancel();
	}
	public void cancel(int bibNumber) {
		currentRun.cancel(bibNumber);
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
			Printer.printMessage("Swap is only used for IndRun");
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
		if(currentRun == null) {
			Printer.printMessage("Can't clear a racer without a current run");
			return;
		}
 		currentRun.clear(bibNumber);
	}
	
	public void addRaceStatusChangedActionListener(ActionListener listener) {
		if (!statusChangeListeners.contains(listener)) {
			statusChangeListeners.add(listener);
		} else {
			//this specific listener has already been attached.
			throw new IllegalArgumentException("listener");
		}
	}
	
	public enum RaceStatusChangedEventType {
		RaceNew,
		RaceEnd
	}
	
	private void raiseRaceStatusChangedEvent(RaceStatusChangedEventType type) {
		for(ActionListener listener : statusChangeListeners) {
			listener.actionPerformed(new ActionEvent(this, 0, type.toString()));
		}
	}
}
