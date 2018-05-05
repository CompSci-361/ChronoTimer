package server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Callable;

import core.Racer;
import server.ChronoTimerWebServer.IChronoTimerWebServerResolveRacer;

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
			});
			System.out.println("Done");
		}
	}
}
