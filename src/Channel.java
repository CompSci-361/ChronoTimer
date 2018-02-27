
public class Channel {
	private boolean state;
	private String sensorType;
	
	public Channel() {
		state = false;
		sensorType = null;
	}
	public void toggle() {
		state = !state;
	}
	public boolean isEnabled() {
		return state;
	}
	public void setConnect(String sensor){
		sensorType = sensor;
	}
	
	public String getSensorType(){
		return sensorType;
	}
}
