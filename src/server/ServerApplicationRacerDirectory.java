package server;

import java.util.ArrayList;

class ServerApplicationRacerDirectory {
	private ArrayList<ServerSideRunner> runners = null;
	public ServerApplicationRacerDirectory() {
		runners = new ArrayList<ServerSideRunner>();
	}
	
	public ServerSideRunner getRunnerWithBibNumber(int bibNumber) {
		for(ServerSideRunner runner : runners) {
			if (runner.getBibNumber() == bibNumber) 
				return runner;
		}
		
		return null;
	}

	public void loadSampleData() {
		runners.clear();
		
		runners.add(new ServerSideRunner(367, "Bob Smith"));
		runners.add(new ServerSideRunner(226, "Ted Snowden"));
		runners.add(new ServerSideRunner(217, "Tom Brookes"));
		runners.add(new ServerSideRunner(101, "Mary Smith"));
	}

	public boolean containsBibNumber(int bibNumber) {
		for(ServerSideRunner runner : runners) {
			if (runner.getBibNumber() == bibNumber) 
				return true;
		}
		
		return false;
	}
	
}
