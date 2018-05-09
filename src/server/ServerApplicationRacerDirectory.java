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
		
		runners.add(new ServerSideRunner(55, "Bob Smith"));
		runners.add(new ServerSideRunner(66, "Ted Snowden"));
		runners.add(new ServerSideRunner(77, "Tom Brookes"));
		runners.add(new ServerSideRunner(88, "Mary Smith"));
		runners.add(new ServerSideRunner(11, "Alex Mitchell"));
		runners.add(new ServerSideRunner(22, "Catelyn Scholl"));
		runners.add(new ServerSideRunner(33, "Keira Skenandore"));
		runners.add(new ServerSideRunner(44, "Adam Dunn"));
	}

	public boolean containsBibNumber(int bibNumber) {
		for(ServerSideRunner runner : runners) {
			if (runner.getBibNumber() == bibNumber) 
				return true;
		}
		
		return false;
	}
	
}
