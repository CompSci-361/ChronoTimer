package testing;
import org.junit.Test;
import static org.junit.Assert.*;

import core.*;

public class TestChronoTimer {
	@Test
	public void testChrono1() {
		//unit test version of chrono1.txt
		Chronotimer chronotimer = new Chronotimer();
		
		//"TIME 12:00:01.0"
		chronotimer.setTime(12, 0, 1);
		assertEquals("12:0:1.0", chronotimer.getTime()); //getTime may sometimes add .01 to the end for some reason.
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"CONN GATE 1"
		chronotimer.setConnect(1, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(1));
		
		chronotimer.setConnect(2, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(2));
		
		chronotimer.setConnect(3, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(3));
		
		chronotimer.setConnect(4, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(4));
		
		//"TOG 1"
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));
		
		/* "TRIG 1" and related commands. "Current Run must not be null" 
		 * will be printed out since there is no active run.
		 */
		assertEquals(null, chronotimer.getCurrentRun());
		
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(3);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(4);
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(4);
		
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"EXIT"
	}
	
	@Test
	public void testChrono1WithRun() {
		//unit test version of chrono1.txt but this time, we initiate a run
		Chronotimer chronotimer = new Chronotimer();
		
		//"TIME 12:00:01.0"
		chronotimer.setTime(12, 0, 1);
		assertEquals("12:0:1.0", chronotimer.getTime()); //getTime may sometimes add .01 to the end for some reason.
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"CONN GATE 1"
		chronotimer.setConnect(1, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(1));
		
		chronotimer.setConnect(2, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(2));
		
		chronotimer.setConnect(3, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(3));
		
		chronotimer.setConnect(4, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(4));
		
		chronotimer.setRaceType(RaceType.IND);
		
		//initiate the run
		chronotimer.newRun();
		Run currentRun = chronotimer.getCurrentRun();
		
		assertEquals(null, currentRun.getCurrentRunningRacer());
		
		int racerOneBib = 111;
		
		//adds a racer
		chronotimer.addRacer(racerOneBib);
		
		//ensure that the racer is in the "wait" queue
		assertTrue(currentRun.containsRacerBibNumberInWaitQueue(racerOneBib));
		
		/*
		 * Enables the sensors
		 */
		
		//"TOG 1"
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));	

		chronotimer.start(); //start the run - equivalent to triggerChannel(1)
		assertNotEquals(null, currentRun.getCurrentRunningRacer());
		
		//ensure that the racer is in the "running" queue
		assertTrue(currentRun.containsRacerBibNumberInRunningQueue(racerOneBib));
		
		assertNotEquals(null, chronotimer.getCurrentRun());
		
		
		/* "TRIG 1" and related commands. "Current Run must not be null" 
		 * will be printed out since there is no active run.
		 */
		chronotimer.triggerChannel(2); //trigger the end of the run
		
		//ensure that the racer is in the "end" queue
		assertTrue(currentRun.containsRacerBibNumberInEndQueue(racerOneBib));
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"EXIT"
	}

	@Test
	public void testChrono2() {
		//unit test version of chrono2.txt
		Chronotimer chronotimer = new Chronotimer();
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());

		
	}
}
