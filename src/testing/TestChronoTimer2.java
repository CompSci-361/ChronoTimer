package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.*;


public class TestChronoTimer2 {
	
	@Test
	public void testParInd() {
		System.out.println("---Test ParInd---");
		
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
		assertEquals("12:1:30.0", chronotimer.getTime());
		
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
		
		System.out.println("First run test beginning...");
		
		//"TOG" - Toggle Channels 1 - 4
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		System.out.println("Toggled channel 1");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));	
		System.out.println("Toggled channel 2");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(3));
		chronotimer.toggleChannel(3);
		assertEquals(true, chronotimer.getChannelIsEnabled(3));	
		System.out.println("Toggled channel 3");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(4));
		chronotimer.toggleChannel(4);
		assertEquals(true, chronotimer.getChannelIsEnabled(4));	
		System.out.println("Toggled channel 4");

		//"NUM" - Adds racer 234 and 315
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(272));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(123));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(111));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(711));
		chronotimer.addRacer(272);
		chronotimer.addRacer(123);
		chronotimer.addRacer(111);
		chronotimer.addRacer(711);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(272));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(123));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(111));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(711));
		System.out.println("Added racer (272)");
		System.out.println("Added racer (123)");
		System.out.println("Added racer (111)");
		System.out.println("Added racer (711)");
		
		//"TRIG" Start
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(272));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(111));
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(3);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(272));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(123));
		System.out.println("Racer (123) Triggered channel 1");
		System.out.println("Racer (272) triggered channel 3");
		
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(111));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(711));
		
		//"TRIG" End
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(272));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(123));
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(4);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(123));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(272));
		System.out.println("Racer (123) Triggered channel 2");
		System.out.println("Racer (272) Triggered channel 4");
		
		assertTrue(chronotimer.getCurrentRun().getCurrentRunningRacers().length == 0);
		
		//"PRINT"
		chronotimer.print();
		
		//"ENDRUN"
		assertNotEquals(null, chronotimer.getCurrentRun());
		chronotimer.endRun();
		assertEquals(null, chronotimer.getCurrentRun());
		System.out.println("Run ended");
				
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		System.out.println("Turning off power...");
	}
}