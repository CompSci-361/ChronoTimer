package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import core.Chronotimer;
import core.RaceType;

public class TestChronoTimer32 {
	@Test
	public void testGrpRun() {
		System.out.println("---Test GrpRun---");
		
		/*
		 * Unit tests that reflect some of the outputs
		 * of chrono4-2.txt
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
		
		//ADD RACER
		chronotimer.addRacer(123);
		chronotimer.addRacer(456);
		chronotimer.addRacer(789);
		
		assertEquals(chronotimer.getCurrentRun().getCurrentWaitingRacers().length, 3);
		
		//"TRIG" Start
		assertEquals(0, chronotimer.getCurrentRun().getFinishedRacers().length);
		chronotimer.triggerChannel(1);
		System.out.println("Racers triggered channel 1");
		assertEquals(3, chronotimer.getCurrentRun().getCurrentRunningRacers().length);

		//"TRIG" End
		assertEquals(0, chronotimer.getCurrentRun().getFinishedRacers().length);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		chronotimer.addRacer(123);
		chronotimer.addRacer(456);
		chronotimer.addRacer(789);
		assertEquals(3, chronotimer.getCurrentRun().getFinishedRacers().length);
		System.out.println("Three racers triggered channel 2");
		
		
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
