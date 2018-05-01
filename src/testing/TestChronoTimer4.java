package testing;

import static org.junit.Assert.*;

import org.junit.Test;

import core.Chronotimer;
import core.ParGrpRun;
import core.RaceType;
import core.Racer;
import core.SensorType;

public class TestChronoTimer4 {
	@Test
	public void testParGrpRun() {
		System.out.println("---Test ParGrpRun---");
		
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
		
		chronotimer.setTime(0,0,0);
		
		//"TOG" - Toggle Channels 1-8
		for(int i = 1; i < 9; i++){
			chronotimer.toggleChannel(i);
		}
		
		for(int i = 1; i < 9; i++){
			assertTrue(chronotimer.getChannelIsEnabled(i));
		}
		
		//"EVENT PARGRP"
		assertEquals(RaceType.IND, chronotimer.getRaceType());
		chronotimer.setRaceType(RaceType.PARGRP);
		assertEquals(RaceType.PARGRP, chronotimer.getRaceType());
		System.out.println("Set race type: PARGRP");
		
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
		
		int size = 0;
		for(int i = 0; i < 8; i++){
			if(chronotimer.getCurrentRun().getCurrentWaitingRacers()[i] != null)
				size++;
		}
		assertEquals(size, 3);
		
		//"TRIG" Start
		assertEquals(0, chronotimer.getCurrentRun().getFinishedRacers().length);
		chronotimer.triggerChannel(1);
		System.out.println("Racers triggered channel 1");
		assertNotEquals(chronotimer.getCurrentRun().getCurrentRunningRacers(), null);
		
		Racer[] runningRacers = chronotimer.getCurrentRun().getCurrentRunningRacers();
		System.out.println(runningRacers[0]);
		if(runningRacers[0] != null)
			System.out.println(runningRacers[0]);
		assertTrue(chronotimer.getCurrentRun() != null);
		assertTrue(chronotimer.getCurrentRun() instanceof ParGrpRun);
		assertTrue(chronotimer.getCurrentRun().getCurrentRunningRacers() != null);
		
		assertNotEquals(chronotimer.getCurrentRun().getCurrentRunningRacers()[0], null);
		
		assertNotEquals(chronotimer.getCurrentRun().getCurrentRunningRacers()[0].getStartTime(), -1);
		
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(2);
		System.out.println("Racers triggered channel 1 and 2");
		
		size = 0;
		for(int i = 0; i < 8; i++){
			if(chronotimer.getCurrentRun().getCurrentRunningRacers()[i] != null)
				size++;
		}
		assertEquals(size, 1);

		//"TRIG" End
		assertEquals(2, chronotimer.getCurrentRun().getFinishedRacers().length);		
		
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
