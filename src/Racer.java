
public class Racer {

	private int bibNumber;
	private double startTime;
	private double endTime;
	
	public Racer(int bibNumber){
		this.bibNumber = bibNumber;
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
	/**
	 * Gets the current time from ourTimer and gives that value to the end time
	 */
	public void setEndTime(){
		this.endTime = Chronotimer.ourTimer.getSystemTime();
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
		return "Racer " + this.bibNumber + ": Start Time = " + Chronotimer.ourTimer.formatTime(this.startTime)
				+ " End Time = " + Chronotimer.ourTimer.formatTime(this.endTime);
	}
	
	
	
}
