package core;

public class Racer {

	private int bibNumber;
	private double startTime; //milliseconds
	private double endTime; //milliseconds
	private Stopwatch raceStopWatch;
	
	public Racer(int bibNumber){
		this.bibNumber = bibNumber;
		this.raceStopWatch = new Stopwatch();
	}
	
	void onBeginRacing() {
		raceStopWatch.start();
	}
	
	void onFinishRacing() {
		raceStopWatch.stop();
	}
	
	public double getCurrentRaceTime() {
		return raceStopWatch.getElapsedTime();
	}
	
	/**
	 * resets the startTime to 0
	 */
	public void clearStartTime() {
		startTime=0;
	}
	/**
	 * Gets the current time from ourTimer and gives that value to the start time
	 */
	public void setStartTime(){
		this.startTime = Chronotimer.ourTimer.getSystemTime();
	}
	
	public void setStartTime(double time){
		this.startTime = time;
	}
	
	/**
	 * Gets the current time from ourTimer and gives that value to the end time
	 */
	public void setEndTime(){
		this.endTime = Chronotimer.ourTimer.getSystemTime();
	}
	
	public void setEndTime(double time){
		this.endTime = time;
	}
	
	public boolean hasDnf() {
		return this.endTime <= -1;
	}
	
	/**
	 * This is for giving a racer a DNF end time represented by -1
	 */
	public void setDnf() {
		this.endTime = -1;
	}
	/**
	 * @return startTime
	 */
	public double getStartTime(){
		return startTime;
	}
	/**
	 * @return endTime
	 */
	public double getEndTime(){
		return endTime;
	}
	/**
	 * Sends the Racer attributes to ourTimer to calculate finish time
	 * @return
	 */
	public double getTotalTime(){
		return Chronotimer.ourTimer.calculateTime(startTime, endTime);
	}
	/**
	 * Accessor for bibNumber
	 * @return
	 */
	public int getBibNumber() {
		return bibNumber;
	}
	public void setBibNumber(int bibNumber) {
		this.bibNumber = bibNumber;
	}
	/**
	 * To allow checking if a racer is already existing
	 */
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Racer))
			return false;
		Racer racer = (Racer)obj;
		return this.bibNumber == racer.bibNumber;	
	}
	/**
	 * Converts Racer to a string in the format:
	 * Racer XXX : Start Time = Hours:Min:Seconds : End Time = Hours:Min:Seconds
	 */
	@Override
	public String toString(){
		return "Racer " + this.bibNumber + ": Total Time = "+Chronotimer.ourTimer.formatTime(Chronotimer.ourTimer.calculateTime(startTime, endTime))+" Start Time = " + Chronotimer.ourTimer.formatTime(this.startTime)
				+ " End Time = " + Chronotimer.ourTimer.formatTime(this.endTime);
	}
	
	
	
}
