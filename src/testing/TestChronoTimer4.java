package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import core.Chronotimer;
import core.RaceType;
import core.Racer;

public class TestChronoTimer4 {
	@Test
	public void testGrpRun() {
		System.out.println("---Test GrpRun---");
		
		/*
		 * Unit tests that reflect some of the outputs
		 * of chrono4.txt
		 */
		
		Chronotimer chronotimer = new Chronotimer();
		
		//"POWER"
		System.out.println("Turning on power...");
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		
		//"TOG" - Toggle Channels 1 and 2
		assertEquals(false, chronotimer.getChannelIsEnabled(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannelIsEnabled(1));
		System.out.println("Toggled channel 1");
		
		assertEquals(false, chronotimer.getChannelIsEnabled(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannelIsEnabled(2));	
		System.out.println("Toggled channel 2");
		
		//"EVENT IND"
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		chronotimer.setRaceType(RaceType.GRP);
		assertEquals(RaceType.GRP, chronotimer.getRaceType());
		System.out.println("Set race type: GRP");
		
		//"NEWRUN"
		assertEquals(null, chronotimer.getCurrentRun());
		chronotimer.newRun();
		assertNotEquals(null, chronotimer.getCurrentRun());
		System.out.println("New run initiated");		
		
		System.out.println("First run test beginning...");
		
		//"TRIG" Start
		assertEquals(0, chronotimer.getCurrentRun().getFinishedRacers().length);
		chronotimer.triggerChannel(1);
		System.out.println("Racers triggered channel 1");
		
		//"TRIG" End
		assertEquals(0, chronotimer.getCurrentRun().getFinishedRacers().length);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		assertEquals(3, chronotimer.getCurrentRun().getFinishedRacers().length);
		System.out.println("Three racers triggered channel 2");
		
		//"NUM" - Adds racers 123, 456, 789
		Racer[] racers = chronotimer.getCurrentRun().getFinishedRacers();
		assertNotEquals(123, racers[0].getBibNumber());
		assertNotEquals(456, racers[1].getBibNumber());
		assertNotEquals(789, racers[2].getBibNumber());
		chronotimer.addRacer(123);
		chronotimer.addRacer(456);
		chronotimer.addRacer(789);
		assertEquals(123, racers[0].getBibNumber());
		assertEquals(456, racers[1].getBibNumber());
		assertEquals(789, racers[2].getBibNumber());
		System.out.println("Added racer (123)");
		System.out.println("Added racer (456)");
		System.out.println("Added racer (789)");
		
		//"ENDRUN"
		assertNotEquals(null, chronotimer.getCurrentRun());
		chronotimer.endRun();
		assertEquals(null, chronotimer.getCurrentRun());
		System.out.println("Run ended");
		
		//"PRINT"
		chronotimer.print();
		
		//"POWER"
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
		System.out.println("Turning off power...");
	}
}
