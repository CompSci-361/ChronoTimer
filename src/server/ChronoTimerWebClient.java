package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.google.gson.Gson;

import core.Chronotimer;
import core.Chronotimer.RaceStatusChangedEventType;
import core.Racer;
import core.Run;

/*
 * An addon class for connecting the Chronotimer to a http web server.
 */
public class ChronoTimerWebClient {
	private Chronotimer chronoTimer = null;
	public ChronoTimerWebClient(Chronotimer timer) {
		if (timer == null) throw new IllegalArgumentException("timer");
		chronoTimer = timer;
		
		//event handler for listening for when a race starts and ends.
		timer.addRaceStatusChangedActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Chronotimer.RaceStatusChangedEventType raceType = Enum.valueOf(Chronotimer.RaceStatusChangedEventType.class, e.getActionCommand());
				
				//check to see if this handler is being fired because a race ended.
				if (raceType == RaceStatusChangedEventType.RaceEnd) {
					//grab the current race so that we can get the results (finished racers).
					Run currentRun = timer.getCurrentRun();
					if (currentRun != null) {
						try {
							//get the finished racers
							Racer[] raceResults = currentRun.getFinishedRacers();
							
							//prepare to send it to the server.
							Gson g = new Gson();
							String json = g.toJson(raceResults);
							
							URL site = new URL("http://localhost:8080/");
	
							HttpURLConnection conn = (HttpURLConnection) site.openConnection();
							// now create a POST request
							conn.setRequestMethod("POST");
							conn.setDoOutput(true);
							conn.setDoInput(true);
	
							DataOutputStream out = new DataOutputStream(conn.getOutputStream());
	
							out.writeBytes(json);
							out.flush();
							out.close();
							
							conn.getResponseCode(); //IMPORTANT: this actually sends the http request
						} catch (Exception ex) {
							//sprint 4 doc says to ignore and continue.
							System.out.println("Error posting results to server: " + ex.toString());
						}
					}
				}
			}
		});
	}
}
