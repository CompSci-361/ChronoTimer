package server;

import java.io.IOException;

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
						ServerSideRunner runner = directory.getRunnerWithBibNumber(racer.getBibNumber());
						return runner;
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
					//TODO return racers in ascending order based on "place time" or whatever.
					return racers;
				}
			});
			System.out.println("Done");
		}
	}
}
