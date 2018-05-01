package server;

import core.Chronotimer;

/*
 * An addon class for connecting the Chronotimer to a http web server.
 */
public class ChronoTimerWebClient {
	private Chronotimer chronoTimer = null;
	public ChronoTimerWebClient(Chronotimer timer) {
		if (timer == null) throw new IllegalArgumentException("timer");
		chronoTimer = timer;
	}
}
