
public class Racer {

	private int bibNumber;
	private double startTime;
	private double endTime;
	
	public Racer(int bibNumber){
		this.bibNumber = bibNumber;
	}
	
	public void setStartTime(){
		this.startTime = Chronotimer.ourTimer.getSystemTime();
	}
	
	public void setEndTime(){
		this.endTime = Chronotimer.ourTimer.getSystemTime();
	}
	/**
	 * This is for giving a racer a DNF end time
	 */
	public void setDnf() {
		this.endTime = -1;
	}
	
	public double getStartTime(){
		return startTime;
	}
	
	public double getEndTime(){
		return endTime;
	}
	
	public double getTotalTime(){
		return Chronotimer.ourTimer.calculateTime(startTime, endTime);
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Racer))
			return false;
		Racer racer = (Racer)obj;
		return this.bibNumber == racer.bibNumber;	
	}
	
	
	
}
