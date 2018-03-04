package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.*;

public class TestChronoTimer2 {
	Chronotimer chronotimer = new Chronotimer();
	
	@Test
	public void testSwap() {
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.valueOf("IND"));
		chronotimer.newRun();
				
		chronotimer.addRacer(10);
		chronotimer.addRacer(11);
		
		chronotimer.toggleChannel(1);
		chronotimer.toggleChannel(2);
		assertTrue(chronotimer.getChannelIsEnabled(1));
		assertTrue(chronotimer.getChannelIsEnabled(2));
		
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		
		chronotimer.swap();
		
		assertEquals(11, chronotimer.getCurrentRun().getCurrentRunningRacer().getBibNumber());
	}
}
