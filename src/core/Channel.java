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
	 * @return whether the connect succeeded or not
	 */
	public boolean setConnect(SensorType sensor){
		if(state) {
			if(sensor == SensorType.NONE) {
				Printer.printMessage("Can't connect a sensor of type NONE");
				return false;
			}else {
				sensorType = sensor;
			}
		}else {
			Printer.printMessage("Can't connect to disabled channel");
			return false;
		}
		return true;
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
