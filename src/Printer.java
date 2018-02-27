
public class Printer {

	/**
	 * Print individual race
	 * @param racer
	 */
	public static void printRace(Racer racer){
		System.out.println(racer.toString());
	}
	
	/**
	 * Print all races run
	 * Only prints finished Racers
	 * --Might want to change to print all Racers
	 * @param run
	 */
	public static void printRun(Run run){
		System.out.println(run.toString());
	}
}
