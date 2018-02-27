
public class Timer {
	//Milliseconds
	double oldSystemTime;
	double setTime;

	public Timer() {
		setTime=0.0;
		oldSystemTime=0.0;
	}
	/**
	 * 
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public double calculateTime(double startTime, double endTime){
		return endTime - startTime;
	}
	
	/**
	 * 
	 * @return system current time in milliseconds
	 */
	public double getSystemTime(){
		return System.currentTimeMillis()-oldSystemTime+setTime;
	}
	public void setTime(int hours, int minutes, double seconds) {
		setTime=hours*3600000+minutes*60000+seconds*1000;
		oldSystemTime = System.currentTimeMillis();
	}
	
	public String formatTime(double time){
		int hours = (int)(time/3600000);
		int min = (int)((time - (hours*3600000)) / 60000);
		double seconds = ((time - (hours*3600000) - (min*60000))/1000);
		return hours + ":" + min + ":" + seconds;
	}
}
