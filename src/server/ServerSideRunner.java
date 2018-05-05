package server;

import core.Racer;

class ServerSideRunner {
	private int bibNumber;
	private String runnerName;
	private Racer racer;
	ServerSideRunner(int bibNumber, String name) {
		this.bibNumber = bibNumber;
		this.runnerName = name;
	}
	
	public String getName() {
		return this.runnerName;
	}
	
	public int getBibNumber() {
		return this.bibNumber;
	}
	
	public void setRacer(Racer racer) {
		this.racer = racer;
	}
	
	public Racer getRacer() {
		return this.racer;
	}
}
