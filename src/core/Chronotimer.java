package core;

public class Chronotimer {
	
	private Run currentRun;
	private RaceType raceType;
	private boolean isPower;
	private Channel[] channels;
	
	public static Timer ourTimer;

	public Chronotimer(){
		isPower = false;
		currentRun = null;
		channels = new Channel[8];
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
		raceType = RaceType.IND;
	}
	
	/**
	 * Puts the Chronotimer back to a default and cleared state
	 */
	public void reset(){
		currentRun = null;
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
		raceType = RaceType.None;
	}
	
	/**
	 * Set the state of Power to ON/OFF
	 */
	public void togglePower(){
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
			currentRun = new IndRun();
			break;
		case PARIND:
			currentRun = new ParIndRun();
			break;
		case GRP:
			currentRun = new GrpRun();
			break;
		case PARGRP:
			currentRun = new ParGrpRun();
			break;
		}
	}
	
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(){
		if(currentRun != null) System.out.println("Must be starting a new run by ending one first or after initial power on");
		setRunBasedOnRaceType(raceType);
	}
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(RaceType selectedType){
		if(currentRun != null) System.out.println("Must be starting a new run by ending one first or after initial power on");
		setRunBasedOnRaceType(selectedType);
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
		//should this call currentRun.cancel?
		currentRun = null;
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
			currentRun.setRacerEndTime(channelNumber);
		}
		else {
			currentRun.setRacerStartTime(channelNumber);
		}
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
		Printer.printRun(currentRun);
	}
	
}
