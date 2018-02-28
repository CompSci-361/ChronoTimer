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
		
		//todo: handle the "trig 1" * commands
		
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
