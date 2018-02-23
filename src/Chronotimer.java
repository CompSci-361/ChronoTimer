
public class Chronotimer {
	
	private Run currentRun;
	private boolean isEnabled;
	private boolean[] channels;

	public Chronotimer(){
		isEnabled = false;
		currentRun = null;
		channels = new boolean [8];
		
		//TODO
	}
	
	public void reset(){
		isEnabled = false;
		currentRun = null;
		channels = new boolean [8];
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
	
	public void enableChannel(int channelNumber){
		channels[channelNumber-1] = true;
	}
		
	
	
	
}
