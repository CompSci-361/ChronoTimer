package core;

import java.time.Instant;
import java.util.Date;

class Stopwatch {
	private boolean isActive = false;
	private double startTimeSystem = 0;
	private Date startTime = null;
	
	Stopwatch() {
		//passive timer. no threads
	}
	
	public void start() {
		isActive = true;
		startTimeSystem = Chronotimer.ourTimer.getSystemTime();
		startTime = Date.from(Instant.now());
	}
	
	public void stop() {
		isActive = false;
		startTimeSystem = 0;
		startTime = null;
	}
	
	public double getElapsedTime() {
		if (isActive) {
			Date now = Date.from(Instant.now());
			double result = startTimeSystem + (now.getTime() - startTime.getTime());
			return result;
		}
		
		return -1;
	}
}
