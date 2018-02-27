
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
		//TODO
		return System.currentTimeMillis()-oldSystemTime+setTime;
	//	return (System.currentTimeMillis()-oldSystemTime+hours*3600000+minutes*60000+seconds*1000);
	}
	public void setTime(int hours, int minutes, double seconds) {
		setTime=hours*3600000+minutes*60000+seconds*1000;
		oldSystemTime = System.currentTimeMillis();
	}
	
	
	
	//TODO
	//sensor stuff?
	

}
