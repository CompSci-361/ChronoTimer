package server;

import java.io.IOException;
import java.util.Arrays;

import core.Racer;
import server.ChronoTimerWebServer.IChronoTimerWebServerResolveRacer;
import server.ChronoTimerWebServer.IChronoTimerWebServerSortRacers;

public class ServerApplication {
	static ChronoTimerWebServer server = new ChronoTimerWebServer();
	static ServerApplicationRacerDirectory directory = new ServerApplicationRacerDirectory();
	public static void main(String[] args) throws IOException {
		{
			System.out.print("Initializing directory... ");
			directory.loadSampleData();
			System.out.println("Done");
		}
		
		{
			System.out.print("Initializing server... ");
			server.initialize(new IChronoTimerWebServerResolveRacer() {
				@Override
				public ServerSideRunner resolve(Racer racer) {
					if (directory.containsBibNumber(racer.getBibNumber())) {
						//make a copy of whats in the directory
						ServerSideRunner runner = directory.getRunnerWithBibNumber(racer.getBibNumber());
						ServerSideRunner runnerClone = new ServerSideRunner(runner.getBibNumber(), runner.getName()); 
						//if only i implemented the interface for creating clones...
						runnerClone.setRacer(racer);
						return runnerClone;
					} else {
						ServerSideRunner runner = new ServerSideRunner(racer.getBibNumber(), "");
						runner.setRacer(racer);
						return runner;
					}
				}
			},
			new IChronoTimerWebServerSortRacers() {
				@Override
				public Racer[] sortRacers(Racer[] racers) {
					//return racers in ascending order based on "place time" or whatever.
					
					//sorts the plays who actually have a time.
					Object[] sortedTimedRacers = Arrays.stream(racers).filter(x -> !x.hasDnf()).sorted((x,y) -> Double.compare(x.getEndTime(), y.getEndTime())).toArray();
					//arranges dnf racers by bib number.
					Object[] sortedDnfRacers = Arrays.stream(racers).filter(x -> x.hasDnf()).sorted((x,y) -> Integer.compare(x.getBibNumber(), y.getBibNumber())).toArray();
					
					//merge the two arrays
					Racer[] finalArray = new Racer[sortedTimedRacers.length + sortedDnfRacers.length];
					int index = 0;
					for(Object r : sortedTimedRacers) {
						finalArray[index] = (Racer) r;
						index++;
					}
					for(Object r : sortedDnfRacers) {
						finalArray[index] = (Racer) r;
						index++;
					}
					
					return finalArray;
					
				}
			});
			System.out.println("Done");
		}
	}
}
