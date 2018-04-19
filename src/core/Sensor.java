package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Sensor {
	private SensorType sensorType;
	private int channelNumber;
	private Thread sensorThread = null;
	private volatile boolean isListening = false;
	private ArrayList<ActionListener> sensorListeners = null;
	Sensor(SensorType type, int channelNumber) {
		sensorType = type;
		this.channelNumber = channelNumber;
		sensorListeners = new ArrayList<ActionListener>();
		isListening = true;
		sensorThread = new Thread(new SensorThread());
		sensorThread.start();
	}
	
	public void addSensorFiredActionListener(ActionListener listener) {
		if (!sensorListeners.contains(listener)) {
			sensorListeners.add(listener);
		}
	}
	
	public int getChannelNumber() {
		return channelNumber;
	}
	
	public SensorType getSensorType() {
		return sensorType;
	}
	public void close() {
		sensorListeners.clear();
		
		isListening = false;
	}
	
	private class SensorThread implements Runnable {
		@Override
		public void run() {
			while(isListening) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					//fire our sensor
					
					for(ActionListener listener : sensorListeners) {
						listener.actionPerformed(new ActionEvent(Sensor.this, 1, 
								Sensor.this.getChannelNumber() + ":" + Sensor.this.getSensorType().toString()));
					}
				}
			}
			
		}
	}
}
