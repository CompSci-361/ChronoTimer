package core;

public class Timer {
	//Milliseconds
	double oldSystemTime;
	double setTime;

	/**
	 * setTime holds the user input value
	 * oldSystemTime holds the SystemTime(of the OS) at which the user input that time
	 */
	public Timer() {
		setTime=0.0;
		oldSystemTime=System.currentTimeMillis();
	}
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return the difference of the params in milliseconds
	 */
	public double calculateTime(double startTime, double endTime){
		return endTime - startTime;
	}
	
	/**
	 * The different between the currentOSSytemTime and the time at which setTime was set
	 * 		+ the setTime
	 * @return system(Chronotimer) current time in milliseconds
	 */
	public double getSystemTime(){
		return System.currentTimeMillis()-oldSystemTime+setTime;
	}
	/**
	 * Converts parameters into milliseconds and sets setTime, records oldSystemTime
	 * @param hours
	 * @param minutes
	 * @param seconds
	 */
	public void setTime(int hours, int minutes, double seconds) {
		setTime=hours*3600000+minutes*60000+seconds*1000;
		oldSystemTime = System.currentTimeMillis();
	}
	/**
	 * Converts milliseconds (double) param to the format:
	 * Hours:Min:Seconds
	 * @param time
	 * @return
	 */
	public String formatTime(double time){
		int hours = (int)(time/3600000);
		int min = (int)((time - (hours*3600000)) / 60000);
		double seconds = ((time - (hours*3600000) - (min*60000))/1000);
		return hours + ":" + min + ":" + seconds;
	}
}
