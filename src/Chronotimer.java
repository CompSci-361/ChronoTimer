
public class Chronotimer {
	
	private Run currentRun;
	private boolean isEnabled;
	private Channel[] channels;
	public static Timer ourTimer;

	public Chronotimer(){
		isEnabled = false;
		currentRun = null;
		channels = new Channel [8];
		ourTimer = new Timer();
		//TODO
	}
	
	public void reset(){
		isEnabled = false;
		currentRun = null;
		channels = new Channel [8];
		ourTimer = new Timer();
		//TODO is the same as constructor
	}
	
	public void newRun(){
		currentRun = new Run();
	}
	
	/**
	 * Set the state of Power to ON/OFF
	 * @param isEnabled toggle the power, True for Enabled, False for Disabled
	 */
	public void setIsEnabled(boolean isEnabled){
		this.isEnabled = isEnabled;
	}
	
	/**
	 * Get the state of Power 
	 * @return return the state of Power: ON/OFF
	 */
	public boolean getIsEnabled(){
		return this.isEnabled;
	}
	
	public void addRacer(int bibNumber){
		if(!getIsEnabled()) throw new IllegalStateException("Power must be enabled to add racer to run");
		if(currentRun == null) throw new NullPointerException("Current run must not be null");
		currentRun.addRacer(bibNumber);
	}
	
	public void toggleChannel(int channelNumber){
//		channels[channelNumber-1] = true;
		channels[channelNumber].toggle();
	}
	/**
	 * channelNumber==even then end time
	 * channelNumber==odd then start time
	 * @param channelNumber
	 */
	public void triggerChannel(int channelNumber) {
		if(!getIsEnabled()) throw new IllegalStateException("Power must be enabled to add racer to run");
		if(channels[channelNumber].isEnabled()!=true) {
			System.out.println("Channel "+ channelNumber + " not enabled");
			throw new IllegalStateException("Channel "+ channelNumber + " not enabled");
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
	public void dnf() {
		currentRun.giveDnf();
	}
	public void cancel() {
		currentRun.cancel();
	}
	
	
	
}
