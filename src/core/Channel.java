package core;

public class Channel {
	private boolean state;
	private SensorType sensorType;
	
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
	 * @return The toggled state of the channel
	 */
	public boolean isEnabled() {
		return state;
	}
	
	/**
	 * Connects the param to this channel by setting the sensorType to <GATE,EYE,TRIP, PAD, NONE>
	 * @param sensor
	 */
	public void setConnect(SensorType sensor){
		sensorType = sensor;
	}
	
	/**
	 * Disconnects the sensor that's connected and sets the type to NONE
	 */
	public void setDisconnect(){
		sensorType = SensorType.NONE;
	}
	
	/**
	 * @return The type of sensor connected to this channel, can be null if not connected or NONE if disconnected
	 */
	public SensorType getSensorType(){
		return sensorType;
	}
}
