package Test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import elevator.Elevator;
import elevator.ElevatorImp;
import elevator.MovingState;
import elevatorsystem.ElevatorPanel;
import elevatorsystem.ElevatorSystem;
import elevatorsystem.ElevatorSystemImp;

class ElevatorTest {
	private static Elevator elevator;
	
	@BeforeAll
	static void init() throws Exception {
		System.out.println("Elevator test start");
	}
	
	@BeforeEach
	void setup() {
		elevator = new ElevatorImp(5, (ElevatorPanel)new ElevatorSystemImp( 0, 20));
		
		elevator.addPersons(2);
		elevator.moveTo(10);
	}
	
	@Test
	void testgetCapacity() {
		assertTrue(2==elevator.getCapacity());
	}

	@Test
	void testisFull() {
		assertTrue(!elevator.isFull());
	}
	
	@Test
	void testisEmpty() {
		assertTrue(!elevator.isEmpty());
	}
	
	@Test
	void TestgetState() {
		assertTrue(MovingState.Idle==elevator.getState());
	}
	
	@Test
	void testgetPowerConsumed() {
		assertTrue(12==elevator.getPowerConsumed());	
	}
	
	@Test
	void testmoveto() {
		assertTrue(10== elevator.getFloor());
	}
	
	@Test
	void testgetFloor() {
		assertTrue(10== elevator.getFloor());
	}
	
	@Test
	void testaddPerson() {
		elevator.addPersons(6);
		assertTrue(5==elevator.getCapacity());
	}
	/*
	@Test
	void testrequestStop() {
		elevator.requestStop(10);
		assertTrue(10== elevator.getFloor());
	}*/
	
	@AfterAll
	static void reset() throws Exception {
		elevator = null;
		System.out.println("Elevator test end");
	}
}
