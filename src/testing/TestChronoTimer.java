package testing;
import org.junit.Test;
import static org.junit.Assert.*;

import core.*;

public class TestChronoTimer {
	@Test
	public void testChrono1() {
		System.out.println("---Test1---");
		
		//unit test version of chrono1.txt
		Chronotimer chronotimer = new Chronotimer();
		
		//"TIME 12:00:01.0"
		chronotimer.setTime(12, 0, 1);
		System.out.println("Setting time: 12:00:01");
		assertEquals("12:0:1.0", chronotimer.getTime()); //getTime may sometimes add .01 to the end for some reason.
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"CONN GATE 1"
		System.out.println("Connecting GATE sensor to channel 1...");
		chronotimer.setConnect(1, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(1));
		
		System.out.println("Connecting EYE sensor to channel 2...");
		chronotimer.setConnect(2, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(2));
		
		System.out.println("Connecting GATE sensor to channel 3...");
		chronotimer.setConnect(3, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(3));
		
		System.out.println("Connecting EYE sensor to channel 4...");
		chronotimer.setConnect(4, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(4));
		
		//"TOG 1"
		System.out.println("Enabling channel 1 by toggling...");
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		
		System.out.println("Enabling channel 2 by toggling...");
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));
		
		/* "TRIG 1" and related commands. "Current Run must not be null" 
		 * will be printed out since there is no active run.
		 */
		assertEquals(null, chronotimer.getCurrentRun());
		
		System.out.println("Triggering channel 1...");
		chronotimer.triggerChannel(1);
		System.out.println("Triggering channel 3...");
		chronotimer.triggerChannel(3);
		System.out.println("Triggering channel 2...");
		chronotimer.triggerChannel(2);
		System.out.println("Triggering channel 4...");
		chronotimer.triggerChannel(4);
		System.out.println("Triggering channel 1...");
		chronotimer.triggerChannel(1);
		System.out.println("Triggering channel 1...");
		chronotimer.triggerChannel(1);
		System.out.println("Triggering channel 2...");
		chronotimer.triggerChannel(2);
		System.out.println("Triggering channel 2...");
		chronotimer.triggerChannel(2);
		System.out.println("Triggering channel 4...");
		chronotimer.triggerChannel(4);
		
		
		//"POWER"
		System.out.println("Turning off power...");
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"EXIT"
		
		System.out.println("-----------");
		System.out.println();
	}
	
	@Test
	public void testChrono1WithRun() {
		System.out.println("---Test1 v2---");
		
		//unit test version of chrono1.txt but this time, we initiate a run
		Chronotimer chronotimer = new Chronotimer();
		
		//"TIME 12:00:01.0"
		chronotimer.setTime(12, 0, 1);
		System.out.println("Setting time: 12:00:01");
		assertEquals("12:0:1.0", chronotimer.getTime()); //getTime may sometimes add .01 to the end for some reason.
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"CONN GATE 1"
		System.out.println("Connecting GATE sensor to channel 1...");
		chronotimer.setConnect(1, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(1));
		
		System.out.println("Connecting EYE sensor to channel 2...");
		chronotimer.setConnect(2, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(2));
		
		System.out.println("Connecting GATE sensor to channel 3...");
		chronotimer.setConnect(3, SensorType.GATE);
		assertEquals(SensorType.GATE, chronotimer.getSensorType(3));
		
		System.out.println("Connecting EYE sensor to channel 4...");
		chronotimer.setConnect(4, SensorType.EYE);
		assertEquals(SensorType.EYE, chronotimer.getSensorType(4));
		
		System.out.println("Setting race type to IND...");
		chronotimer.setRaceType(RaceType.IND);
		
		//initiate the run
		System.out.println("Initiating new run...");
		chronotimer.newRun();
		Run currentRun = chronotimer.getCurrentRun();
		
		System.out.println("Checking for no racers...");
		assertEquals(0, currentRun.getCurrentRunningRacers().length);
		
		int racerOneBib = 111;
		
		//adds a racer
		chronotimer.addRacer(racerOneBib);

		
		//ensure that the racer is in the "wait" queue
		assertTrue(currentRun.containsRacerBibNumberInWaitQueue(racerOneBib));
		System.out.println("Added racer with bib number of " + racerOneBib);
				
		/*
		 * Enables the sensors
		 */
		
		//"TOG 1"
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		System.out.println("Toggled channel 1");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));
		System.out.println("Toggled channel 2");

		System.out.println("Starting run... (Triggered channel 1)");
		chronotimer.start(); //start the run - equivalent to triggerChannel(1)
		assertNotEquals(null, currentRun.getCurrentRunningRacers());

		
		//ensure that the racer is in the "running" queue
		assertTrue(currentRun.containsRacerBibNumberInRunningQueue(racerOneBib));
		System.out.println("Made sure that there is a racer running.");
		
		assertNotEquals(null, chronotimer.getCurrentRun());
		
		
		/* "TRIG 1" and related commands. "Current Run must not be null" 
		 * will be printed out since there is no active run.
		 */
		chronotimer.triggerChannel(2); //trigger the end of the run
		System.out.println("Triggered channel 2 - Run ended");
		
		//ensure that the racer is in the "end" queue
		assertTrue(currentRun.containsRacerBibNumberInEndQueue(racerOneBib));
		System.out.println("Racer has finished.");
		
		//"POWER"
		System.out.println("Turning power off...");
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"EXIT"
		
		System.out.println("-----------");
		System.out.println();
	}

	@Test
	public void testChrono2() {
		System.out.println("---Test2---");
		
		//unit test version of chrono2.txt
		Chronotimer chronotimer = new Chronotimer();
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"POWER"
		System.out.println("Turning off power...");
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"EVENT IND"
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		chronotimer.setRaceType(RaceType.IND);
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		System.out.println("Set race type: IND");
		
		System.out.println("First run test beginning...");
		
		//"NEWRUN"
		assertEquals(null, chronotimer.getCurrentRun());
		chronotimer.newRun();
		assertNotEquals(null, chronotimer.getCurrentRun());
		System.out.println("New run initiated");
		
		//"TOG" - Toggle Channels 1 and 2
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		System.out.println("Toggled channel 1");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));	
		System.out.println("Toggled channel 2");

		//"NUM" - Adds racer 234 and 315
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(234));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(315));
		chronotimer.addRacer(234);
		chronotimer.addRacer(315);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(234));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(315));
		System.out.println("Added racer (234)");
		System.out.println("Added racer (315)");
		
		//"TRIG" Start
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(234));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(315));
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(3);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(234));
		System.out.println("Triggered channel 1");
		System.out.println("Triggered channel 3");
		
		//trigger 3 isn't enabled
		//assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(315));
		
		//"TRIG" End
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(234));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(315));
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(4);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(234));
		//trigger 4 isn't enabled
		//assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(315));
		System.out.println("Triggered channel 2");
		System.out.println("Triggered channel 4");
		
		//"PRINT"
		// TODO
		chronotimer.print();
		
		//"ENDRUN"
		assertNotEquals(null, chronotimer.getCurrentRun());
		chronotimer.endRun();
		assertEquals(false, chronotimer.getIsRunning());
		System.out.println("Run ended");
		
		/*
		 * Testing second run while power is still on
		 */
		
		System.out.println("Second run test beginning...");
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		System.out.println("Still powered on");
		
		//"NEWRUN"
		assertEquals(false, chronotimer.getIsRunning());
		chronotimer.newRun();
		assertNotEquals(false, chronotimer.getIsRunning());
		System.out.println("New run initiated");
		
		//"EVENT IND"
		//assertEquals(null, chronotimer.getRaceType()); IND is the default apparently.
		chronotimer.setRaceType(RaceType.IND);
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		System.out.println("Set race type: IND");

		//"NUM" - Adds racers
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(167));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(166));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(200));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(201));
		chronotimer.addRacer(167);
		chronotimer.addRacer(166);
		chronotimer.addRacer(200);
		chronotimer.addRacer(201);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(167));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(166));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(200));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(201));
		System.out.println("Added racer (167)");
		System.out.println("Added racer (166)");
		System.out.println("Added racer (200)");
		System.out.println("Added racer (201)");
		
		//"TRIG" Start
		// TODO how do we know which racer triggers which???
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(167));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(166));
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(167));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(166));		
		System.out.println("Triggered channel 1");
		System.out.println("Triggered channel 1");
		
		//"TRIG" End
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(167));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(166));
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(167));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(166));
		
		System.out.println("Triggered channel 2");
		System.out.println("Triggered channel 2");
		
		// TODO how do we test TRIG 4?
		
		//"PRINT"
		// TODO
		chronotimer.print();
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		System.out.println("Turning off power...");
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		System.out.println("Turning on power...");
		
		//"NEWRUN"
		// TODO note that the previous run was never ended !!
		//assertEquals(null, chronotimer.getCurrentRun());
		chronotimer.newRun();
		assertNotEquals(null, chronotimer.getCurrentRun());
		System.out.println("New run initiated...");
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		System.out.println("Turning off power...");
		
		//"EXIT"
		
		System.out.println("-----------");
		System.out.println();
	}
	
}
