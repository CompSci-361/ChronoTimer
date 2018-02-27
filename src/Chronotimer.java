
public class Chronotimer {
	
	private Run currentRun;
	private String raceType;
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
	}
	/**
	 * Puts the Chronotimer back to a default and cleared state
	 */
	public void reset(){
		//isPower = false;
		currentRun = null;
		for(int i = 0; i < 8; i++)
			channels[i] = new Channel();
		ourTimer = new Timer();
	}
	/**
	 * Creates a new run only if there isn't already a run active
	 */
	public void newRun(){
		if(currentRun != null) System.out.println("Must be starting a new run by ending one first or after initial power on");
		currentRun = new Run();
	}
	/**
	 * Clears the current run, newRun() can now be called
	 */
	public void endRun(){
		currentRun = null;
	}
	
	/**
	 * Set the state of Power to ON/OFF
	 * @param isEnabled toggle the power, True for Enabled, False for Disabled
	 */
	public void togglePower(){
		this.isPower = !isPower;
	}
	
	/**
	 * Get the state of Power 
	 * @return return the state of Power: ON/OFF
	 */
	public boolean getIsPower(){
		return this.isPower;
	}
	/**
	 * Adds a Racer to the current race
	 * Power must be on
	 * There must be an active Run
	 * @param bibNumber - Number given to the Racer
	 */
	public void addRacer(int bibNumber){
		if(!getIsPower()){
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
		if(!getIsPower()){
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
	public boolean getChannel(int channelNumber){
		return channels[channelNumber-1].isEnabled();
	}
	/**
	 * Connects a sensor to a given channel
	 * @param channelNumber Which channel is being connected to
	 * @param sensorType <GATE,EYE,TRIP>
	 */
	public void setConnect(int channelNumber, String sensorType){
		if(!getIsPower()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		channels[channelNumber-1].setConnect(sensorType);
	}
	/**
	 * Gets the SensoreType of the channel
	 * @param channelNumber
	 * @return <GATE,EYE,TRIP>
	 */
	public String getSensorType(int channelNumber){
		return channels[channelNumber-1].getSensorType();
	}
	/**
	 * channelNumber==even then end time
	 * channelNumber==odd then start time
	 * @param channelNumber
	 */
	public void triggerChannel(int channelNumber) {
		if(!getIsPower()){
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
			currentRun.setRacerEndTime();
		}
		else {
			currentRun.setRacerStartTime();
		}
	}
	public void setTime(int hours, int minutes, double seconds) {
		ourTimer.setTime(hours, minutes, seconds);
	}
	public String getTime(){
		return ourTimer.formatTime(ourTimer.getSystemTime());
	}
	public void dnf() {
		currentRun.giveDnf();
	}
	public void cancel() {
		currentRun.cancel();
	}
	
	public void print(){
		Printer.printRun(currentRun);
	}
	
	public void start(){
		triggerChannel(1);
	}
	
	public void finish(){
		triggerChannel(2);
	}
	
	public void setRaceType(String event){
		if(!getIsPower()){
			System.out.println("Power must be enabled to add racer to run");
			return;
		}
		this.raceType = event;
		
		switch(event){
			case "IND":{
				
			}
		}
	}
	
	
}
