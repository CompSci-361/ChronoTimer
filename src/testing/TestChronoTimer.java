package testing;
import org.junit.Test;
import static org.junit.Assert.*;

import core.Chronotimer;

public class TestChronoTimer {
	@Test
	public void testChrono1(){
		//unit test version of chrono1.txt
		Chronotimer chronotimer = new Chronotimer();
		
		//"TIME 12:00:01.0"
		chronotimer.setTime(12, 0, 1);
		assertEquals("12:0:1.0", chronotimer.getTime());
		
		//"POWER"
		assertEquals(false, chronotimer.getIsPower());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPower());
		
		//"CONN GATE 1"
		chronotimer.setConnect(1, "GATE");
		assertEquals("GATE", chronotimer.getSensorType(1));
		
		chronotimer.setConnect(2, "EYE");
		assertEquals("EYE", chronotimer.getSensorType(2));
		
		chronotimer.setConnect(3, "GATE");
		assertEquals("GATE", chronotimer.getSensorType(3));
		
		chronotimer.setConnect(2, "EYE");
		assertEquals("EYE", chronotimer.getSensorType(2));
		
		//"TOG 1"
		assertEquals(false, chronotimer.getChannel(1));
		chronotimer.toggleChannel(1);
		assertEquals(true, chronotimer.getChannel(1));
		
		assertEquals(false, chronotimer.getChannel(2));
		chronotimer.toggleChannel(2);
		assertEquals(true, chronotimer.getChannel(2));
	}

}
