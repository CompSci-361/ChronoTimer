package testing;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import core.*;

public class TestChronoTimerUnitTest {
	Chronotimer chronotimer = new Chronotimer();
	
	@Test
	public void testReset() {
		chronotimer.togglePower();
		chronotimer.reset();
		
		assertEquals(true, chronotimer.getIsPoweredOn());
		assertEquals(null, chronotimer.getCurrentRun());
		assertEquals(RaceType.IND, chronotimer.getRaceType());
	}
	
	@Test
	public void testPower() {
		assertEquals(false, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(true, chronotimer.getIsPoweredOn());
		chronotimer.togglePower();
		assertEquals(false, chronotimer.getIsPoweredOn());
	}
	
	@Test
	public void testChannels() {
		chronotimer.togglePower();

		//default state is off
		for(int i = 1; i < 9; i++){
			assertTrue(!chronotimer.getChannelIsEnabled(i));
			chronotimer.toggleChannel(i);
		}
		//turn all channels on
		for(int i = 1; i < 9; i++){
			assertTrue(chronotimer.getChannelIsEnabled(i));
			chronotimer.toggleChannel(i);
		}
		//turn them all off again
		for(int i = 1; i < 9; i++){
			assertTrue(!chronotimer.getChannelIsEnabled(i));
		}
		//turn random channel on
		chronotimer.toggleChannel(3);
		assertTrue(chronotimer.getChannelIsEnabled(3));
	}
	
	@Test
	public void testRun() {
		chronotimer.togglePower();
		
		chronotimer.newRun();
		assertNotEquals(null, chronotimer.getCurrentRun());
		assertTrue(chronotimer.getCurrentRun() instanceof IndRun);
		assertEquals(1, chronotimer.getRunNumber());
		
		chronotimer.endRun();
		assertEquals(null, chronotimer.getCurrentRun());
		
		chronotimer.newRun();
		assertEquals(2, chronotimer.getRunNumber());
		
	}
	
	@Test
	public void testAddRacer() {
		chronotimer.togglePower();
		chronotimer.newRun();
		chronotimer.addRacer(123);
		
		//prints error message
		chronotimer.addRacer(123);
		
		chronotimer.addRacer(134);
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(123));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(134));
		
		chronotimer.addRacer(175);
		chronotimer.addRacer(189);
		assertEquals(4, chronotimer.getCurrentRun().getCurrentWaitingRacers().length);
	}
	
	@Test
	public void testTrigger() {
		chronotimer.togglePower();
		chronotimer.newRun();
		chronotimer.addRacer(123);
		chronotimer.triggerChannel(1);
		
		assertEquals(0, chronotimer.getCurrentRun().getCurrentRunningRacers().length);
		
		chronotimer.toggleChannel(1);
		chronotimer.triggerChannel(1);

		assertEquals(1, chronotimer.getCurrentRun().getCurrentRunningRacers().length);
		System.out.println("Racer 123 Start Time: " + chronotimer.getCurrentRun().getCurrentRunningRacers()[0].getStartTime());
		
		chronotimer.toggleChannel(2);
		chronotimer.triggerChannel(2);
		
		assertEquals(1, chronotimer.getCurrentRun().getFinishedRacers().length);
	}
	
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
		
		assertEquals(11, chronotimer.getCurrentRun().getCurrentRunningRacers()[0].getBibNumber());
	}
	
	@Test
	public void testSwap2(){
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.valueOf("IND"));
		chronotimer.newRun();
				
		chronotimer.addRacer(10);
		chronotimer.addRacer(11);
		chronotimer.addRacer(12);
		chronotimer.addRacer(13);
		
		chronotimer.toggleChannel(1);
		chronotimer.toggleChannel(2);
		assertTrue(chronotimer.getChannelIsEnabled(1));
		assertTrue(chronotimer.getChannelIsEnabled(2));
		
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		chronotimer.triggerChannel(1);
		
		chronotimer.swap();
		
		assertEquals(11, chronotimer.getCurrentRun().getCurrentRunningRacers()[0].getBibNumber());
		
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		
		chronotimer.swap();
		
		assertEquals(13, chronotimer.getCurrentRun().getCurrentRunningRacers()[0].getBibNumber());
		
		chronotimer.triggerChannel(2);
		chronotimer.triggerChannel(2);
		
		assertEquals(0, chronotimer.getCurrentRun().getCurrentRunningRacers().length);
	}
	
	@Test
	public void testCancelIND() {
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.IND);
		chronotimer.newRun();
		chronotimer.addRacer(10);
		chronotimer.toggleChannel(1);
		
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
		
		chronotimer.triggerChannel(1);
		
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
		
		chronotimer.cancel();
		
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
	}
	
	@Test
	public void testCancelPARIND() {
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.PARIND);
		assertEquals(RaceType.PARIND, chronotimer.getRaceType());
		chronotimer.newRun();
		assertTrue(chronotimer.getCurrentRun() instanceof ParIndRun);
		chronotimer.addRacer(10);
		chronotimer.toggleChannel(3);
		
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
		
		chronotimer.triggerChannel(3);
		
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
		
		chronotimer.cancel();
		
		assertEquals(true, chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(10));
		assertEquals(false, chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10));
	}
	
	@Test
	public void testSensors(){
		chronotimer.togglePower();
		chronotimer.setConnect(1, SensorType.EYE);
		chronotimer.setConnect(3, SensorType.PAD);
		chronotimer.setConnect(2, SensorType.TRIP);
		chronotimer.setDisconnect(2);
		
		assertEquals(chronotimer.getSensorType(1), SensorType.EYE);
		assertEquals(chronotimer.getSensorType(3), SensorType.PAD);
		assertEquals(chronotimer.getSensorType(2), SensorType.NONE);
	}
	
	@Test
	public void testTime(){
		chronotimer.togglePower();
		chronotimer.setTime(10, 10, 10.0);
		assertEquals(chronotimer.getTime(), "10:10:10.0");
	}
	
	@Test
	public void testDNF(){
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.IND);
		chronotimer.newRun();
		chronotimer.addRacer(10);
		chronotimer.toggleChannel(1);
		chronotimer.triggerChannel(1);
		chronotimer.dnf();
		
		assertEquals(chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(10), true);
		assertEquals(chronotimer.getCurrentRun().getFinishedRacers()[0].getEndTime(), -1, 0);
	}
	
	@Test
	public void testStart(){
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.IND);
		chronotimer.newRun();
		chronotimer.addRacer(10);
		chronotimer.toggleChannel(1);
		chronotimer.start();
		
		assertEquals(chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10), true);
	}
	
	@Test
	public void testFinish(){
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.IND);
		chronotimer.newRun();
		chronotimer.addRacer(10);
		chronotimer.toggleChannel(1);
		chronotimer.toggleChannel(2);
		chronotimer.start();
		
		assertEquals(chronotimer.getCurrentRun().containsRacerBibNumberInRunningQueue(10), true);
		
		chronotimer.finish();
		
		assertEquals(chronotimer.getCurrentRun().containsRacerBibNumberInEndQueue(10), true);
	}
	
	@Test
	public void testClear(){
		chronotimer.togglePower();
		chronotimer.setRaceType(RaceType.IND);
		chronotimer.newRun();
		chronotimer.addRacer(10);
		chronotimer.clear(10);
		
		assertEquals(chronotimer.getCurrentRun().containsRacerBibNumberInWaitQueue(0), false);
	}
}
