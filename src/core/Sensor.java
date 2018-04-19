package core;

public class Sensor {
	private SensorType sensorType;
	private int channelNumber; 
	Sensor(SensorType type, int channelNumber) {
		sensorType = type;
		this.channelNumber = channelNumber;
	}
	public int getChannelNumber() {
		return channelNumber;
	}
	
	public SensorType getSensorType() {
		return sensorType;
	}
	public void close() {
		
		
	}
}
