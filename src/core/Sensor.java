package core;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Sensor {
	private SensorType sensorType;
	private Channel channel;
	private int channelNumber;
	private Thread sensorThread = null;
	private volatile boolean isListening = false;
	private ArrayList<ActionListener> sensorListeners = null;
	Sensor(SensorType type, Channel channel, int channelNum) {
		sensorType = type;
		this.channel = channel;
		channelNumber = channelNum;
		channel.setConnect(type);
		sensorListeners = new ArrayList<ActionListener>();
		isListening = true;
		sensorThread = new Thread(new SensorThread());
		sensorThread.start();
	}
	
	/**
	 * Adds an event listener for handling when the sensor is triggered.
	 * @param listener The listener to call when the sensor has been triggered.
	 */
	public void addSensorFiredActionListener(ActionListener listener) {
		if (!sensorListeners.contains(listener)) {
			sensorListeners.add(listener);
		}
	}
	
	/**
	 * Gets the sensor's assigned channel number.
	 * @return <int>
	 */
	public int getChannelNumber() {
		return channelNumber;
	}
	
	/**
	 * Gets the sensor's type.
	 * @return SensorType <GATE,EYE,TRIP,NONE>
	 */
	public SensorType getSensorType() {
		return sensorType;
	}
	
	/**
	 * Tells the sensor to pretend that it has been triggered.
	 */
	public void simulateSensorTriggered() {
		if (isListening) {
			sensorThread.interrupt();
		}
	}
	
	/**
	 * Stops the sensor from listening by killing the thread and removing all listeners.
	 */
	public void close() {
		channel.setDisconnect();
		sensorListeners.clear();
		
		isListening = false;
	}
	
	//A class for running the sensor's thread.
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
