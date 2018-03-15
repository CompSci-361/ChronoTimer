package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.Chronotimer;
import core.RaceType;

public class TestChronoTimer3 {
	public void testParInd() {
		System.out.println("---Test2---");
		
		/*
		 * Unit tests that reflect some of the outputs
		 * of chrono3.txt
		 */
		
		Chronotimer chronotimer = new Chronotimer();
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"TIME"
		System.out.println("Setting time...");
		chronotimer.setTime(12, 1, 30);
		assertEquals("12:01:30.0", chronotimer.getTime());
		
		//"EVENT IND"
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		chronotimer.setRaceType(RaceType.PARIND);
		assertEquals(RaceType.PARIND, chronotimer.getRaceType());
		System.out.println("Set race type: PARIND");
				
		//"NEWRUN"
		assertEquals(null, chronotimer.getCurrentRun());
		chronotimer.newRun();
		assertNotEquals(null, chronotimer.getCurrentRun());
		System.out.println("New run initiated");		
		
		
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
