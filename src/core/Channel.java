package core;

public class Channel {
	private boolean state;
	private String sensorType;
	
	public Channel() {
		state = false;
		sensorType = null;
	}
	/**
	 * Toggles the channel
	 */
	public void toggle() {
		state = !state;
	}
	/**
	 * 
	 * @return the state of the channel
	 */
	public boolean isEnabled() {
		return state;
	}
	/**
	 * Connects the param to this channel by setting the sensorType to <GATE,EYE,TRIP>
	 * @param sensor
	 */
	public void setConnect(String sensor){
		sensorType = sensor;
	}
	/**
	 * @return The type of sensor connected to this channel, can be null if not connected
	 */
	public String getSensorType(){
		return sensorType;
	}
}
