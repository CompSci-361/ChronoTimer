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
		
		
	}

	public boolean containsBibNumber(int bibNumber) {
		for(ServerSideRunner runner : runners) {
			if (runner.getBibNumber() == bibNumber) 
				return true;
		}
		
		return false;
	}
	
}
