
public class Racer {

	private int bibNumber;
	private double startTime;
	private double endTime;
	
	public Racer(int bibNumber){
		this.bibNumber = bibNumber;
	}
	
	public void setStartTime(){
		this.startTime = Timer.getSystemTime();
	}
	
	public void setEndTime(){
		this.endTime = Timer.getSystemTime();
	}
	
	public double getStartTime(){
		return startTime;
	}
	
	public double getEndTime(){
		return endTime;
	}
	
	public double getTotalTime(){
		return Timer.calculateTime(startTime, endTime);
	}
	
	@Override
	public boolean equals(Object obj){
		if(!(obj instanceof Racer))
			return false;
		Racer racer = (Racer)obj;
		return this.bibNumber == racer.bibNumber;	
	}
	
	
	
}
